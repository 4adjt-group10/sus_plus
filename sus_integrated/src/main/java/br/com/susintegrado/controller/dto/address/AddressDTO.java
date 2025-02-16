package br.com.susintegrado.controller.dto.address;

import br.com.susintegrado.model.address.Address;

public record AddressDTO(String street, int number, String neighborhood, String city, String state) {

    public AddressDTO(Address address) {
        this(address.getStreet(), address.getNumber(), address.getNeighborhood(), address.getCity(), address.getState());
    }
}
