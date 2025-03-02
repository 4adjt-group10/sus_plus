package br.com.susunity.service;

import br.com.susunity.controller.dto.professional.ProfessionalOut;
import br.com.susunity.controller.dto.unity.UnityInForm;
import br.com.susunity.controller.dto.unity.UnityDto;
import br.com.susunity.controller.dto.professional.UnityProfessionalForm;
import br.com.susunity.model.ProfessionalUnityModel;
import br.com.susunity.model.SpecialityModel;
import br.com.susunity.model.UnityModel;
import br.com.susunity.queue.consumer.dto.manager.Professional;
import br.com.susunity.queue.consumer.dto.manager.Speciality;
import br.com.susunity.queue.consumer.dto.MessageBodyByPatientRecord;
import br.com.susunity.queue.consumer.dto.MessageBodyByScheduling;
import br.com.susunity.queue.producer.MessageProducer;
import br.com.susunity.queue.producer.dto.MessageBodyForManager;
import br.com.susunity.queue.producer.dto.MessageBodyForPatientRecord;
import br.com.susunity.repository.UnityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Objects.nonNull;

@Service
public class UnityService {

    private final UnityRepository unityRepository;
    private final MessageProducer messageProducer;
    private final SpecialityService specialityService;
    private final ProfessionalService professionalService;

    public UnityService(UnityRepository unityRepository,
                        MessageProducer messageProducer,
                        SpecialityService specialityService,
                        ProfessionalService professionalService) {
        this.unityRepository = unityRepository;
        this.messageProducer = messageProducer;
        this.specialityService = specialityService;
        this.professionalService = professionalService;
    }
    @Transactional
    public UnityDto create(UnityInForm unityInForm) {
        Optional<UnityModel> unity = unityRepository.findByname(unityInForm.name());
        return unity.map(UnityService::getUnityDto)
                .orElseGet(() -> new UnityDto(unityRepository.save(new UnityModel(unityInForm))));
    }

    public List<UnityDto> findAll() {
        List<UnityModel> unityModels = unityRepository.findAll();
        List<UnityDto> unityDtos = new ArrayList<>();
        unityModels.forEach(unity -> unityDtos.add(getUnityDto(unity)));
        return unityDtos;
    }

    public UnityDto getById(UUID id) {
        UnityModel unityModel = unityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return getUnityDto (unityModel);
    }

    public Optional<UnityModel> findUnityById(UUID id) {
        return unityRepository.findById(id);
    }

    @Transactional
    public UnityDto update(UUID id, UnityInForm unityInForm) {
        UnityModel unity = unityRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        unity.merge(unityInForm);
        unity = unityRepository.saveAndFlush(unity);

        return getUnityDto(unity);
    }

    public void delete(UUID id) {
        unityRepository.deleteById(id);
    }

    public UnityDto updateInQuantity(UUID id, Integer quantity) {
        UnityModel unity = unityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        unity.inPatiente(quantity);
        unity=  unityRepository.save(unity);
        return getUnityDto(unity);
    }

    public UnityDto updateOutQuantity(UUID id, Integer quantity) {
        UnityModel unity = unityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        unity.outPatiente(quantity);
        unity = unityRepository.save(unity);
        return getUnityDto(unity);
    }

    private static UnityDto getUnityDto(UnityModel unity) {
        if(nonNull(unity.getProfessional())) {
            List<ProfessionalUnityModel> professional = unity.getProfessional();
            List<ProfessionalOut> professionalOut = new ArrayList<>();
            professional.stream().forEach(professionalUnityModel -> {
                List<String> especilityes = new ArrayList<>();
                professionalUnityModel.getSpeciality().forEach(speciality -> especilityes.add(speciality.getName()));
                professionalOut.add(new ProfessionalOut(professionalUnityModel, especilityes));
            });
            return new UnityDto(unity, professionalOut);
        }
        return new UnityDto(unity);
    }


    public void updateProfessional(Professional messageBody) {
        if(messageBody.isValidProfessional()){
            List<SpecialityModel> specialityModels = findSpeciality(messageBody.getSpeciality());
            UnityModel unityModel = unityRepository.findById(messageBody.getUnityId()).orElseThrow(EntityNotFoundException::new);
            unityModel.setProfessional(professionalService.save(messageBody,specialityModels));
            unityRepository.save(unityModel);
        }
    }

    private List<SpecialityModel> findSpeciality(List<Speciality> speciality) {
        return specialityService.findAllSpecialityes(speciality);
    }

    public void includeProfessional(UnityProfessionalForm unityProfessionalForm) {
        unityRepository.findById(unityProfessionalForm.unityId()).orElseThrow(EntityNotFoundException::new);
        messageProducer.sendToManager(new MessageBodyForManager(unityProfessionalForm));
    }

    public void excludeProfessional(UnityProfessionalForm unityProfessionalForm) {
        UnityModel unityModel = unityRepository.findById(unityProfessionalForm.unityId()).orElseThrow(EntityNotFoundException::new);
        Optional<ProfessionalUnityModel> professional = professionalService.getProfessional(unityProfessionalForm.ProfessionalId());
        if(professional.isPresent()){
            unityModel.remove(professional.get());
            unityRepository.saveAndFlush(unityModel);
        }
    }

    protected boolean validateEspeciality(UnityModel unityModel, MessageBodyByScheduling message) {
        if (unityModel.getProfessional().isEmpty()) {
            return false;
        }

        return unityModel.getProfessional().stream()
                .flatMap(professionalUnityModel -> professionalUnityModel.getSpeciality().stream())
                .map(SpecialityModel::getId)
                .anyMatch(id -> id.equals(message.specialityId()));
    }


    public void getUnityForPatientRecord(MessageBodyByPatientRecord message) {
        unityRepository.findById(message.getUnityId())
                .ifPresentOrElse(unityModel -> {
                    unityModel.getProfessional().stream()
                            .filter(professionalModel -> professionalModel.getProfessionalId().equals(message.getProfessionalId()))
                            .findFirst()
                            .ifPresentOrElse(professionalModel -> {

                                boolean specialityFound = professionalModel.getSpeciality().stream()
                                        .anyMatch(specialityModel -> specialityModel.getId().equals(message.getSpecialityId()));

                                String specialityName = specialityFound ? professionalModel.getSpeciality().stream()
                                        .filter(specialityModel -> specialityModel.getId().equals(message.getSpecialityId()))
                                        .findFirst().get().getName() : null;

                                messageProducer.sendToPatientRecord(new MessageBodyForPatientRecord(
                                        message.getPatientRecordId(),
                                        professionalModel.getProfessionalName(),
                                        unityModel.getName(),
                                        specialityName,
                                        specialityFound
                                        
                                ));
                            }, () -> messageProducer.sendToPatientRecord(new MessageBodyForPatientRecord(
                                    message.getPatientRecordId(),
                                    null,
                                    null,
                                    null,
                                    false
                            )));
                }, () -> messageProducer.sendToPatientRecord(new MessageBodyForPatientRecord(
                        message.getPatientRecordId(),
                        null,
                        null,
                        null,
                        false
                )));
    }

}
