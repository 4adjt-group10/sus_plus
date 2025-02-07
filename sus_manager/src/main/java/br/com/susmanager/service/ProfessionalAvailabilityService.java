package br.com.susmanager.service;

import br.com.susmanager.controller.dto.professional.ProfessionalAvailabilityDTO;
import br.com.susmanager.controller.dto.professional.ProfessionalAvailabilityFormDTO;
import br.com.susmanager.model.ProfessionalAvailabilityModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.repository.ProfessionalAvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ProfessionalAvailabilityService {

    private final  ProfessionalManagerService professionalService;

    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    public ProfessionalAvailabilityService(ProfessionalManagerService professionalService, ProfessionalAvailabilityRepository professionalAvailabilityRepository) {
        this.professionalService = professionalService;
        this.professionalAvailabilityRepository = professionalAvailabilityRepository;
    }


    @Transactional
    public ProfessionalAvailabilityDTO registerAvailability(ProfessionalAvailabilityFormDTO formDTO) {
        ProfessionalModel professional = professionalService.findProfessionalById(formDTO.professionalId());
        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel(professional, formDTO.availableTime());
        professionalAvailabilityRepository.save(availability);
        return new ProfessionalAvailabilityDTO(availability);
    }

    public List<ProfessionalAvailabilityDTO> listAllAvailabilities() {
        return professionalAvailabilityRepository.findAll().stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByProfessionalId(UUID professionalId) {
        return professionalAvailabilityRepository.findByProfessionalId(professionalId)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByDate(LocalDate date) {
        return professionalAvailabilityRepository.findByAvailableByDate(date)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByDayOfWeek(int dayOfWeek) {
        return professionalAvailabilityRepository.findByAvailableTimeByDayOfWeek(dayOfWeek % 7 + 1)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByHour(int hour) {
        return professionalAvailabilityRepository.findByAvailableByHour(hour)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }


//    public List<ProfessionalAvailabilityDTO> findByProcedureId(UUID id) {
//        return professionalAvailabilityRepository.findAllByProfessional_Procedures_id(id)
//                .stream().map(ProfessionalAvailabilityDTO::new).toList();
//    }

    @Transactional
    public ProfessionalAvailabilityDTO updateAvailability(UUID id, ProfessionalAvailabilityFormDTO formDTO) {
        ProfessionalAvailabilityModel availability = professionalAvailabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));
        availability.merge(formDTO);
        return new ProfessionalAvailabilityDTO(availability);
    }

    public void deleteAvailability(ProfessionalAvailabilityModel availability) {
        professionalAvailabilityRepository.delete(availability);
    }
}
