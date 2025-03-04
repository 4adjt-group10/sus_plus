package br.com.sus_scheduling.controller;

import br.com.sus_scheduling.controller.dto.scheduling.SchedulingDTO;
import br.com.sus_scheduling.controller.dto.scheduling.SchedulingFormDTO;
import br.com.sus_scheduling.controller.dto.scheduling.SchedulingUpdateDTO;
import br.com.sus_scheduling.model.Scheduling;
import br.com.sus_scheduling.service.SchedulingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.sus_scheduling.model.SchedulingStatus.ATTENDING;
import static br.com.sus_scheduling.model.SchedulingStatus.SCHEDULED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulingControllerTest {

    @Mock
    private SchedulingService schedulingService;

    @InjectMocks
    private SchedulingController schedulingController;

    private SchedulingFormDTO formDTO;
    private SchedulingDTO schedulingDTO;
    private SchedulingUpdateDTO updateDTO;
    private UUID schedulingId;
    private UUID patientId;
    private UUID professionalId;
    private LocalDate date;
    private UUID unityId;
    private UUID specialityId;
    private LocalDateTime appointment;
    private Scheduling scheduling;



    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();
        unityId = UUID.randomUUID();
        professionalId = UUID.randomUUID();
        specialityId = UUID.randomUUID();
        appointment = LocalDateTime.now();


        formDTO = new SchedulingFormDTO(patientId, specialityId, unityId, professionalId, appointment, SCHEDULED, "OBSERVATION");
        scheduling = new Scheduling(formDTO);
        schedulingId = scheduling.getId();
        schedulingDTO = new SchedulingDTO(schedulingId,appointment.toString(),SCHEDULED);
        updateDTO = new SchedulingUpdateDTO(schedulingId, Optional.ofNullable(appointment),ATTENDING);
    }

    @Test
    void schedulerRegister_shouldReturnCreatedAndSchedulingDTO() {
        when(schedulingService.register(formDTO)).thenReturn(schedulingDTO);

        ResponseEntity<SchedulingDTO> response = schedulingController.schedulerRegister(formDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(schedulingDTO, response.getBody());
        verify(schedulingService, times(1)).register(formDTO);
    }

    @Test
    void listByPatient_shouldReturnOkAndListOfSchedulingDTOs() {
        List<SchedulingDTO> schedulingDTOList = List.of(schedulingDTO);
        when(schedulingService.findAllByPatientId(patientId, Optional.empty())).thenReturn(schedulingDTOList);

        ResponseEntity<List<SchedulingDTO>> response = schedulingController.listByPatient(patientId, Optional.empty());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schedulingDTOList, response.getBody());
        verify(schedulingService, times(1)).findAllByPatientId(patientId, Optional.empty());
    }


    @Test
    void listByProfessional_shouldReturnOkAndListOfSchedulingDTOs() {
        List<SchedulingDTO> schedulingDTOList = List.of(schedulingDTO);
        when(schedulingService.findAllByProfessionalId(professionalId, Optional.empty())).thenReturn(schedulingDTOList);

        ResponseEntity<List<SchedulingDTO>> response = schedulingController.listByProfessional(professionalId, Optional.empty());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schedulingDTOList, response.getBody());
        verify(schedulingService, times(1)).findAllByProfessionalId(professionalId, Optional.empty());
    }



    @Test
    void done_shouldReturnOkAndDoneSchedulingDTO() {
        when(schedulingService.done(schedulingId)).thenReturn(schedulingDTO);

        ResponseEntity<SchedulingDTO> response = schedulingController.done(schedulingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schedulingDTO, response.getBody());
        verify(schedulingService, times(1)).done(schedulingId);
    }
}
