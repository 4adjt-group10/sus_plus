package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.*;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.model.ProfessionalAvailabilityModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.repository.ProfessionalAvailabilityRepository;
import br.com.susmanager.repository.ProfessionalManagerRepository;
import br.com.susmanager.repository.SpecialityRepository;;
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
public class ProfessionalManagerServiceTest {

    @Mock
    private ProfessionalManagerRepository professionalRepository;

    @Mock
    private SpecialityRepository specialityRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    @InjectMocks
    private ProfessionalManagerService professionalManagerService;

    @Test
    void testRegisterProfessional() {
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel address = new AddressModel(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());

        ProfessionalCreateForm form = new ProfessionalCreateForm("Name", "123456", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
        ProfessionalModel professional = new ProfessionalModel(form,null);
        professional.setAddress(address);

        when(specialityRepository.findAllById(specialityIds)).thenReturn(specialities);
        when(addressService.register(addressForm)).thenReturn(address);
        when(professionalRepository.save(any(ProfessionalModel.class))).thenReturn(professional);
        when(professionalAvailabilityRepository.saveAll(anyList())).thenReturn(List.of());

        ProfessionalManagerOut output = professionalManagerService.register(form);

        assertNotNull(output);
        assertEquals("Name", output.name());
        verify(professionalRepository).save(any(ProfessionalModel.class));
        verify(professionalAvailabilityRepository).saveAll(anyList());
        verify(addressService).register(addressForm);
        verify(specialityRepository).findAllById(specialityIds);
    }

    @Test
    void testFindByDocument() {
        String document = "123456";
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel address = new AddressModel(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());

        ProfessionalCreateForm form = new ProfessionalCreateForm("Name", document,  addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
        ProfessionalModel professional = new ProfessionalModel(form,null);

        when(professionalRepository.findByDocument(document)).thenReturn(Optional.of(professional));

        ProfessionalManagerOut output = professionalManagerService.findByDocument(document);

        assertNotNull(output);
        assertEquals("Name", output.name());
        verify(professionalRepository).findByDocument(document);
    }

    @Test
    void testFindByDocument_notFound() {
        String document = "123456";
        when(professionalRepository.findByDocument(document)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> professionalManagerService.findByDocument(document));
        verify(professionalRepository).findByDocument(document);
    }

    @Test
    void testFindProfessionalById() {
        UUID id = UUID.randomUUID();
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());

        ProfessionalCreateForm form = new ProfessionalCreateForm("Name", "12345",  addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
        ProfessionalModel professional = new ProfessionalModel(form,null);

        when(professionalRepository.findById(id)).thenReturn(Optional.of(professional));

        ProfessionalModel output = professionalManagerService.findProfessionalById(id);

        assertEquals(professional, output);
        verify(professionalRepository).findById(id);
    }


    @Test
    void testFindProfessionalById_notFound() {
        UUID id = UUID.randomUUID();
        when(professionalRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> professionalManagerService.findProfessionalById(id));
        verify(professionalRepository).findById(id);
    }

    @Test
    void testUpdate() {
        UUID id = UUID.randomUUID();
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel address = new AddressModel(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());
        ProfessionalCreateForm form = new ProfessionalCreateForm("Name", "123456",  addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
        ProfessionalModel existingProfessional = new ProfessionalModel(form,null);
        existingProfessional.setAddress(address);

        existingProfessional.setAddress(new AddressModel(new AddressFormDTO("Old Street", 123, "Old City", "Old State", "Old Zip")));

        when(professionalRepository.findById(id)).thenReturn(Optional.of(existingProfessional));
        when(specialityRepository.findAllById(specialityIds)).thenReturn(specialities);
        when(professionalAvailabilityRepository.save(any(ProfessionalAvailabilityModel.class))).thenReturn(new ProfessionalAvailabilityModel());

        ProfessionalManagerOut output = professionalManagerService.update(id, form);

        assertNotNull(output);
        verify(professionalRepository).save(existingProfessional);


        assertEquals("Name", output.name());
        assertEquals("123456", output.document());

        verify(professionalAvailabilityRepository).deleteAll(existingProfessional.getAvailability());
        verify(specialityRepository).findAllById(specialityIds);
        verify(professionalRepository).findById(id);
    }

    @Test
    void testDeleById() {
        UUID professionalId = UUID.randomUUID();

        professionalManagerService.deleById(professionalId);

        verify(professionalRepository).deleteById(professionalId);
    }

}