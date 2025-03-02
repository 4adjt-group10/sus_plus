package br.com.sus_patient_record.controller;

import br.com.suspatientrecord.controller.PatientRecordController;
import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
import br.com.suspatientrecord.controller.dto.PatientRecordOutDTO;
import br.com.suspatientrecord.service.PatientRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientRecordControllerTest {

    @InjectMocks
    private PatientRecordController patientRecordController;

    @Mock
    private PatientRecordService patientRecordService;

    @Test
    public void testCreatePatientRecord() {
        // Arrange
        PatientRecordFormDTO formDTO = new PatientRecordFormDTO(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),"paciente ok");
        PatientRecordOutDTO outDTO = new PatientRecordOutDTO(UUID.randomUUID(),"Karen","Doutor","Médico","Pirituba","paciente ok");
        when(patientRecordService.createPatientRecord(formDTO)).thenReturn(outDTO);

        // Act
        ResponseEntity<PatientRecordOutDTO> response = patientRecordController.createPatientRecord(formDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outDTO, response.getBody());
        verify(patientRecordService, times(1)).createPatientRecord(formDTO);
    }

    @Test
    public void testGetPatientRecordById() {
        // Arrange
        UUID id = UUID.randomUUID();
        PatientRecordOutDTO outDTO = new PatientRecordOutDTO(UUID.randomUUID(),"Karen","Doutor","Médico","Pirituba","paciente ok");
        when(patientRecordService.getPatientRecordById(id)).thenReturn(outDTO);

        // Act
        ResponseEntity<PatientRecordOutDTO> response = patientRecordController.getPatientRecordById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outDTO, response.getBody());
        verify(patientRecordService, times(1)).getPatientRecordById(id);
    }



}