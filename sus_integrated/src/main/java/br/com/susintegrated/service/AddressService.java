package br.com.susintegrated.service;

import br.com.susintegrated.controller.dto.address.AddressFormDTO;
import br.com.susintegrated.model.address.Address;
import br.com.susintegrated.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address register(AddressFormDTO addressFormDTO) {
        Address address = new Address(addressFormDTO);
        return addressRepository.save(address);
    }

}
