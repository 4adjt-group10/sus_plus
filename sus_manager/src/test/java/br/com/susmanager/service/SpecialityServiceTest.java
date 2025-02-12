package br.com.susmanager.service;

import br.com.susmanager.controller.dto.speciality.SpecialityDTO;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.repository.ProfessionalManagerRepository;
import br.com.susmanager.repository.SpecialityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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

    @Mock
    private ProfessionalManagerRepository professionalManagerRepository;

    @InjectMocks
    private SpecialityService specialityService;


    @Test
    void testFindAll() {
        List<SpecialityModel> specialities = List.of(new SpecialityModel(new SpecialityForm("Cardiology", List.of()), List.of()));
        when(specialityRepository.findAll()).thenReturn(specialities);

        List<SpecialityDTO> dtos = specialityService.findAll();

        assertEquals(1, dtos.size());
        assertEquals("Cardiology", dtos.get(0).name());
        verify(specialityRepository).findAll();
    }

    @Test
    void testFindByName() {
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", List.of()), List.of());
        when(specialityRepository.findByName("Cardiology")).thenReturn(Optional.of(speciality));

        SpecialityDTO dto = specialityService.findByName("Cardiology");

        assertEquals("Cardiology", dto.name());
        verify(specialityRepository).findByName("Cardiology");
    }

    @Test
    void testFindByName_notFound() {
        when(specialityRepository.findByName("NonExistent")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> specialityService.findByName("NonExistent"));
        verify(specialityRepository).findByName("NonExistent");
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", List.of()), List.of());
        when(specialityRepository.findById(id)).thenReturn(Optional.of(speciality));

        SpecialityModel foundSpeciality = specialityService.findById(id);

        assertEquals(speciality, foundSpeciality);
        verify(specialityRepository).findById(id);
    }

    @Test
    void testFindById_notFound() {
        UUID id = UUID.randomUUID();
        when(specialityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> specialityService.findById(id));
        verify(specialityRepository).findById(id);
    }

    @Test
    void testFindByIdAndProfessionalId() {
        UUID id = UUID.randomUUID();
        UUID professionalId = UUID.randomUUID();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", List.of()), List.of());
        when(specialityRepository.findByIdAndProfessionalId(id, professionalId)).thenReturn(Optional.of(speciality));

        SpecialityModel foundSpeciality = specialityService.findByIdAndProfessionalId(id, professionalId);

        assertEquals(speciality, foundSpeciality);
        verify(specialityRepository).findByIdAndProfessionalId(id, professionalId);
    }

    @Test
    void testFindByIdAndProfessionalId_notFound() {
        UUID id = UUID.randomUUID();
        UUID professionalId = UUID.randomUUID();
        when(specialityRepository.findByIdAndProfessionalId(id, professionalId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> specialityService.findByIdAndProfessionalId(id, professionalId));
        verify(specialityRepository).findByIdAndProfessionalId(id, professionalId);
    }

    @Test
    void testCreateProcedure() {
        UUID id = UUID.randomUUID();
        SpecialityForm form = new SpecialityForm("Cardiology", List.of(id));

        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", List.of(id)), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());

        List<ProfessionalModel> professionals = new ArrayList<>();
        ProfessionalModel professional1 = mock(ProfessionalModel.class);
        ProfessionalModel professional2 = mock(ProfessionalModel.class);
        professionals.add(professional1);
        professionals.add(professional2);

        when(professionalManagerRepository.findAllById(List.of(id))).thenReturn(professionals);
        when(specialityRepository.save(any(SpecialityModel.class))).thenReturn(speciality);
        when(professionalManagerRepository.saveAll(anyList())).thenReturn(professionals);

        doNothing().when(professional1).addSpeciality(any(SpecialityModel.class));
        doNothing().when(professional2).addSpeciality(any(SpecialityModel.class));


        SpecialityDTO dto = specialityService.createProcedure(form);

        assertNotNull(dto);
        assertEquals("Cardiology", dto.name());

        verify(professionalManagerRepository).findAllById(List.of(id));
        verify(specialityRepository).save(any(SpecialityModel.class));
        verify(professionalManagerRepository).saveAll(anyList());
    }

}