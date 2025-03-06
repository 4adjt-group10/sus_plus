package br.com.susintegrated.service;

import br.com.susintegrated.controller.dto.address.AddressFormDTO;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private PatientFormDTO formDTO;
    private UUID patientId;
    private String patientName;
    private String patientDocument;
    private String phone;
    private UUID schedulingId;
    private UUID patientRecordId;

    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();
        patientName = "John Doe";
        patientDocument = "123456789";
        phone = "1234-7890";
        schedulingId = UUID.randomUUID();
        patientRecordId = UUID.randomUUID();

        formDTO = new PatientFormDTO(patientName, patientDocument, phone, Optional.of("email@gmail.com"),new AddressFormDTO("street",123,"neighborhood","city","state","123456-000"));
        patient = new Patient(formDTO);
        patientId=patient.getId();
    }

    @Test
    void register_shouldSavePatientAndReturnPatientDTO() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientDTO result = patientService.register(formDTO);

        assertNotNull(result);
        assertEquals(patientId, result.id());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }


    @Test
    void findPatientById_shouldReturnPatient() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.findPatientById(patientId);

        assertEquals(patientId, result.getId());
    }

    @Test
    void findPatientById_shouldThrowEntityNotFoundException() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> patientService.findPatientById(patientId));
    }

    @Test
    void findById_shouldReturnOptionalPatient() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.findById(patientId);

        assertTrue(result.isPresent());
        assertEquals(patientId, result.get().getId());
    }

//    @Test
//    void listAll_shouldReturnListOfPatientDTOs() {
//        when(patientRepository.findAll()).thenReturn(List.of(patient));
//        Pageable pageable = PageRequest.of(0, 10);
//        List<PatientDTO> result = patientService.listAll(pageable);
//
//        assertFalse(result.isEmpty());
//        assertEquals(patientId, result.get(0).id());
//    }

    @Test
    void findByDocumentOrCreate_shouldReturnExistingPatient() {
        when(patientRepository.findByDocument(patientDocument)).thenReturn(Optional.of(patient));

        Patient result = patientService.findByDocumentOrCreate(patientName, patientDocument, phone);

        assertEquals(patientId, result.getId());
        verify(patientRepository, times(1)).findByDocument(patientDocument);
    }

    @Test
    void findByDocumentOrCreate_shouldCreateAndReturnNewPatient() {
        when(patientRepository.findByDocument(patientDocument)).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.findByDocumentOrCreate(patientName, patientDocument, phone);

        assertEquals(patientId, result.getId());
        verify(patientRepository, times(1)).findByDocument(patientDocument);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void validatePatientToScheduling_shouldSendTrueToScheduling() {
        MessageBodyByScheduling message = new MessageBodyByScheduling(patientId, schedulingId);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        patientService.validatePatientToScheduling(message);

        verify(messageProducer, times(1)).sendToScheduling(new MessageBodyForScheduling(true, schedulingId));
    }

    @Test
    void validatePatientToScheduling_shouldSendFalseToScheduling() {
        MessageBodyByScheduling message = new MessageBodyByScheduling(UUID.randomUUID(), schedulingId);
        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        patientService.validatePatientToScheduling(message);

        verify(messageProducer, times(1)).sendToScheduling(new MessageBodyForScheduling(false, schedulingId));
    }

    @Test
    void validatePatientToPatientRecord_shouldSendTrueToPatientRecord() {
        MessageBodyByPatienteRecord message = new MessageBodyByPatienteRecord(patientId, patientRecordId);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        patientService.validatePatientToPatientRecord(message);

        verify(messageProducer, times(1)).sendToPatientRecord(new MessageBodyForPatientRecord(patientRecordId, true, patientName));
    }

    @Test
    void validatePatientToPatientRecord_shouldSendFalseToPatientRecord() {
        MessageBodyByPatienteRecord message = new MessageBodyByPatienteRecord(UUID.randomUUID(), patientRecordId);
        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        patientService.validatePatientToPatientRecord(message);

        verify(messageProducer, times(1)).sendToPatientRecord(new MessageBodyForPatientRecord(patientRecordId, false, null));
    }
}
