package br.com.susintegrated.controller;

import br.com.susintegrated.controller.dto.address.AddressFormDTO;
import br.com.susintegrated.controller.dto.patient.PatientDTO;
import br.com.susintegrated.controller.dto.patient.PatientFormDTO;
import br.com.susintegrated.model.Patient;
import br.com.susintegrated.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private PatientDTO patientDTO;
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
        patientDTO = new PatientDTO(patient);
    }

    @Test
    void register_shouldReturnCreatedAndPatientDTO() {
        when(patientService.register(formDTO)).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.register(formDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).register(formDTO);
    }

    @Test
    void search_shouldReturnOkAndPatientDTO() {
        when(patientService.findPatientById(patientId)).thenReturn(patient);

        ResponseEntity<PatientDTO> response = patientController.search(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).findPatientById(patientId);
    }

    @Test
    void listAll_shouldReturnOkAndListOfPatientDTOs() {
        List<PatientDTO> patientDTOList = List.of(patientDTO);
        when(patientService.listAll()).thenReturn(patientDTOList);

        ResponseEntity<List<PatientDTO>> response = patientController.listAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientDTOList, response.getBody());
        verify(patientService, times(1)).listAll();
    }

    @Test
    void update_shouldReturnOkAndUpdatedPatientDTO() {
        PatientFormDTO updateFormDTO = new PatientFormDTO(patientName, patientDocument, phone, Optional.of("email@gmail.com"),new AddressFormDTO("street",123,"neighborhood","city","state","123456-000"));
        PatientDTO updatedPatientDTO = new PatientDTO(UUID.randomUUID(), "Jane Doe", "987654321", "098-765-4321");
        when(patientService.update(patientId, updateFormDTO)).thenReturn(updatedPatientDTO);

        ResponseEntity<PatientDTO> response = patientController.update(patientId, updateFormDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPatientDTO, response.getBody());
        verify(patientService, times(1)).update(patientId, updateFormDTO);
    }
}