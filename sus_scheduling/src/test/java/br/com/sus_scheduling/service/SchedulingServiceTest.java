package br.com.sus_scheduling.service;

import br.com.sus_scheduling.controller.dto.scheduling.*;
import br.com.sus_scheduling.model.Scheduling;
import br.com.sus_scheduling.model.SchedulingStatus;
import br.com.sus_scheduling.queue.consumer.dto.*;
import br.com.sus_scheduling.queue.producer.MessageProducer;
import br.com.sus_scheduling.queue.producer.dto.*;
import br.com.sus_scheduling.repository.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.sus_scheduling.model.SchedulingStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulingServiceTest {

    @Mock
    private SchedulingRepository schedulingRepository;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private Logger logger;

    @InjectMocks
    private SchedulingService schedulingService;

    private Scheduling scheduling;
    private SchedulingFormDTO formDTO;
    private UUID schedulingId;
    private UUID patientId;
    private UUID unityId;
    private UUID professionalId;
    private UUID specialityId;
    private LocalDateTime appointment;

    @BeforeEach
    void setUp() {

        patientId = UUID.randomUUID();
        unityId = UUID.randomUUID();
        professionalId = UUID.randomUUID();
        specialityId = UUID.randomUUID();
        appointment = LocalDateTime.now().plusDays(1);

        formDTO = new SchedulingFormDTO(patientId, specialityId, unityId, professionalId, appointment, SCHEDULED, "OBSERVATION");
        scheduling = new Scheduling(formDTO);
        schedulingId = scheduling.getId();
    }

    @Test
    void register_shouldSaveSchedulingAndSendMessage() {
        when(schedulingRepository.save(any(Scheduling.class))).thenReturn(scheduling);

        SchedulingDTO result = schedulingService.register(formDTO);

        assertNotNull(result);
        assertEquals(schedulingId, result.id());
        verify(schedulingRepository, times(1)).save(any(Scheduling.class));
        verify(messageProducer, times(1)).sendToIntegrated(any(MessageBodyForIntegrated.class));
    }

    @Test
    void postValidateIntegrated_shouldApproveAndSendMessageToUnity() {
        MessageBodyByIntegrated message = new MessageBodyByIntegrated(true, schedulingId);
        when(schedulingRepository.findById(schedulingId)).thenReturn(Optional.of(scheduling));

        schedulingService.postValidateIntegrated(message);

        verify(messageProducer, times(1)).sendToUnity(any(MessageBodyForUnity.class));
    }

    @Test
    void postValidateIntegrated_shouldCancelAndSendEmailWhenNotValidated() {
        MessageBodyByIntegrated message = new MessageBodyByIntegrated(false,schedulingId);
        when(schedulingRepository.findById(schedulingId)).thenReturn(Optional.of(scheduling));

        schedulingService.postValidateIntegrated(message);

        assertEquals(CANCELED, scheduling.getStatus());
        verify(schedulingRepository, times(1)).save(scheduling);
    }

    @Test
    void postValidateUnity_shouldApproveAndSaveScheduling() {
        MessageBodyByUnity message = new MessageBodyByUnity(true,true,true,true,schedulingId);
        when(schedulingRepository.findById(schedulingId)).thenReturn(Optional.of(scheduling));

        schedulingService.postValidateUnity(message);

        assertEquals(SchedulingStatus.SCHEDULED, scheduling.getStatus());
        verify(schedulingRepository, times(1)).save(scheduling);
    }

    @Test
    void postValidateUnity_shouldCancelAndSendEmailWhenNotValidated() {
        MessageBodyByUnity message = new MessageBodyByUnity(false,true,true,true,schedulingId);
        when(schedulingRepository.findById(schedulingId)).thenReturn(Optional.of(scheduling));

        schedulingService.postValidateUnity(message);

        assertEquals(CANCELED, scheduling.getStatus());
        verify(schedulingRepository, times(1)).save(scheduling);
    }


    @Test
    void findAllByPatientId_shouldReturnListOfSchedulingDTOs() {
        when(schedulingRepository.findAllByPatientId(patientId)).thenReturn(List.of(scheduling));

        List<SchedulingDTO> result = schedulingService.findAllByPatientId(patientId, Optional.empty());

        assertFalse(result.isEmpty());
        assertEquals(schedulingId, result.get(0).id());
    }

    @Test
    void findAllByPatientIdAndDate_shouldReturnFilteredList() {
        LocalDate date = LocalDate.now();
        when(schedulingRepository.findAllByPatientIdAndDate(patientId, date)).thenReturn(List.of(scheduling));

        List<SchedulingDTO> result = schedulingService.findAllByPatientId(patientId, Optional.of(date));

        assertFalse(result.isEmpty());
        assertEquals(schedulingId, result.get(0).id());
    }

    @Test
    void findAllByProfessionalId_shouldReturnListOfSchedulingDTOs() {
        when(schedulingRepository.findAllByProfessionalId(professionalId)).thenReturn(List.of(scheduling));

        List<SchedulingDTO> result = schedulingService.findAllByProfessionalId(professionalId, Optional.empty());

        assertFalse(result.isEmpty());
        assertEquals(schedulingId, result.get(0).id());
    }

    @Test
    void findAllByProfessionalIdAndDate_shouldReturnFilteredList() {
        LocalDate date = LocalDate.now();
        when(schedulingRepository.findAllByProfessionalIdAndDate(professionalId, date)).thenReturn(List.of(scheduling));

        List<SchedulingDTO> result = schedulingService.findAllByProfessionalId(professionalId, Optional.of(date));

        assertFalse(result.isEmpty());
        assertEquals(schedulingId, result.get(0).id());
    }

    @Test
    void findById_shouldReturnScheduling() {
        when(schedulingRepository.findById(schedulingId)).thenReturn(Optional.of(scheduling));

        Scheduling result = schedulingService.findById(schedulingId);

        assertEquals(schedulingId, result.getId());
    }

    @Test
    void findById_shouldThrowEntityNotFoundException() {
        when(schedulingRepository.findById(schedulingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schedulingService.findById(schedulingId));
    }


    @Test
    void done_shouldSetSchedulingToDoneAndSave() {
        when(schedulingRepository.findById(schedulingId)).thenReturn(Optional.of(scheduling));

        SchedulingDTO result = schedulingService.done(schedulingId);

        assertEquals(DONE, scheduling.getStatus());
        verify(schedulingRepository, times(1)).save(scheduling);
    }


}
