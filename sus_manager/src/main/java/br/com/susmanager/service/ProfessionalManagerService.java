package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.exception.DoctorException;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.model.ProfessionalAvailabilityModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.repository.ProfessionalAvailabilityRepository;
import br.com.susmanager.repository.ProfessionalManagerRepository;
import br.com.susmanager.repository.SpecialityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProfessionalManagerService {
    @Autowired
    ProfessionalManagerRepository professionalRepository;
    @Autowired
    SpecialityRepository speciality;
    @Autowired
    AddressService addressService;
    @Autowired
    ProfessionalAvailabilityRepository professionalAvailabilityRepository;
    @Autowired
    private SpecialityRepository specialityRepository;

    public ProfessionalManagerOut register(ProfessionalCreateForm form) {
        List<SpecialityModel> specialities = this.speciality.findAllById(form.specialityIds() != null ? form.specialityIds() : new ArrayList<>());
        ProfessionalModel professional = new ProfessionalModel(form);
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

    public ProfessionalManagerOut findById(UUID ProfissionalId) {
        ProfessionalModel doctor = getDoctorModel(ProfissionalId);
        return new ProfessionalManagerOut(doctor);
    }

    public void deleById(UUID ProfissionalId) {
        professionalRepository.deleteById(ProfissionalId);
    }

    @Transactional
    public ProfessionalManagerOut update(UUID id, ProfessionalCreateForm professionalFormDTO) {
        List<SpecialityModel> procedures = specialityRepository.findAllById(professionalFormDTO.specialityIds());
        ProfessionalModel professional = findProfessionalById(id);
        professionalAvailabilityRepository.deleteAll(professional.getAvailability());
        professional.merge(professionalFormDTO, procedures);
        professionalFormDTO.availabilities()
                .forEach(availability -> professionalAvailabilityRepository.save(new ProfessionalAvailabilityModel(professional, availability)));
        procedures.forEach(procedure -> procedure.addProfessional(professional));
        professionalRepository.save(professional);
        return new ProfessionalManagerOut(professional);
    }

    private ProfessionalModel getDoctorModel(UUID ProfissionalId) {
        ProfessionalModel doctor = professionalRepository.findById(ProfissionalId).
                orElseThrow(() -> new DoctorException("Doctor record not found"));
        return doctor;
    }
}
