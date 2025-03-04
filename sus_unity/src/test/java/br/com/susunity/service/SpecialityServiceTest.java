package br.com.susunity.service;

import br.com.susunity.model.ProfessionalUnityModel;
import br.com.susunity.model.SpecialityModel;
import br.com.susunity.queue.consumer.dto.manager.Speciality;
import br.com.susunity.repository.SpecialityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecialityServiceTest {

    @Mock
    private SpecialityRepository specialityRepository;

    @InjectMocks
    private SpecialityService specialityService;

    @Test
    void findAllSpecialityes_existingSpecialities() {
        // Arrange
        List<Speciality> specialities = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        specialities.add(new Speciality(id1, "Cardiologia"));
        specialities.add(new Speciality(id2, "Dermatologia"));

        SpecialityModel specialityModel1 = new SpecialityModel( "Cardiologia",List.of( new ProfessionalUnityModel()));
        SpecialityModel specialityModel2 = new SpecialityModel( "Dermatologia",List.of( new ProfessionalUnityModel()));

        when(specialityRepository.findById(id1)).thenReturn(Optional.of(specialityModel1));
        when(specialityRepository.findById(id2)).thenReturn(Optional.of(specialityModel2));

        // Act
        List<SpecialityModel> result = specialityService.findAllSpecialityes(specialities);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(specialityModel1));
        assertTrue(result.contains(specialityModel2));
        verify(specialityRepository, never()).save(any(SpecialityModel.class));

    }

    @Test
    void findAllSpecialityes_newSpecialities() {
        // Arrange
        List<Speciality> specialities = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        specialities.add(new Speciality(id1, "Cardiologia"));
        specialities.add(new Speciality(id2, "Dermatologia"));

        when(specialityRepository.findById(id1)).thenReturn(Optional.empty());
        when(specialityRepository.save(any(SpecialityModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<SpecialityModel> result = specialityService.findAllSpecialityes(specialities);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Cardiologia", result.get(0).getName());
        assertEquals("Dermatologia", result.get(1).getName());
        verify(specialityRepository, times(1)).findById(id1);
        verify(specialityRepository, times(2)).save(any(SpecialityModel.class));
    }


    @Test
    void findAllSpecialityes_mixedSpecialities() {
        // Arrange
        List<Speciality> specialities = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        specialities.add(new Speciality(id1, "Cardiologia"));
        specialities.add(new Speciality(id2, "Dermatologia"));

        SpecialityModel specialityModel1 = new SpecialityModel("Cardiologia", List.of( new ProfessionalUnityModel()));

        when(specialityRepository.findById(id1)).thenReturn(Optional.of(specialityModel1));
        when(specialityRepository.findById(id2)).thenReturn(Optional.empty());
        when(specialityRepository.save(any(SpecialityModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<SpecialityModel> result = specialityService.findAllSpecialityes(specialities);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(specialityModel1));
        assertEquals("Dermatologia", result.stream().filter(s -> s.getId() == id2).findFirst().orElseThrow().getName());
        verify(specialityRepository, times(1)).findById(id1);
        verify(specialityRepository, times(1)).save(any(SpecialityModel.class));
    }


    @Test
    void findAllSpecialityes_emptyList() {
        // Arrange
        List<Speciality> specialities = new ArrayList<>();

        // Act
        List<SpecialityModel> result = specialityService.findAllSpecialityes(specialities);

        // Assert
        assertTrue(result.isEmpty());
        verify(specialityRepository, never()).save(any(SpecialityModel.class));
    }

}
