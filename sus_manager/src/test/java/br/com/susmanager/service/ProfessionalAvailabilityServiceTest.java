package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.*;
import br.com.susmanager.helper.ProfessionalHelper;
import br.com.susmanager.model.ProfessionalAvailabilityModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.repository.ProfessionalAvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessionalAvailabilityServiceTest {

    @Mock
    private ProfessionalManagerService professionalService;

    @Mock
    private ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    @InjectMocks
    private ProfessionalAvailabilityService professionalAvailabilityService;

    private ProfessionalHelper helper = new ProfessionalHelper();

    @Test
    void testRegisterAvailability() {
        UUID professionalId = UUID.randomUUID();
        LocalDateTime availableTime = LocalDateTime.now();
        ProfessionalAvailabilityFormDTO formDTO = new ProfessionalAvailabilityFormDTO(professionalId, availableTime);
        ProfessionalModel professional = new ProfessionalModel();
        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel(professional, availableTime);

        when(professionalService.findProfessionalById(professionalId)).thenReturn(professional);
        when(professionalAvailabilityRepository.save(any(ProfessionalAvailabilityModel.class))).thenReturn(availability);

        ProfessionalAvailabilityDTO dto = professionalAvailabilityService.registerAvailability(formDTO);

        assertNotNull(dto);
        assertEquals(availableTime, dto.availableTime());
        verify(professionalService).findProfessionalById(professionalId);
        verify(professionalAvailabilityRepository).save(any(ProfessionalAvailabilityModel.class));
    }


    @Test
    void testListAvailabilitiesByProfessionalId() {
        UUID professionalId = UUID.randomUUID();


        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel(helper.createProfessionalModel(), LocalDateTime.now());
        List<ProfessionalAvailabilityModel> avail = List.of(availability);

        when(professionalAvailabilityRepository.findByProfessionalId(professionalId)).thenReturn(avail);

        List<ProfessionalAvailabilityDTO> dtos = professionalAvailabilityService.listAvailabilitiesByProfessionalId(professionalId);

        assertEquals(1, dtos.size());
        verify(professionalAvailabilityRepository).findByProfessionalId(professionalId);
    }


    @Test
    void testListAvailabilitiesByDate() {
        LocalDate date = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        ProfessionalAvailabilityModel availability1 = new ProfessionalAvailabilityModel(helper.createProfessionalModel(), now);
        ProfessionalAvailabilityModel availability2 = new ProfessionalAvailabilityModel(helper.createProfessionalModel(), now);
        List<ProfessionalAvailabilityModel> availabilities = List.of(availability1, availability2);

        when(professionalAvailabilityRepository.findByAvailableByDate(date)).thenReturn(availabilities);

        List<ProfessionalAvailabilityDTO> dtos = professionalAvailabilityService.listAvailabilitiesByDate(date);

        assertEquals(2, dtos.size());
        assertEquals(now, dtos.get(0).availableTime());
        assertEquals(now, dtos.get(1).availableTime());
        verify(professionalAvailabilityRepository).findByAvailableByDate(date);
    }

    @Test
    void testListAvailabilitiesByHour() {
        int hour = 9;

        LocalDateTime now = LocalDateTime.now();
        ProfessionalAvailabilityModel availability1 = new ProfessionalAvailabilityModel(helper.createProfessionalModel(), now.withHour(hour));
        ProfessionalAvailabilityModel availability2 = new ProfessionalAvailabilityModel(helper.createProfessionalModel(), now.withHour(hour));
        List<ProfessionalAvailabilityModel> availabilities = List.of(availability1, availability2);

        when(professionalAvailabilityRepository.findByAvailableByHour(hour)).thenReturn(availabilities);

        List<ProfessionalAvailabilityDTO> dtos = professionalAvailabilityService.listAvailabilitiesByHour(hour);

        assertEquals(2, dtos.size());
        assertEquals(hour, dtos.get(0).availableTime().getHour());
        assertEquals(hour, dtos.get(1).availableTime().getHour());

        verify(professionalAvailabilityRepository).findByAvailableByHour(hour);
    }



    @Test
    void testUpdateAvailability_NotFound() {
        UUID id = UUID.randomUUID();
        ProfessionalAvailabilityFormDTO formDTO = new ProfessionalAvailabilityFormDTO(UUID.randomUUID(), LocalDateTime.now());

        when(professionalAvailabilityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> professionalAvailabilityService.updateAvailability(id, formDTO));

        verify(professionalAvailabilityRepository).findById(id);
        verify(professionalAvailabilityRepository, never()).save(any());
    }

    @Test
    void testDeleteAvailability() {
        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel();

        professionalAvailabilityService.deleteAvailability(availability);

        verify(professionalAvailabilityRepository).delete(availability);
    }


}