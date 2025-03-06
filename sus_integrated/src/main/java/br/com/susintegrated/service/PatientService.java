package br.com.susintegrated.service;

import br.com.susintegrated.controller.dto.patient.PatientDTO;
import br.com.susintegrated.controller.dto.patient.PatientFormDTO;
import br.com.susintegrated.model.Patient;
import br.com.susintegrated.queue.consumer.dto.MessageBodyByPatienteRecord;
import br.com.susintegrated.queue.consumer.dto.MessageBodyByScheduling;
import br.com.susintegrated.queue.producer.MessageProducer;
import br.com.susintegrated.queue.producer.dto.MessageBodyForPatientRecord;
import br.com.susintegrated.queue.producer.dto.MessageBodyForScheduling;
import br.com.susintegrated.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final MessageProducer messageProducer;

    public PatientService(PatientRepository patientRepository, MessageProducer messageProducer) {
        this.patientRepository = patientRepository;
        this.messageProducer = messageProducer;
    }

    @Transactional
    public PatientDTO register(PatientFormDTO patientFormDTO) {
        Patient patient = new Patient(patientFormDTO);
        patientRepository.save(patient);
        return new PatientDTO(patient);
    }

    @Transactional
    public PatientDTO update(UUID id, PatientFormDTO patientFormDTO) {
        Patient patient = findPatientById(id);
        patient.merge(patientFormDTO);
        return new PatientDTO(patient);
    }

    public Patient findPatientById(UUID patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    public Optional<Patient> findById(UUID id) {
        return patientRepository.findById(id);
    }

    public Page<PatientDTO> listAll(Pageable pageable) {
        return patientRepository.findAll(pageable).map(PatientDTO::new);
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
                    messageProducer.sendToPatientRecord(new MessageBodyForPatientRecord(message.getPatienteRecordId(),true, patient.getName()));
                }, () -> {
                    messageProducer.sendToPatientRecord(new MessageBodyForPatientRecord(message.getPatienteRecordId(),false, null));
                });
    }
}
