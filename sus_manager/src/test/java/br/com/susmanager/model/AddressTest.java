package br.com.susmanager.model;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    @Test
    public void testConstructorWithFormDTO() {
        AddressFormDTO formDTO = Mockito.mock(AddressFormDTO.class);
        Mockito.when(formDTO.neighborhood()).thenReturn("Neighborhood");
        Mockito.when(formDTO.city()).thenReturn("City");
        Mockito.when(formDTO.state()).thenReturn("State");
        Mockito.when(formDTO.number()).thenReturn(123);
        Mockito.when(formDTO.street()).thenReturn("Street");

        Address address = new Address(formDTO);

        assertEquals("Neighborhood", address.getNeighborhood());
        assertEquals("City", address.getCity());
        assertEquals("State", address.getState());
        assertEquals(123, address.getNumber());
        assertEquals("Street", address.getStreet());
    }

    @Test
    public void testMergeWithFormDTO() {
        AddressFormDTO formDTO = Mockito.mock(AddressFormDTO.class);
        Mockito.when(formDTO.neighborhood()).thenReturn("New Neighborhood");
        Mockito.when(formDTO.city()).thenReturn("New City");
        Mockito.when(formDTO.state()).thenReturn("New State");
        Mockito.when(formDTO.number()).thenReturn(456);
        Mockito.when(formDTO.street()).thenReturn("New Street");


        Address address = new Address(Mockito.mock(AddressFormDTO.class));
        address.merge(formDTO);

        assertEquals("New Neighborhood", address.getNeighborhood());
        assertEquals("New City", address.getCity());
        assertEquals("New State", address.getState());
        assertEquals(456, address.getNumber());
        assertEquals("New Street", address.getStreet());
    }
}
