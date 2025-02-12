package br.com.susmanager.controller;

import br.com.susmanager.controller.SpecialityController;
import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.controller.dto.professional.ProfessionalType;
import br.com.susmanager.controller.dto.speciality.SpecialityDTO;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.service.SpecialityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecialityControllerTest {

    @Mock
    private SpecialityService specialityService;

    @InjectMocks
    private SpecialityController specialityController;

    @Captor
    private ArgumentCaptor<SpecialityForm> specialityFormCaptor;

    @Test
    void testProcedureRegister() {
        SpecialityForm specialityForm = new SpecialityForm("Cardiology", List.of(UUID.randomUUID()));
        SpecialityDTO specialityDTO = new SpecialityDTO(new SpecialityModel(specialityForm, List.of())); // Mocked Speciality

        when(specialityService.createProcedure(any(SpecialityForm.class))).thenReturn(specialityDTO);

        ResponseEntity<SpecialityDTO> response = specialityController.procedureRegister(specialityForm);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(specialityDTO, response.getBody());

        verify(specialityService).createProcedure(specialityFormCaptor.capture());
        SpecialityForm capturedForm = specialityFormCaptor.getValue();
        assertEquals("Cardiology", capturedForm.name());
    }

    @Test
    void testFindAllProcedures() {
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel address = new AddressModel(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());
        ProfessionalModel professional1 = new  ProfessionalModel(new ProfessionalCreateForm("Name1", "unity", "123", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities));
        ProfessionalModel professional2 = new  ProfessionalModel(new ProfessionalCreateForm("Name1", "unity", "123", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities));

        SpecialityDTO specialityDTO1 = new SpecialityDTO(new SpecialityModel(new SpecialityForm("Cardiology",List.of(UUID.randomUUID())), List.of(professional1, professional2)));
        SpecialityDTO specialityDTO2 = new SpecialityDTO(new SpecialityModel(new SpecialityForm("Dermatology",List.of(UUID.randomUUID())), List.of(professional1, professional2)));
        List<SpecialityDTO> specialityDTOs = List.of(specialityDTO1, specialityDTO2);

        when(specialityService.findAll()).thenReturn(specialityDTOs);

        ResponseEntity<List<SpecialityDTO>> response = specialityController.findAllProcedures();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(specialityDTOs, response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Cardiology", response.getBody().get(0).name());
        assertEquals("Dermatology", response.getBody().get(1).name());

        verify(specialityService).findAll();
    }

    @Test
    void testFindProcedureForName() {
        String name = "Cardiology";
        SpecialityDTO specialityDTO = new SpecialityDTO(new SpecialityModel(new SpecialityForm(name,List.of(UUID.randomUUID())), List.of()));

        when(specialityService.findByName(name)).thenReturn(specialityDTO);

        ResponseEntity<SpecialityDTO> response = specialityController.findProcedureForName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(specialityDTO, response.getBody());

        verify(specialityService).findByName(name);
    }

    @Test
    void testUpdateProcedure() {
        UUID id = UUID.randomUUID();
        SpecialityForm specialityForm = new SpecialityForm("New Cardiology",List.of(UUID.randomUUID()));
        SpecialityDTO updatedSpecialityDTO = new SpecialityDTO(new SpecialityModel(specialityForm, List.of()));

        when(specialityService.update(eq(id), any(SpecialityForm.class))).thenReturn(updatedSpecialityDTO); // Use eq() for UUID matching

        ResponseEntity<SpecialityDTO> response = specialityController.updateProcedure(id, specialityForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedSpecialityDTO, response.getBody());

        verify(specialityService).update(eq(id), specialityFormCaptor.capture());
        SpecialityForm capturedForm = specialityFormCaptor.getValue();
        assertEquals("New Cardiology", capturedForm.name());
    }
}