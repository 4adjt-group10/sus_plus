package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressModel register(AddressFormDTO addressFormDTO) {
        AddressModel address = new AddressModel(addressFormDTO);
        return addressRepository.save(address);
    }
}
