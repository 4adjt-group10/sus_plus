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
    @Autowired
    private SpecialityRepository specialityRepository;
    @Autowired
    private ProfessionalManagerRepository professionalManagerRepository;

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
        List<ProfessionalModel> professionals = professionalManagerRepository.findAllById(procedureFormDTO.professionalsIds());
        SpecialityModel specialityModel = findById(id);
        specialityModel.merge(procedureFormDTO, professionals);
        professionals.forEach(professional -> professional.addSpeciality(specialityModel));
        specialityRepository.save(specialityModel);
        professionalManagerRepository.saveAll(professionals);
        return new SpecialityDTO(specialityModel);
    }

    public List<SpecialityDTO> findAll() {
        List<SpecialityModel> allProcedures = specialityRepository.findAll();
        return allProcedures.stream().map(SpecialityDTO::new).toList();
    }

    public SpecialityDTO findByName(String name) {
        return new SpecialityDTO(specialityRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Procedure not found")));
    }

    public SpecialityModel findById(UUID id) {
        return specialityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Procedure not found"));
    }

    public SpecialityModel findByIdAndProfessionalId(UUID id, UUID professional) {
        return specialityRepository.findByIdAndProfessionalId(id, professional)
                .orElseThrow(() -> new EntityNotFoundException("Procedure not found"));
    }
}
