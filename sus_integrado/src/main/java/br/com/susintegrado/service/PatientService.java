package br.com.susintegrado.service;

import br.com.susintegrado.controller.dto.patient.PatientDTO;
import br.com.susintegrado.controller.dto.patient.PatientFormDTO;
import br.com.susintegrado.model.address.Address;
import br.com.susintegrado.model.patient.Patient;
import br.com.susintegrado.model.scheduling.Scheduling;
import br.com.susintegrado.repository.PatientRepository;
import br.com.susintegrado.repository.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.susintegrado.model.scheduling.SchedulingStatus.CANCELED;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressService addressService;

    public PatientService(PatientRepository patientRepository,
                          AddressService addressService) {
        this.patientRepository = patientRepository;
        this.addressService = addressService;
    }

    @Transactional
    public PatientDTO register(PatientFormDTO patientFormDTO) {
        Patient patient = new Patient(patientFormDTO);
        Address address = addressService.register(patientFormDTO.address());
//        patient.setAddress(address);
        patientRepository.save(patient);
        return new PatientDTO(patient);
    }

    @Transactional
    public PatientDTO update(UUID id, PatientFormDTO patientFormDTO) {
        Patient patient = findPatientById(id);
        if(!patient.hasAddess()) {
            Address address = addressService.register(patientFormDTO.address());
//            patient.setAddress(address);
        }
//        patient.merge(patientFormDTO);
        return new PatientDTO(patient);
    }

    public Patient findPatientById(UUID patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    public List<PatientDTO> listAll() {
        return patientRepository.findAll().stream().map(PatientDTO::new).toList();
    }

    public Patient findByDocumentOrCreate(String patientName, String patientDocument, String phone) {
        return patientRepository.findByDocument(patientDocument)
                .orElseGet(() -> patientRepository.save(new Patient(patientName, patientDocument, phone)));
    }
}
