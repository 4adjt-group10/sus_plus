package br.com.susunity.service;

import br.com.susunity.controller.dto.unity.AddressFormDTO;
import br.com.susunity.controller.dto.unity.UnityInForm;
import br.com.susunity.controller.dto.unity.UnityDto;
import br.com.susunity.model.UnityModel;
import br.com.susunity.repository.UnityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnityServiceTest {

    @Mock
    private UnityRepository unityRepository;


    @InjectMocks
    private UnityService unityService;

    private UnityModel unityModel;
    private UnityInForm unityInForm;
    private UUID unityId;
    private UUID professionalId;
    private UUID specialityId;

    @BeforeEach
    void setUp() {
        unityId = UUID.randomUUID();
        professionalId = UUID.randomUUID();
        specialityId = UUID.randomUUID();
        unityInForm = new UnityInForm("Test Unity", 1, new AddressFormDTO("street", 123, "neighborhood", "city", "state", "123456-000"));
        unityModel = new UnityModel(unityInForm);
        unityId = unityModel.getId();
    }

    @Test
    void create_ShouldCreateNewUnity_WhenUnityDoesNotExist() {
        when(unityRepository.findByname(unityInForm.name())).thenReturn(Optional.empty());
        when(unityRepository.save(any(UnityModel.class))).thenReturn(unityModel);

        UnityDto result = unityService.create(unityInForm);

        assertNotNull(result);
        assertEquals(unityId, result.id());
        assertEquals(unityInForm.name(), result.name());
        verify(unityRepository, times(1)).save(any(UnityModel.class));
    }

    @Test
    void create_ShouldReturnExistingUnityDto_WhenUnityExists() {
        when(unityRepository.findByname(unityInForm.name())).thenReturn(Optional.of(unityModel));

        UnityDto result = unityService.create(unityInForm);

        assertNotNull(result);
        assertEquals(unityId, result.id());
        assertEquals(unityInForm.name(), result.name());
        verify(unityRepository, never()).save(any(UnityModel.class));
    }

    @Test
    void findAll_ShouldReturnListOfUnityDtos() {
        when(unityRepository.findAll()).thenReturn(Collections.singletonList(unityModel));

        List<UnityDto> result = unityService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(unityId, result.get(0).id());
    }

    @Test
    void getById_ShouldReturnUnityDto_WhenUnityExists() {
        when(unityRepository.findById(unityId)).thenReturn(Optional.of(unityModel));

        UnityDto result = unityService.getById(unityId);

        assertNotNull(result);
        assertEquals(unityId, result.id());
    }

    @Test
    void getById_ShouldThrowEntityNotFoundException_WhenUnityDoesNotExist() {
        when(unityRepository.findById(unityId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unityService.getById(unityId));
    }

    @Test
    void findUnityById_ShouldReturnOptionalUnityModel_WhenUnityExists() {
        when(unityRepository.findById(unityId)).thenReturn(Optional.of(unityModel));

        Optional<UnityModel> result = unityService.findUnityById(unityId);

        assertTrue(result.isPresent());
        assertEquals(unityId, result.get().getId());
    }

    @Test
    void update_ShouldUpdateUnity_WhenUnityExists() {
        when(unityRepository.findById(unityId)).thenReturn(Optional.of(unityModel));
        when(unityRepository.saveAndFlush(any(UnityModel.class))).thenReturn(unityModel);

        UnityDto result = unityService.update(unityId, unityInForm);

        assertNotNull(result);
        assertEquals(unityId, result.id());
        verify(unityRepository, times(1)).saveAndFlush(any(UnityModel.class));
    }

    @Test
    void update_ShouldThrowEntityNotFoundException_WhenUnityDoesNotExist() {
        when(unityRepository.findById(unityId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unityService.update(unityId, unityInForm));
    }

    @Test
    void delete_ShouldDeleteUnity() {
        unityService.delete(unityId);

        verify(unityRepository, times(1)).deleteById(unityId);
    }

    @Test
    void updateInQuantity_ShouldIncreasePatientQuantity() {
        when(unityRepository.findById(unityId)).thenReturn(Optional.of(unityModel));
        when(unityRepository.save(any(UnityModel.class))).thenReturn(unityModel);

        UnityDto result = unityService.updateInQuantity(unityId, 1);

        assertNotNull(result);
        verify(unityRepository, times(1)).save(any(UnityModel.class));
    }

    @Test
    void updateOutQuantity_ShouldDecreasePatientQuantity() {
        when(unityRepository.findById(unityId)).thenReturn(Optional.of(unityModel));
        when(unityRepository.save(any(UnityModel.class))).thenReturn(unityModel);

        UnityDto result = unityService.updateOutQuantity(unityId, 1);

        assertNotNull(result);
        verify(unityRepository, times(1)).save(any(UnityModel.class));
    }


}