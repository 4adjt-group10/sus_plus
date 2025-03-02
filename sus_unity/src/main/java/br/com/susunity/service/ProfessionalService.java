package br.com.susunity.service;

import br.com.susunity.model.ProfessionalUnityModel;
import br.com.susunity.model.SpecialityModel;
import br.com.susunity.queue.consumer.dto.manager.MessageBodyByManager;
import br.com.susunity.repository.ProfessionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Transactional
    public ProfessionalUnityModel save(MessageBodyByManager messageBody, List<SpecialityModel> specialityModels) {
        Optional<ProfessionalUnityModel> byProfissionalId = professionalRepository.findByProfessionalId(messageBody.professionalId());
        if(!byProfissionalId.isPresent()){
            ProfessionalUnityModel professionalUnityModel = new ProfessionalUnityModel(messageBody);
            professionalRepository.save(professionalUnityModel);
            professionalUnityModel.setSpeciality(specialityModels);
            return professionalRepository.save(professionalUnityModel);
        }
        return byProfissionalId.get();
    }

    public Optional<ProfessionalUnityModel> getProfessional(UUID uuid) {
        return professionalRepository.findById(uuid);
    }

    public boolean validateProfessional(UUID uuid) {
        return professionalRepository.existsById(uuid);
    }
}
