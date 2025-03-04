//package br.com.susunity.service;
//
//import br.com.susunity.controller.dto.*;
//import br.com.susunity.model.*;
//import br.com.susunity.queue.consumer.dto.manager.Professional;
//import br.com.susunity.queue.consumer.dto.manager.Speciality;
//import br.com.susunity.queue.producer.MessageProducer;
//import br.com.susunity.repository.UnityRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UnityServiceTest {
//
//    @Mock
//    private UnityRepository unityRepository;
//    @Mock
//    private AddressService addressService;
//    @Mock
//    private MessageProducer messageProducer;
//    @Mock
//    private SpecialityService specialityService;
//    @Mock
//    private ProfissionalService profissionalService;
//
//    @InjectMocks
//    private UnityService unityService;
//
//    @Test
//    void create_newUnity() {
//        // Arrange
//        AddressFormDTO formDTO = new AddressFormDTO("São Paulo", 456, "Consolação", "São Paulo", "SP");
//        UnityInForm unityInForm = new UnityInForm("Hospital Sírio-Libanês", 456, formDTO);
//        AddressModel addressModel = new AddressModel(formDTO);
//        when(addressService.createAddress(any())).thenReturn(addressModel);
//        when(unityRepository.findByname(any())).thenReturn(Optional.empty());
//        when(unityRepository.save(any(UnityModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        UnityDto unityDto = unityService.create(unityInForm);
//
//        // Assert
//        assertNotNull(unityDto);
//        assertEquals("Hospital Sírio-Libanês", unityDto.name());
//        verify(addressService).createAddress(any());
//        verify(unityRepository).save(any(UnityModel.class));
//    }
//
//    @Test
//    void findAll() {
//        // Arrange
//        List<UnityModel> unityModels = Arrays.asList(new UnityModel(), new UnityModel());
//        when(unityRepository.findAll()).thenReturn(unityModels);
//
//        // Act
//        List<UnityDto> unityDtos = unityService.findAll();
//
//        // Assert
//        assertEquals(2, unityDtos.size());
//        verify(unityRepository).findAll();
//    }
//
//    @Test
//    void findById_existingUnity() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        UnityModel unityModel = new UnityModel();
//        when(unityRepository.findById(id)).thenReturn(Optional.of(unityModel));
//
//        // Act
//        UnityDto unityDto = unityService.findById(id);
//
//        // Assert
//        assertNotNull(unityDto);
//        verify(unityRepository).findById(id);
//    }
//
//    @Test
//    void findById_nonExistingUnity() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        when(unityRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(EntityNotFoundException.class, () -> unityService.findById(id));
//        verify(unityRepository).findById(id);
//    }
//
//    @Test
//    void updateProfessional_professionalAdded() {
//        UUID unityId = UUID.randomUUID();
//        List<Speciality> specialities = List.of(new Speciality(UUID.randomUUID(), "Cardiologia"));
//        Professional professional = new Professional(UUID.randomUUID(),"doutor1", ProfessionalType.DOCTOR,specialities,true,unityId);
//        List<SpecialityModel> specialityModels = List.of(new SpecialityModel(new Speciality(UUID.randomUUID(),"Cardiologia")));
//        when(specialityService.findAllSpecialityes(any())).thenReturn(specialityModels);
//        UnityModel unityModel = new UnityModel();
//        when(unityRepository.findById(unityId)).thenReturn(Optional.of(unityModel));
//
//        ProfissionalUnityModel profissionalUnityModel = new ProfissionalUnityModel(UUID.randomUUID(),"doutor1",ProfessionalType.DOCTOR,List.of(unityModel),specialityModels);
//        profissionalUnityModel.setSpeciality(specialityModels);
//        when(profissionalService.save(any(), any())).thenReturn(profissionalUnityModel);
//
//        unityService.updateProfessional(professional);
//
//        verify(unityRepository).save(unityModel);
//
//        ArgumentCaptor<UnityModel> unityCaptor = ArgumentCaptor.forClass(UnityModel.class);
//        verify(unityRepository).save(unityCaptor.capture());
//        UnityModel savedUnity = unityCaptor.getValue();
//        assertFalse(savedUnity.getProfessional().isEmpty());
//        assertEquals(1, savedUnity.getProfessional().size());
//        ProfissionalUnityModel savedProfessional = savedUnity.getProfessional().get(0);
//        assertEquals("doutor1", savedProfessional.getProfissionalName());
//        assertEquals(1, savedProfessional.getSpeciality().size());
//        assertEquals("Cardiologia", savedProfessional.getSpeciality().get(0).getName());
//    }
//
//}