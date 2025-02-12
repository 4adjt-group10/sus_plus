package br.com.susmanager.model;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.model.AddressModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AddressModelTest {

    @Test
    public void testConstructorWithFormDTO() {
        AddressFormDTO formDTO = Mockito.mock(AddressFormDTO.class);
        Mockito.when(formDTO.neighborhood()).thenReturn("Neighborhood");
        Mockito.when(formDTO.city()).thenReturn("City");
        Mockito.when(formDTO.state()).thenReturn("State");
        Mockito.when(formDTO.number()).thenReturn(123);
        Mockito.when(formDTO.street()).thenReturn("Street");

        AddressModel address = new AddressModel(formDTO);

        assertNotNull(address.getId());
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


        AddressModel address = new AddressModel(Mockito.mock(AddressFormDTO.class));
        address.merge(formDTO);

        assertEquals("New Neighborhood", address.getNeighborhood());
        assertEquals("New City", address.getCity());
        assertEquals("New State", address.getState());
        assertEquals(456, address.getNumber());
        assertEquals("New Street", address.getStreet());
    }

    @Test
    public void testGetters() {
        AddressFormDTO formDTO = Mockito.mock(AddressFormDTO.class);
        Mockito.when(formDTO.neighborhood()).thenReturn("Neighborhood");
        Mockito.when(formDTO.city()).thenReturn("City");
        Mockito.when(formDTO.state()).thenReturn("State");
        Mockito.when(formDTO.number()).thenReturn(123);
        Mockito.when(formDTO.street()).thenReturn("Street");

        AddressModel address = new AddressModel(formDTO);
        UUID id = address.getId();

        assertEquals("Neighborhood", address.getNeighborhood());
        assertEquals("City", address.getCity());
        assertEquals("State", address.getState());
        assertEquals(123, address.getNumber());
        assertEquals("Street", address.getStreet());
        assertNotNull(id);
    }


    @Test
    public void testNoArgsConstructor() {
        AddressModel address = new AddressModel();
        assertNotNull(address);
    }
}
