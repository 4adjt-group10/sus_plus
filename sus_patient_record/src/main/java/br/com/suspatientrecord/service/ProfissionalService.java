package br.com.suspatientrecord.service;

import br.com.susunity.model.ProfissionalUnityModel;
import br.com.susunity.model.SpecialityModel;
import br.com.susunity.queue.consumer.dto.Professional;
import br.com.susunity.repository.ProfessionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfissionalService {
    private final ProfessionalRepository professionalRepository;

    public ProfissionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Transactional
    public ProfissionalUnityModel save(Professional messageBody, List<SpecialityModel> specialityModels) {
        Optional<ProfissionalUnityModel> byProfissionalId = professionalRepository.findByProfissionalId(messageBody.getProfissionalId());
        if(!byProfissionalId.isPresent()){
            ProfissionalUnityModel profissionalUnityModel = new ProfissionalUnityModel(messageBody);
            professionalRepository.save(profissionalUnityModel);
            profissionalUnityModel.setSpeciality(specialityModels);
            return professionalRepository.save(profissionalUnityModel);
        }
        return byProfissionalId.get();
    }

    public Optional<ProfissionalUnityModel> getProfessional(UUID uuid) {
        return professionalRepository.findById(uuid);
    }
}
