package br.com.susunity.service;

import br.com.susunity.controller.dto.UnityInForm;
import br.com.susunity.controller.dto.UnityDto;
import br.com.susunity.controller.dto.UnityProfessionalForm;
import br.com.susunity.model.AddressModel;
import br.com.susunity.model.SpecialityModel;
import br.com.susunity.model.UnityModel;
import br.com.susunity.queue.consumer.dto.Professional;
import br.com.susunity.queue.consumer.dto.Speciality;
import br.com.susunity.queue.producer.MessageProducer;
import br.com.susunity.queue.producer.dto.UnityProfessional;
import br.com.susunity.repository.UnityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UnityService {
    private final UnityRepository unityRepository;
    private final AddressService addressService;
    private final MessageProducer messageProducer;
    private final SpecialityService specialityService;

    public UnityService(UnityRepository unityRepository, AddressService addressService, MessageProducer messageProducer, SpecialityService specialityService) {
        this.unityRepository = unityRepository;
        this.addressService = addressService;
        this.messageProducer = messageProducer;
        this.specialityService = specialityService;
    }
    @Transactional
    public UnityDto create(UnityInForm unityInForm) {
        Optional<UnityModel> unity = unityRepository.findByname(unityInForm.name());
        if(unity.isPresent()){
            return new UnityDto(unity.get());
        }
        AddressModel newAddress =  addressService.createAddress(unityInForm.address());
        return  new UnityDto(unityRepository.save(new UnityModel(unityInForm,newAddress)));

    }

    public List<UnityDto> findAll() {
        List<UnityModel> unityModels = unityRepository.findAll();
        return unityModels.stream().map(UnityDto::new).collect(Collectors.toList());
    }

    public UnityDto findById(UUID id) {
        return unityRepository.findById(id).map(UnityDto::new).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional
    public UnityDto update(UUID id, UnityInForm unityInForm) {
        UnityModel unity = unityRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        AddressModel newAddress =  addressService.findAdrress(unityInForm.address());
        unity.merge(unityInForm,newAddress);
        return  new UnityDto(unityRepository.saveAndFlush(unity));
    }

    public void delete(UUID id) {
        unityRepository.deleteById(id);
    }

    public UnityDto updateInQuantity(UUID id, Integer quantity) {
        UnityModel unity = unityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        unity.inPatiente(quantity);
        return  new UnityDto(unityRepository.save(unity));
    }

    public UnityDto updateOutQuantity(UUID id, Integer quantity) {
        UnityModel unity = unityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        unity.outPatiente(quantity);
        return  new UnityDto(unityRepository.save(unity));
    }


    @Transactional
    public void updateProfessional(Professional messageBody) {
        if(messageBody.getProfessional()){
            List<SpecialityModel> specialityModels = findSpeciality(messageBody.getSpeciality());
            UnityModel unityModel = unityRepository.findById(messageBody.getUnityId()).orElseThrow(EntityNotFoundException::new);
            unityModel.setProfessional(messageBody,unityModel,specialityModels);
            unityRepository.save(unityModel);
        }
    }

    private List<SpecialityModel> findSpeciality(List<Speciality> speciality) {
        return specialityService.findAllSpecialityes(speciality);
    }

    public void includeProfessional(UnityProfessionalForm unityProfessionalForm) {
        unityRepository.findById(unityProfessionalForm.unityId()).orElseThrow(EntityNotFoundException::new);
        messageProducer.sendToManager(new UnityProfessional(unityProfessionalForm));
    }
}
