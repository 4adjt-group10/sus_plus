package br.com.susunity.service;

import br.com.susunity.model.ProfissionalUnityModel;
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
    private ProfissionalService profissionalService;

    @Mock
    private ProfessionalRepository professionalRepository;

    @Test
    public void testSave_NewProfessional() {
        // Arrange
        Professional messageBody = new Professional(UUID.randomUUID());
        List<SpecialityModel> specialityModels = List.of(new SpecialityModel(), new SpecialityModel());

        ProfissionalUnityModel expectedProfessional = new ProfissionalUnityModel(messageBody);
        expectedProfessional.setSpeciality(specialityModels);

        when(professionalRepository.findByProfissionalId(messageBody.getProfissionalId())).thenReturn(Optional.empty());
        when(professionalRepository.save(any(ProfissionalUnityModel.class))).thenReturn(expectedProfessional);

        // Act
        ProfissionalUnityModel savedProfessional = profissionalService.save(messageBody, specialityModels);

        // Assert
        assertNotNull(savedProfessional);
        assertEquals(messageBody.getProfissionalName(), savedProfessional.getProfissionalName());
        assertEquals(specialityModels, savedProfessional.getSpeciality());
        verify(professionalRepository, times(2)).save(any(ProfissionalUnityModel.class));
    }

    @Test
    public void testSave_ExistingProfessional() {
        // Arrange
        Professional messageBody = new Professional();
        List<SpecialityModel> specialityModels = List.of(new SpecialityModel(), new SpecialityModel());

        ProfissionalUnityModel existingProfessional = new ProfissionalUnityModel(messageBody);
        existingProfessional.setSpeciality(List.of(new SpecialityModel()));
        when(professionalRepository.findByProfissionalId(messageBody.getProfissionalId())).thenReturn(Optional.of(existingProfessional));

        // Act
        ProfissionalUnityModel savedProfessional = profissionalService.save(messageBody, specialityModels);

        // Assert
        assertEquals(existingProfessional, savedProfessional);
        verify(professionalRepository, times(1)).findByProfissionalId(messageBody.getProfissionalId());
        verify(professionalRepository, never()).save(any(ProfissionalUnityModel.class));
    }

    @Test
    public void testGetProfessional_Exists() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        ProfissionalUnityModel professional = new ProfissionalUnityModel(new Professional());
        when(professionalRepository.findById(uuid)).thenReturn(Optional.of(professional));

        // Act
        Optional<ProfissionalUnityModel> result = profissionalService.getProfessional(uuid);

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
        Optional<ProfissionalUnityModel> result = profissionalService.getProfessional(uuid);

        // Assert
        assertTrue(result.isEmpty());
        verify(professionalRepository, times(1)).findById(uuid);
    }
}