package br.com.susintegrated.service;

import br.com.susintegrated.controller.dto.patient.PatientDTO;
import br.com.susintegrated.controller.dto.patient.PatientFormDTO;
import br.com.susintegrated.model.address.Address;
import br.com.susintegrated.model.patient.Patient;
import br.com.susintegrated.queue.consumer.MessageBodyByScheduling;
import br.com.susintegrated.queue.consumer.dto.MessageBodyByPatienteRecord;
import br.com.susintegrated.queue.producer.MessageBodyForScheduling;
import br.com.susintegrated.queue.producer.MessageProducer;
import br.com.susintegrated.queue.producer.dto.MessageBodyForPatientRecord;
import br.com.susintegrated.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressService addressService;
    private final MessageProducer messageProducer;

    public PatientService(PatientRepository patientRepository,
                          AddressService addressService, MessageProducer messageProducer) {
        this.patientRepository = patientRepository;
        this.addressService = addressService;
        this.messageProducer = messageProducer;
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

    public Optional<Patient> findById(UUID id) {
        return patientRepository.findById(id);
    }

    public List<PatientDTO> listAll() {
        return patientRepository.findAll().stream().map(PatientDTO::new).toList();
    }

    public Patient findByDocumentOrCreate(String patientName, String patientDocument, String phone) {
        return patientRepository.findByDocument(patientDocument)
                .orElseGet(() -> patientRepository.save(new Patient(patientName, patientDocument, phone)));
    }

    public void validatePatientToScheduling(MessageBodyByScheduling message) {
        patientRepository.findById(message.patientId())
                .ifPresentOrElse(patient -> {
                    messageProducer.sendToScheduling(new MessageBodyForScheduling(true, message.schedulingId()));
                }, () -> {
                    messageProducer.sendToScheduling(new MessageBodyForScheduling(false, message.schedulingId()));
                });
    }

    public void validatePatientToPatientRecord(MessageBodyByPatienteRecord message) {
        patientRepository.findById(message.getPatientId())
                .ifPresentOrElse(patient -> {
                    messageProducer.sendToPatientRecord(new MessageBodyForPatientRecord(true, patient.getName()));
                }, () -> {
                    messageProducer.sendToPatientRecord(new MessageBodyForPatientRecord(false, null));
                });
    }
}
