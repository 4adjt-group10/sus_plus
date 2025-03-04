package br.com.susunity.service;

import br.com.susunity.model.ProfessionalUnityModel;
import br.com.susunity.model.SpecialityModel;
import br.com.susunity.queue.consumer.dto.manager.Professional;
import br.com.susunity.repository.ProfessionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfissionalServiceTest {

    @InjectMocks
    private ProfessionalService profissionalService;

    @Mock
    private ProfessionalRepository professionalRepository;

    @Test
    public void testSave_NewProfessional() {
        // Arrange
        Professional messageBody = new Professional(UUID.randomUUID());
        List<SpecialityModel> specialityModels = List.of(new SpecialityModel(), new SpecialityModel());

        ProfessionalUnityModel expectedProfessional = new ProfessionalUnityModel(messageBody);
        expectedProfessional.setSpeciality(specialityModels);

        when(professionalRepository.findByProfessionalId(messageBody.getProfissionalId())).thenReturn(Optional.empty());
        when(professionalRepository.save(any(ProfessionalUnityModel.class))).thenReturn(expectedProfessional);

        // Act
        ProfessionalUnityModel savedProfessional = profissionalService.save(messageBody, specialityModels);

        // Assert
        assertNotNull(savedProfessional);
        assertEquals(messageBody.getProfissionalName(), savedProfessional.getProfessionalName());
        assertEquals(specialityModels, savedProfessional.getSpeciality());
        verify(professionalRepository, times(2)).save(any(ProfessionalUnityModel.class));
    }

    @Test
    public void testSave_ExistingProfessional() {
        // Arrange
        Professional messageBody = new Professional();
        List<SpecialityModel> specialityModels = List.of(new SpecialityModel(), new SpecialityModel());

        ProfessionalUnityModel existingProfessional = new ProfessionalUnityModel(messageBody);
        existingProfessional.setSpeciality(List.of(new SpecialityModel()));
        when(professionalRepository.findByProfessionalId(messageBody.getProfissionalId())).thenReturn(Optional.of(existingProfessional));

        // Act
        ProfessionalUnityModel savedProfessional = profissionalService.save(messageBody, specialityModels);

        // Assert
        assertEquals(existingProfessional, savedProfessional);
        verify(professionalRepository, times(1)).findByProfessionalId(messageBody.getProfissionalId());
        verify(professionalRepository, never()).save(any(ProfessionalUnityModel.class));
    }

    @Test
    public void testGetProfessional_Exists() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        ProfessionalUnityModel professional = new ProfessionalUnityModel(new Professional());
        when(professionalRepository.findById(uuid)).thenReturn(Optional.of(professional));

        // Act
        Optional<ProfessionalUnityModel> result = profissionalService.getProfessional(uuid);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(professional, result.get());
        verify(professionalRepository, times(1)).findById(uuid);
    }

    @Test
    public void testGetProfessional_NotExists() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(professionalRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        Optional<ProfessionalUnityModel> result = profissionalService.getProfessional(uuid);

        // Assert
        assertTrue(result.isEmpty());
        verify(professionalRepository, times(1)).findById(uuid);
    }
}