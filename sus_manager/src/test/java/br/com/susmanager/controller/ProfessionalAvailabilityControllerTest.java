package br.com.susmanager.controller;

import br.com.susmanager.controller.dto.professional.*;
import br.com.susmanager.helper.ProfessionalHelper;
import br.com.susmanager.model.ProfessionalAvailabilityModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.service.ProfessionalAvailabilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessionalAvailabilityControllerTest {

    @Mock
    private ProfessionalAvailabilityService availabilityService;

    @InjectMocks
    private ProfessionalAvailabilityController availabilityController;

    @Captor
    private ArgumentCaptor<ProfessionalAvailabilityFormDTO> availabilityFormCaptor;

    private ProfessionalHelper helper;

    @Test
    void testAddRegister() {
        LocalDateTime availableTime = LocalDateTime.now();
        ProfessionalAvailabilityFormDTO formDTO = new ProfessionalAvailabilityFormDTO(UUID.randomUUID(), availableTime);
        ProfessionalAvailabilityDTO availabilityDTO = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), availableTime));

        when(availabilityService.registerAvailability(any(ProfessionalAvailabilityFormDTO.class))).thenReturn(availabilityDTO);

        ResponseEntity<ProfessionalAvailabilityDTO> response = availabilityController.addRegister(formDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(availabilityDTO, response.getBody());

        verify(availabilityService).registerAvailability(availabilityFormCaptor.capture());
        ProfessionalAvailabilityFormDTO capturedForm = availabilityFormCaptor.getValue();
        assertEquals(availableTime, capturedForm.availableTime());
    }

    @Test
    void testListAllAvailabilities() {
        LocalDateTime availableTime = LocalDateTime.now();

        ProfessionalAvailabilityDTO availabilityDTO1 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), availableTime));
        ProfessionalAvailabilityDTO availabilityDTO2 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), availableTime));
        List<ProfessionalAvailabilityDTO> availabilityDTOs = List.of(availabilityDTO1, availabilityDTO2);

        when(availabilityService.listAllAvailabilities()).thenReturn(availabilityDTOs);

        ResponseEntity<List<ProfessionalAvailabilityDTO>> response = availabilityController.listAllAvailabilities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availabilityDTOs, response.getBody());
        assertEquals(2, response.getBody().size());

        verify(availabilityService).listAllAvailabilities();
    }

    @Test
    void testListAvailabilities() {
        UUID professionalId = UUID.randomUUID();
        ProfessionalAvailabilityDTO availabilityDTO1 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        ProfessionalAvailabilityDTO availabilityDTO2 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        List<ProfessionalAvailabilityDTO> availabilityDTOs = List.of(availabilityDTO1, availabilityDTO2);

        when(availabilityService.listAvailabilitiesByProfessionalId(professionalId)).thenReturn(availabilityDTOs);

        ResponseEntity<List<ProfessionalAvailabilityDTO>> response = availabilityController.listAvailabilities(professionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availabilityDTOs, response.getBody());
        assertEquals(2, response.getBody().size());

        verify(availabilityService).listAvailabilitiesByProfessionalId(professionalId);
    }

    @Test
    void testListAvailabilitiesByDate() {
        LocalDate date = LocalDate.now();

        ProfessionalAvailabilityDTO availabilityDTO1 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        ProfessionalAvailabilityDTO availabilityDTO2 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        List<ProfessionalAvailabilityDTO> availabilityDTOs = List.of(availabilityDTO1, availabilityDTO2);

        when(availabilityService.listAvailabilitiesByDate(date)).thenReturn(availabilityDTOs);

        ResponseEntity<List<ProfessionalAvailabilityDTO>> response = availabilityController.listAvailabilitiesByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availabilityDTOs, response.getBody());
        assertEquals(2, response.getBody().size());

        verify(availabilityService).listAvailabilitiesByDate(date);
    }

    @Test
    void testListAvailabilitiesByDayOfWeek() {
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;

        ProfessionalAvailabilityDTO availabilityDTO1 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        ProfessionalAvailabilityDTO availabilityDTO2 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        List<ProfessionalAvailabilityDTO> availabilityDTOs = List.of(availabilityDTO1, availabilityDTO2);

        when(availabilityService.listAvailabilitiesByDayOfWeek(dayOfWeek.getValue())).thenReturn(availabilityDTOs);

        ResponseEntity<List<ProfessionalAvailabilityDTO>> response = availabilityController.listAvailabilitiesByDayOfWeek(dayOfWeek);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availabilityDTOs, response.getBody());
        assertEquals(2, response.getBody().size());

        verify(availabilityService).listAvailabilitiesByDayOfWeek(dayOfWeek.getValue());
    }

    @Test
    void testListAvailabilitiesByHour() {
        int hour = 9;

        ProfessionalAvailabilityDTO availabilityDTO1 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        ProfessionalAvailabilityDTO availabilityDTO2 = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), LocalDateTime.now()));
        List<ProfessionalAvailabilityDTO> availabilityDTOs = List.of(availabilityDTO1, availabilityDTO2);

        when(availabilityService.listAvailabilitiesByHour(hour)).thenReturn(availabilityDTOs);

        ResponseEntity<List<ProfessionalAvailabilityDTO>> response = availabilityController.listAvailabilitiesByHour(hour);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availabilityDTOs, response.getBody());
        assertEquals(2, response.getBody().size());

        verify(availabilityService).listAvailabilitiesByHour(hour);
    }

    @Test
    void testUpdateAvailability() {
        UUID id = UUID.randomUUID();
        LocalDateTime availableTime = LocalDateTime.now();
        ProfessionalAvailabilityFormDTO formDTO = new ProfessionalAvailabilityFormDTO(UUID.randomUUID(), availableTime);
        ProfessionalAvailabilityDTO availabilityDTO = new ProfessionalAvailabilityDTO(new ProfessionalAvailabilityModel(new ProfessionalModel(), availableTime));

        when(availabilityService.updateAvailability(eq(id), any(ProfessionalAvailabilityFormDTO.class))).thenReturn(availabilityDTO);

        ResponseEntity<ProfessionalAvailabilityDTO> response = availabilityController.updateAvailability(id, formDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availabilityDTO, response.getBody());

        verify(availabilityService).updateAvailability(eq(id), availabilityFormCaptor.capture());
        ProfessionalAvailabilityFormDTO capturedForm = availabilityFormCaptor.getValue();
        assertEquals(availableTime, capturedForm.availableTime());
    }
}