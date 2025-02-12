package br.com.susmanager.model;

import br.com.susmanager.controller.dto.professional.ProfessionalAvailabilityFormDTO;
import br.com.susmanager.model.ProfessionalAvailabilityModel;
import br.com.susmanager.model.ProfessionalModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProfessionalAvailabilityModelTest {

    @Test
    public void testConstructor() {
        ProfessionalModel professional = Mockito.mock(ProfessionalModel.class);
        LocalDateTime availableTime = LocalDateTime.now();

        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel(professional, availableTime);

        assertNotNull(availability.getId());
        assertEquals(professional, availability.getProfessional());
        assertEquals(availableTime, availability.getAvailableTime());
    }

    @Test
    public void testMerge() {
        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel();

        ProfessionalAvailabilityFormDTO formDTO = Mockito.mock(ProfessionalAvailabilityFormDTO.class);
        LocalDateTime newTime = LocalDateTime.now().plusHours(1);
        when(formDTO.availableTime()).thenReturn(newTime);

        availability.merge(formDTO);

        assertEquals(newTime, availability.getAvailableTime());
    }

    @Test
    public void testGetters() {
        ProfessionalModel professional = Mockito.mock(ProfessionalModel.class);
        when(professional.getName()).thenReturn("Test Professional");
        LocalDateTime availableTime = LocalDateTime.now();

        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel(professional, availableTime);
        UUID id = availability.getId();

        assertEquals(professional, availability.getProfessional());
        assertEquals(availableTime, availability.getAvailableTime());
        assertEquals("Test Professional", availability.getProfessionalName());
        assertNotNull(id);
    }

    @Test
    public void testNoArgsConstructor() {
        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel();
        assertNotNull(availability);
    }
}