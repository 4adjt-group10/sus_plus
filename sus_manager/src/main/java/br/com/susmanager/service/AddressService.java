package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressModel register(AddressFormDTO addressFormDTO) {
        AddressModel address = new AddressModel(addressFormDTO);
        return addressRepository.save(address);
    }
}
