package br.com.suspatientrecord.service;

import br.com.suspatientrecord.controller.dto.AddressFormDTO;
import br.com.suspatientrecord.model.AddressModel;
import br.com.suspatientrecord.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressModel createAddress(AddressFormDTO formDTO) {
        return addressRepository.save(new AddressModel(formDTO));
    }

    public AddressModel findAdrress(AddressFormDTO formDTO) {
        Optional<AddressModel> addressModel = Optional.ofNullable(addressRepository.findByStreetAndNumberAndCity(formDTO.street(), formDTO.number(), formDTO.city()));
        return addressModel.orElseGet(() -> this.createAddress(formDTO));
    }

    public AddressModel getById(UUID id) {
        return addressRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void updateAddress(AddressModel address) {
        addressRepository.save(address);
    }
}
