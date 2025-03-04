package br.com.susunity.service;

import br.com.susunity.model.ProfessionalType;
import br.com.susunity.model.ProfessionalUnityModel;
import br.com.susunity.model.SpecialityModel;
import br.com.susunity.queue.consumer.dto.manager.MessageBodyByManager;
import br.com.susunity.queue.consumer.dto.manager.Speciality;
import br.com.susunity.repository.ProfessionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessionalServiceTest {

    @Mock
    private ProfessionalRepository professionalRepository;

    @InjectMocks
    private ProfessionalService professionalService;

    private MessageBodyByManager messageBody;
    private List<SpecialityModel> specialityModels;
    private ProfessionalUnityModel professionalUnityModel;
    private UUID professionalId;

    @BeforeEach
    void setUp() {
        professionalId = UUID.randomUUID();
        messageBody = new MessageBodyByManager(professionalId, "Test Professional", ProfessionalType.DOCTOR, Arrays.asList(new Speciality(), new Speciality()),true,UUID.randomUUID());
        specialityModels = Arrays.asList(new SpecialityModel(new Speciality()), new SpecialityModel(new Speciality()));
        professionalUnityModel = new ProfessionalUnityModel(messageBody);
        professionalId = professionalUnityModel.getProfessionalId();
    }

    @Test
    void save_ShouldReturnExistingProfessionalUnity_WhenProfessionalExists() {
        when(professionalRepository.findByProfessionalId(professionalId)).thenReturn(Optional.of(professionalUnityModel));

        ProfessionalUnityModel result = professionalService.save(messageBody, specialityModels);

        assertNotNull(result);
        assertEquals(professionalId, result.getProfessionalId());
        verify(professionalRepository, never()).save(any(ProfessionalUnityModel.class));
    }

    @Test
    void getProfessional_ShouldReturnOptionalProfessionalUnityModel_WhenProfessionalExists() {
        when(professionalRepository.findByProfessionalId(professionalId)).thenReturn(Optional.of(professionalUnityModel));

        Optional<ProfessionalUnityModel> result = professionalService.getProfessional(professionalId);

        assertTrue(result.isPresent());
        assertEquals(professionalId, result.get().getProfessionalId());
    }

    @Test
    void getProfessional_ShouldReturnEmptyOptional_WhenProfessionalDoesNotExist() {
        when(professionalRepository.findByProfessionalId(professionalId)).thenReturn(Optional.empty());

        Optional<ProfessionalUnityModel> result = professionalService.getProfessional(professionalId);

        assertFalse(result.isPresent());
    }

    @Test
    void getProfessionalById_ShouldReturnOptionalProfessionalUnityModel_WhenProfessionalExists() {
        when(professionalRepository.findById(professionalId)).thenReturn(Optional.of(professionalUnityModel));

        Optional<ProfessionalUnityModel> result = professionalService.getProfessionalById(professionalId);

        assertTrue(result.isPresent());
        assertEquals(professionalId, result.get().getProfessionalId());
    }

    @Test
    void getProfessionalById_ShouldReturnEmptyOptional_WhenProfessionalDoesNotExist() {
        when(professionalRepository.findById(professionalId)).thenReturn(Optional.empty());

        Optional<ProfessionalUnityModel> result = professionalService.getProfessionalById(professionalId);

        assertFalse(result.isPresent());
    }

    @Test
    void validateProfessional_ShouldReturnTrue_WhenProfessionalExists() {
        when(professionalRepository.existsById(professionalId)).thenReturn(true);

        boolean result = professionalService.validateProfessional(professionalId);

        assertTrue(result);
    }

    @Test
    void validateProfessional_ShouldReturnFalse_WhenProfessionalDoesNotExist() {
        when(professionalRepository.existsById(professionalId)).thenReturn(false);

        boolean result = professionalService.validateProfessional(professionalId);

        assertFalse(result);
    }

    @Test
    void saveProfessional_ShouldSaveProfessionalUnityModel() {
        professionalService.saveProfessional(professionalUnityModel);

        verify(professionalRepository, times(1)).save(professionalUnityModel);
    }
}