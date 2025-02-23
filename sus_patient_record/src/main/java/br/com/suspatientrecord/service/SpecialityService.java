package br.com.suspatientrecord.service;

import br.com.suspatientrecord.model.SpecialityModel;
import br.com.suspatientrecord.queue.consumer.dto.Speciality;
import br.com.suspatientrecord.repository.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<SpecialityModel> findAllSpecialityes(List<Speciality> speciality) {
        List<SpecialityModel> specialityModels = new ArrayList<>();
        speciality.forEach(s -> {
            Optional<SpecialityModel> byId = specialityRepository.findById(s.getId());
            if (byId.isPresent()) {
                specialityModels.add(byId.get());
            }
            else{
                SpecialityModel specialityModel = new SpecialityModel(s);
                specialityRepository.save(specialityModel);
                specialityModels.add(specialityModel);

            }
        });
        return specialityModels;
    }
}
