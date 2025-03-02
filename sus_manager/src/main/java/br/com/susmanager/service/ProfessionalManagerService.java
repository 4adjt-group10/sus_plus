package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.ProfessionalAlterForm;
import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.exception.DoctorException;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.queue.consumer.dto.MessageBodyByUnity;
import br.com.susmanager.queue.producer.MessageProducer;
import br.com.susmanager.queue.producer.dto.MessageBodyForUnity;
import br.com.susmanager.repository.ProfessionalManagerRepository;
import br.com.susmanager.repository.SpecialityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessionalManagerService {

    private final ProfessionalManagerRepository professionalRepository;

    private final SpecialityRepository specialityRepository;

    private final MessageProducer messageProducer;

    public ProfessionalManagerService(ProfessionalManagerRepository professionalRepository,
                                      SpecialityRepository specialityRepository,
                                      MessageProducer messageProducer) {
        this.professionalRepository = professionalRepository;
        this.specialityRepository = specialityRepository;
        this.messageProducer = messageProducer;
    }

    public ProfessionalManagerOut register(ProfessionalCreateForm form) {
        List<SpecialityModel> specialities = this.specialityRepository.findAllById(form.specialityIds() != null ? form.specialityIds() : new ArrayList<>());
        ProfessionalModel professional = new ProfessionalModel(form,specialities);
        specialities.forEach(speciality -> speciality.addProfessional(professional));
        professionalRepository.save(professional);
        return new ProfessionalManagerOut(professional);
    }

    public ProfessionalManagerOut findByDocument(String document) {
        var profissional = professionalRepository.findByDocument(document)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));
        return new ProfessionalManagerOut(profissional);
    }

    public ProfessionalModel findProfessionalById(UUID id) {
        return  professionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

    }

    public List<ProfessionalManagerOut> listAllProfessionals() {
        return professionalRepository.findAll().stream().map(ProfessionalManagerOut::new).toList();
    }

    public ProfessionalManagerOut findById(UUID profissionalId) {
        ProfessionalModel doctor = getDoctorModel(profissionalId);
        return new ProfessionalManagerOut(doctor);
    }

    public void deleById(UUID profissionalId) {
        professionalRepository.deleteById(profissionalId);
    }

    @Transactional
    public ProfessionalManagerOut update(UUID id, ProfessionalAlterForm professionalFormDTO) {
        ProfessionalModel professional = findProfessionalById(id);
        professional.merge(professionalFormDTO);
        professionalRepository.save(professional);
        return new ProfessionalManagerOut(professional);
    }

    @Transactional
    public ProfessionalModel getDoctorModel(UUID profissionalId) {
        return professionalRepository.findById(profissionalId).
                orElseThrow(() -> new DoctorException("Doctor record not found"));
    }

    public void findProfessionalAndSendToUnity(MessageBodyByUnity messageBody) {
        try{
            MessageBodyForUnity messageBodyForUnity = new MessageBodyForUnity(getDoctorModel(messageBody.getProfessionalId()),messageBody.getUnityId());
            messageProducer.sendToUnity(messageBodyForUnity);
        }catch (Exception e){
            MessageBodyForUnity messageBodyForUnity = new MessageBodyForUnity(messageBody.getUnityId());
            messageProducer.sendToUnity(messageBodyForUnity);
        }
    }
    @Transactional
    public void includeSpeciality(UUID professionalId, UUID idSpeciality) {
        Optional<SpecialityModel> specialities = this.specialityRepository.findById(idSpeciality);
        if(specialities.isPresent()){
            ProfessionalModel professional = findProfessionalById(professionalId);
            specialities.get().addProfessional(professional);
            professional.addSpeciality(specialities.get());
            professionalRepository.save(professional);
        }
    }

    public void excludSpeciality(UUID professionalId, UUID idSpeciality) {
        Optional<SpecialityModel> specialities = this.specialityRepository.findById(idSpeciality);
        if(specialities.isPresent()){
            ProfessionalModel professional = findProfessionalById(professionalId);
            specialities.get().addProfessional(professional);
            professional.removeSpeciality(specialities.get());
            professionalRepository.save(professional);
        }
    }
}
