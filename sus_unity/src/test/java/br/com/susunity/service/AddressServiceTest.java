package br.com.susunity.service;

import br.com.susunity.controller.dto.AddressFormDTO;
import br.com.susunity.model.AddressModel;
import br.com.susunity.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Test
    public void testCreateAddress() {
        AddressFormDTO formDTO = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel addressModel = new AddressModel(formDTO);
        when(addressRepository.save(any(AddressModel.class))).thenReturn(addressModel);

        AddressModel createdAddress = addressService.createAddress(formDTO);

        assertNotNull(createdAddress);
        assertEquals(formDTO.street(), createdAddress.getStreet());
        assertEquals(formDTO.number(), createdAddress.getNumber());
        assertEquals(formDTO.city(), createdAddress.getCity());


        verify(addressRepository, times(1)).save(any(AddressModel.class));
    }

    @Test
    public void testFindAddress_Exists() {
        AddressFormDTO formDTO = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel addressModel = new AddressModel(formDTO);
        when(addressRepository.findByStreetAndNumberAndCity(formDTO.street(), formDTO.number(), formDTO.city())).thenReturn(addressModel);

        AddressModel foundAddress = addressService.findAdrress(formDTO);

        assertEquals(addressModel, foundAddress);
        verify(addressRepository, times(1)).findByStreetAndNumberAndCity(formDTO.street(), formDTO.number(), formDTO.city());
        verify(addressRepository, never()).save(any(AddressModel.class));
    }

    @Test
    public void testFindAddress_NotExists() {
        AddressFormDTO formDTO = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel addressModel = new AddressModel(formDTO);
        when(addressRepository.findByStreetAndNumberAndCity(formDTO.street(), formDTO.number(), formDTO.city())).thenReturn(null);
        when(addressRepository.save(any(AddressModel.class))).thenReturn(addressModel);

        AddressModel foundAddress = addressService.findAdrress(formDTO);

        assertEquals(addressModel, foundAddress);
        verify(addressRepository, times(1)).findByStreetAndNumberAndCity(formDTO.street(), formDTO.number(), formDTO.city());
        verify(addressRepository, times(1)).save(any(AddressModel.class)); // Should call save
    }


    @Test
    public void testGetById_Exists() {
        UUID id = UUID.randomUUID();
        AddressModel addressModel = new AddressModel(new AddressFormDTO("Street", 123, "City", "State", "Zip"));
        when(addressRepository.findById(id)).thenReturn(Optional.of(addressModel));

        AddressModel foundAddress = addressService.getById(id);

        assertEquals(addressModel, foundAddress);
        verify(addressRepository, times(1)).findById(id);
    }

    @Test
    public void testGetById_NotExists() {
        UUID id = UUID.randomUUID();
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.getById(id));

        verify(addressRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateAddress() {
        AddressModel address = new AddressModel(new AddressFormDTO("Street", 123, "City", "State", "Zip"));

        addressService.updateAddress(address);

        verify(addressRepository, times(1)).save(address);
    }
}