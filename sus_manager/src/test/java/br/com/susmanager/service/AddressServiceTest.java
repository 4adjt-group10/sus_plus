package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.repository.AddressRepository;
import br.com.susmanager.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    void testRegisterAddress() {
        AddressFormDTO addressFormDTO = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel expectedAddress = new AddressModel(new AddressFormDTO("Street", 123, "City", "State", "Zip"));

        when(addressRepository.save(any(AddressModel.class))).thenReturn(expectedAddress);

        AddressModel registeredAddress = addressService.register(addressFormDTO);

        assertNotNull(registeredAddress.getId());
        assertEquals(expectedAddress.getStreet(), registeredAddress.getStreet());
        assertEquals(expectedAddress.getNumber(), registeredAddress.getNumber());
        assertEquals(expectedAddress.getCity(), registeredAddress.getCity());
        assertEquals(expectedAddress.getState(), registeredAddress.getState());


    }


    @Test
    void testRegisterAddress_withNullForm() {
        AddressFormDTO addressFormDTO = null;

        try {
            addressService.register(addressFormDTO);
        } catch (NullPointerException e) {
        }

    }
}