package br.com.susintegrado.service;

import br.com.susintegrado.controller.dto.address.AddressFormDTO;
import br.com.susintegrado.model.address.Address;
import br.com.susintegrado.repository.AddressRepository;
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
