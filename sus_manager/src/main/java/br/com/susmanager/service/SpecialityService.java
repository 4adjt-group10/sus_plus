package br.com.susmanager.service;

import br.com.susmanager.controller.dto.speciality.SpecialityDTO;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import br.com.susmanager.repository.ProfessionalManagerRepository;
import br.com.susmanager.repository.SpecialityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SpecialityService {
    private static final String ESPECIALIDADE_NAO_ENCONTRADA =  "ESPECIALIDADE_NAO_ENCONTRADA";

    private final SpecialityRepository specialityRepository;

    private final ProfessionalManagerRepository professionalManagerRepository;

    public SpecialityService(SpecialityRepository specialityRepository, ProfessionalManagerRepository professionalManagerRepository) {
        this.specialityRepository = specialityRepository;
        this.professionalManagerRepository = professionalManagerRepository;
    }

    @Transactional
    public SpecialityDTO createProcedure(SpecialityForm procedureFormDTO) {
        List<ProfessionalModel> professionals = professionalManagerRepository.findAllById(procedureFormDTO.professionalsIds() != null ? procedureFormDTO.professionalsIds() : new ArrayList<>());
        SpecialityModel speciality = new SpecialityModel(procedureFormDTO, professionals);
        professionals.forEach(professional -> professional.addSpeciality(speciality));
        specialityRepository.save(speciality);
        professionalManagerRepository.saveAll(professionals);
        return new SpecialityDTO(speciality);
    }

    @Transactional
    public SpecialityDTO update(UUID id, SpecialityForm procedureFormDTO) {
        List<ProfessionalModel> professionals;
        SpecialityModel specialityModel = findById(id);
        if(procedureFormDTO.professionalsIds() != null && !procedureFormDTO.professionalsIds().isEmpty()) {
            professionals = professionalManagerRepository.findAllById(procedureFormDTO.professionalsIds());
            specialityModel.merge(procedureFormDTO, professionals);
            professionals.forEach(professional -> professional.addSpeciality(specialityModel));
            professionalManagerRepository.saveAll(professionals);
        } else {
            professionals = new ArrayList<>();
            specialityModel.merge(procedureFormDTO, professionals);
        }
        specialityRepository.save(specialityModel);
        return new SpecialityDTO(specialityModel);
    }

    public List<SpecialityDTO> findAll() {
        List<SpecialityModel> allProcedures = specialityRepository.findAll();
        return allProcedures.stream().map(SpecialityDTO::new).toList();
    }

    public SpecialityDTO findByName(String name) {
        return new SpecialityDTO(specialityRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(ESPECIALIDADE_NAO_ENCONTRADA)));
    }

    public SpecialityModel findById(UUID id) {
        return specialityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ESPECIALIDADE_NAO_ENCONTRADA));
    }

    public SpecialityModel findByIdAndProfessionalId(UUID id, UUID professional) {
        return specialityRepository.findByIdAndProfessionalId(id, professional)
                .orElseThrow(() -> new EntityNotFoundException(ESPECIALIDADE_NAO_ENCONTRADA));
    }
}
