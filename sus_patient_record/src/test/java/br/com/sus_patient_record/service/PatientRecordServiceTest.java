package br.com.sus_patient_record.service;

import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
import br.com.suspatientrecord.controller.dto.PatientRecordOutDTO;
import br.com.suspatientrecord.model.PatientRecordModel;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByIntegrated;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByUnity;
import br.com.suspatientrecord.queue.producer.MessageProducer;
import br.com.suspatientrecord.queue.producer.dto.RecordToIntegrated;
import br.com.suspatientrecord.queue.producer.dto.RecordToUnity;
import br.com.suspatientrecord.repository.PatientRecordRepository;
import br.com.suspatientrecord.service.PatientRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientRecordServiceTest {

    @InjectMocks
    private PatientRecordService patientRecordService;

    @Mock
    private PatientRecordRepository patientRecordRepository;

    @Mock
    private MessageProducer messageProducer;

    @Captor
    private ArgumentCaptor<RecordToIntegrated> recordToIntegratedCaptor;

    @Captor
    private ArgumentCaptor<RecordToUnity> recordToUnityCaptor;


    @Test
    public void testCreatePatientRecord() {
        // Arrange
        PatientRecordFormDTO formDTO = new PatientRecordFormDTO(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),"paciente ok");
        PatientRecordModel model = new PatientRecordModel(formDTO);
        when(patientRecordRepository.save(any(PatientRecordModel.class))).thenReturn(model);

        // Act
        PatientRecordOutDTO outDTO = patientRecordService.createPatientRecord(formDTO);

        // Assert
        assertNotNull(outDTO);
        assertEquals(model.getId(), outDTO.id());
        assertEquals(model.getPatientName(), outDTO.patientName());

        verify(messageProducer).sendToIntegrated(recordToIntegratedCaptor.capture());
        RecordToIntegrated capturedRecord = recordToIntegratedCaptor.getValue();
        assertEquals(model.getPatientId(), capturedRecord.getPatientId());
        assertEquals(model.getId(), capturedRecord.getPatienteRecordId());
    }

    @Test
    public void testUpdatePatientRecoedByIntegrated_Valid() {
        // Arrange
        UUID recordId = UUID.randomUUID();
        MessageBodyByIntegrated message = new MessageBodyByIntegrated(recordId,"Updated Patient Name", true);

        PatientRecordModel model = new PatientRecordModel();
        when(patientRecordRepository.findById(recordId)).thenReturn(Optional.of(model));

        // Act
        patientRecordService.updatePatientRecoedByIntegrated(message);

        // Assert
        assertEquals(message.getPatientName(), model.getPatientName());
        verify(patientRecordRepository).save(model);
        verify(messageProducer).sendToUnity(recordToUnityCaptor.capture());



    }

    @Test
    public void testUpdatePatientRecoedByIntegrated_Invalid() {
        // Arrange
        UUID recordId = UUID.randomUUID();
        MessageBodyByIntegrated message = new MessageBodyByIntegrated(recordId,"name", false);

        // Act
        patientRecordService.updatePatientRecoedByIntegrated(message);

        // Assert
        verify(patientRecordRepository).deleteById(recordId);
        verify(messageProducer, never()).sendToUnity(any());
    }

    @Test
    public void testUpdatePatientRecoedByUnity_Valid() {
        // Arrange
        UUID recordId = UUID.randomUUID();
        MessageBodyByUnity message = new MessageBodyByUnity(recordId,"Updated Unity Name","Updated Speciality","Updated Professional",true);

        PatientRecordModel model = new PatientRecordModel();
        when(patientRecordRepository.findById(recordId)).thenReturn(Optional.of(model));

        // Act
        patientRecordService.updatePatientRecoedByUnity(message);

        // Assert
        assertEquals(message.getUnityName(), model.getUnityName());
        assertEquals(message.getSpecilityName(), model.getSpecialityName());
        assertEquals(message.getProfessionalName(), model.getProfessionName());
        verify(patientRecordRepository).save(model);
    }

    @Test
    public void testUpdatePatientRecoedByUnity_Invalid() {
        // Arrange
        UUID recordId = UUID.randomUUID();
        MessageBodyByUnity message = new MessageBodyByUnity(recordId,"professionalName","unityName","SpecialityName",false);

        // Act
        patientRecordService.updatePatientRecoedByUnity(message);

        // Assert
        verify(patientRecordRepository).deleteById(recordId);
    }


    @Test
    public void testGetPatientRecordById_NotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(patientRecordRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> patientRecordService.getPatientRecordById(id));
    }
}
