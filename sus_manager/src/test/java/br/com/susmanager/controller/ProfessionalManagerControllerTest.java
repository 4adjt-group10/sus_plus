package br.com.susmanager.controller;

import br.com.susmanager.controller.ProfessionalManager;
import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.controller.dto.professional.ProfessionalType;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.service.ProfessionalManagerService;
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
public class ProfessionalManagerControllerTest {

    @Mock
    private ProfessionalManagerService professionalManagerService;

    @InjectMocks
    private ProfessionalManager professionalManager;

    @Captor
    private ArgumentCaptor<ProfessionalCreateForm> professionalCreateFormCaptor;

    @Test
    void testFindAll() {
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel address = new AddressModel(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());
        ProfessionalManagerOut professional1 = new ProfessionalManagerOut(new ProfessionalModel(new ProfessionalCreateForm("Name1", "unity", "123", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities)));
        ProfessionalManagerOut professional2 = new ProfessionalManagerOut(new ProfessionalModel(new ProfessionalCreateForm("Name2", "unity", "456", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities)));
        List<ProfessionalManagerOut> professionals = List.of(professional1, professional2);

        when(professionalManagerService.listAllProfessionals()).thenReturn(professionals);

        ResponseEntity<List<ProfessionalManagerOut>> response = professionalManager.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(professionals, response.getBody());

        verify(professionalManagerService).listAllProfessionals();
    }

    @Test
    void testFind() {
        UUID professionalId = UUID.randomUUID();
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel address = new AddressModel(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());
        ProfessionalManagerOut professional = new ProfessionalManagerOut(new ProfessionalModel(new ProfessionalCreateForm("Name", "unity", "123", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities)));

        when(professionalManagerService.findById(professionalId)).thenReturn(professional);

        ResponseEntity<ProfessionalManagerOut> response = professionalManager.find(professionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(professional, response.getBody());

        verify(professionalManagerService).findById(professionalId);
    }

    @Test
    void testCreate() {
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        List<UUID> specialityIds = List.of(UUID.randomUUID());
        List<LocalDateTime> availabilities = List.of(LocalDateTime.now());
        ProfessionalCreateForm form = new ProfessionalCreateForm("New Professional", "unity", "789", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
        ProfessionalManagerOut professional = new ProfessionalManagerOut(new ProfessionalModel(form));

        when(professionalManagerService.register(any(ProfessionalCreateForm.class))).thenReturn(professional);

        ResponseEntity<ProfessionalManagerOut> response = professionalManager.create(form);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(professional, response.getBody());

        verify(professionalManagerService).register(professionalCreateFormCaptor.capture());
        ProfessionalCreateForm capturedForm = professionalCreateFormCaptor.getValue();
        assertEquals("New Professional", capturedForm.name());
    }

    @Test
    void testDelete() {
        UUID professionalId = UUID.randomUUID();

        professionalManager.delete(professionalId);

        verify(professionalManagerService).deleById(professionalId);
    }
}