package br.com.susunity.controller;


import br.com.susunity.controller.dto.AddressFormDTO;
import br.com.susunity.controller.dto.UnityDto;
import br.com.susunity.controller.dto.UnityInForm;
import br.com.susunity.controller.dto.UnityProfessionalForm;
import br.com.susunity.model.UnityModel;
import br.com.susunity.queue.producer.MessageProducer;
import br.com.susunity.service.UnityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnityControllerTest {
    @InjectMocks
    private UnityController unityController;

    @Mock
    private UnityService unityService;

    @Mock
    private MessageProducer producer;


    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        unityController = new UnityController(unityService);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void testCreateUnity() {
        UnityInForm unityInForm = new UnityInForm("Unity", 10, new AddressFormDTO("street", 1, "neighborhood", "city", "state"));
        UnityDto unityDto = new UnityDto(new UnityModel());
        when(unityService.create(unityInForm)).thenReturn(unityDto);

        ResponseEntity<UnityDto> response = unityController.createUnity(unityInForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(unityDto, response.getBody());
        verify(unityService, times(1)).create(unityInForm);
    }

    @Test
    public void testAllUnity() {
        List<UnityDto> unityDtos = List.of(new UnityDto(new UnityModel()), new UnityDto(new UnityModel()));
        when(unityService.findAll()).thenReturn(unityDtos);

        ResponseEntity<List<UnityDto>> response = unityController.allUnity();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(unityDtos, response.getBody());
        verify(unityService, times(1)).findAll();
    }

    @Test
    public void testFindUnity() {
        UUID id = UUID.randomUUID();
        UnityDto unityDto = new UnityDto(new UnityModel());
        when(unityService.findById(id)).thenReturn(unityDto);

        ResponseEntity<UnityDto> response = unityController.findUnity(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(unityDto, response.getBody());
        verify(unityService, times(1)).findById(id);
    }

    @Test
    public void testAlterUnity() {
        UUID id = UUID.randomUUID();
        UnityInForm unityInForm = new UnityInForm("Unity", 10, new AddressFormDTO("street", 1, "neighborhood", "city", "state"));
        UnityDto unityDto = new UnityDto(new UnityModel());
        when(unityService.update(id, unityInForm)).thenReturn(unityDto);

        ResponseEntity<UnityDto> response = unityController.alterUnity(id, unityInForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(unityDto, response.getBody());
        verify(unityService, times(1)).update(id, unityInForm);
    }

    @Test
    public void testDeleteUnity() {
        UUID id = UUID.randomUUID();

        ResponseEntity<String> response = unityController.deleteUnity(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("unidade Deletada", response.getBody());
        verify(unityService, times(1)).delete(id);
    }

    @Test
    public void testAlterInUnity() {
        UUID id = UUID.randomUUID();
        int quantity = 10;
        UnityDto unityDto = new UnityDto(new UnityModel());
        when(unityService.updateInQuantity(id, quantity)).thenReturn(unityDto);

        ResponseEntity<UnityDto> response = unityController.alterInUnity(id, quantity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(unityDto, response.getBody());
        verify(unityService, times(1)).updateInQuantity(id, quantity);
    }

    @Test
    public void testAlterOutUnity() {
        UUID id = UUID.randomUUID();
        int quantity = 5;
        UnityDto unityDto = new UnityDto(new UnityModel());
        when(unityService.updateOutQuantity(id, quantity)).thenReturn(unityDto);

        ResponseEntity<UnityDto> response = unityController.alterOutUnity(id, quantity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(unityDto, response.getBody());
        verify(unityService, times(1)).updateOutQuantity(id, quantity);
    }

    @Test
    public void testIncludeProfessional() {
        UnityProfessionalForm unityProfessionalForm = new UnityProfessionalForm(UUID.randomUUID(), UUID.randomUUID());

        ResponseEntity<Void> response = unityController.includeProfessional(unityProfessionalForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(unityService, times(1)).includeProfessional(unityProfessionalForm);
    }

    @Test
    public void testExcludeProfessional() {
        UnityProfessionalForm unityProfessionalForm = new UnityProfessionalForm(UUID.randomUUID(), UUID.randomUUID());

        ResponseEntity<Void> response = unityController.excludeProfessional(unityProfessionalForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(unityService, times(1)).excludeProfessional(unityProfessionalForm);
    }
}