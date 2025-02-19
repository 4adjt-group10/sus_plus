package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.exception.DoctorException;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.model.ProfessionalAvailabilityModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.queue.consumer.dto.unity.UnityProfessional;
import br.com.susmanager.queue.producer.MessageProducer;
import br.com.susmanager.queue.producer.dto.Professional;
import br.com.susmanager.repository.ProfessionalAvailabilityRepository;
import br.com.susmanager.repository.ProfessionalManagerRepository;
import br.com.susmanager.repository.SpecialityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProfessionalManagerService {

    private final ProfessionalManagerRepository professionalRepository;

    private final SpecialityRepository speciality;

    private final AddressService addressService;

    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    private final MessageProducer messageProducer;
    public ProfessionalManagerService(ProfessionalManagerRepository professionalRepository, SpecialityRepository speciality, AddressService addressService, ProfessionalAvailabilityRepository professionalAvailabilityRepository, MessageProducer messageProducer) {
        this.professionalRepository = professionalRepository;
        this.speciality = speciality;
        this.addressService = addressService;
        this.professionalAvailabilityRepository = professionalAvailabilityRepository;
        this.messageProducer = messageProducer;
    }

    public ProfessionalManagerOut register(ProfessionalCreateForm form) {
        List<SpecialityModel> specialities = this.speciality.findAllById(form.specialityIds() != null ? form.specialityIds() : new ArrayList<>());
        ProfessionalModel professional = new ProfessionalModel(form,specialities);
        List<ProfessionalAvailabilityModel> availabilities = form.availabilities()
                .stream()
                .map(availability -> new ProfessionalAvailabilityModel(professional, availability))
                .toList();
        specialities.forEach(speciality -> speciality.addProfessional(professional));
        AddressModel address = addressService.register(form.address());
        professional.setAddress(address);
        professionalRepository.save(professional);
        professionalAvailabilityRepository.saveAll(availabilities);
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
    public ProfessionalManagerOut update(UUID id, ProfessionalCreateForm professionalFormDTO) {
        List<SpecialityModel> procedures = speciality.findAllById(professionalFormDTO.specialityIds());
        ProfessionalModel professional = findProfessionalById(id);
        professionalAvailabilityRepository.deleteAll(professional.getAvailability());
        professional.merge(professionalFormDTO, procedures);
        professionalFormDTO.availabilities()
                .forEach(availability -> professionalAvailabilityRepository.save(new ProfessionalAvailabilityModel(professional, availability)));
        procedures.forEach(procedure -> procedure.addProfessional(professional));
        professionalRepository.save(professional);
        return new ProfessionalManagerOut(professional);
    }
    @Transactional
    public ProfessionalModel getDoctorModel(UUID profissionalId) {
        return professionalRepository.findById(profissionalId).
                orElseThrow(() -> new DoctorException("Doctor record not found"));
    }

    public void findProfessionalMQ(UnityProfessional messageBody) {
        try{
            Professional professional = new Professional(getDoctorModel(messageBody.getProfessionalId()),messageBody.getUnityId());
            messageProducer.sendToUnity(professional);
        }catch (Exception e){
            Professional professional = new Professional(messageBody.getUnityId());
            messageProducer.sendToUnity(professional);
        }
    }
}
