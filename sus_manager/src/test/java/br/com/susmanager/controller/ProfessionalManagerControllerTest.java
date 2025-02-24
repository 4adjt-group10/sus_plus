package br.com.susmanager.controller;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.controller.dto.professional.ProfessionalType;
import br.com.susmanager.helper.ProfessionalHelper;
import br.com.susmanager.model.ProfessionalModel;
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

    private ProfessionalHelper helper = new ProfessionalHelper();

    @Test
    void testFindAll() {
        ProfessionalManagerOut professional1 = helper.createProfessionalManagerOut();
        ProfessionalManagerOut professional2 = helper.createProfessionalManagerOut();
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

        ProfessionalManagerOut professional = helper.createProfessionalManagerOut();

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
        ProfessionalCreateForm form = new ProfessionalCreateForm("New Professional",  "789", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
        ProfessionalManagerOut professional = new ProfessionalManagerOut(new ProfessionalModel(form,null));

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