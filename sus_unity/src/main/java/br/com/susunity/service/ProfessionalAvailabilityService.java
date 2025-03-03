package br.com.susunity.service;

import br.com.susunity.controller.dto.professional.ProfessionalAvailabilityDTO;
import br.com.susunity.controller.dto.professional.ProfessionalAvailabilityFormDTO;
import br.com.susunity.model.ProfessionalAvailabilityModel;
import br.com.susunity.model.ProfessionalUnityModel;
import br.com.susunity.model.UnityModel;
import br.com.susunity.queue.consumer.dto.MessageBodyByScheduling;
import br.com.susunity.queue.producer.MessageProducer;
import br.com.susunity.queue.producer.dto.MessageBodyForScheduler;
import br.com.susunity.repository.ProfessionalAvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessionalAvailabilityService {

    private final ProfessionalService professionalService;

    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    private final UnityService unityService;

    private final MessageProducer messageProducer;

    public ProfessionalAvailabilityService(ProfessionalService professionalService,
                                           ProfessionalAvailabilityRepository professionalAvailabilityRepository,
                                           UnityService unityService, MessageProducer messageProducer) {
        this.professionalService = professionalService;
        this.professionalAvailabilityRepository = professionalAvailabilityRepository;
        this.unityService = unityService;
        this.messageProducer = messageProducer;
    }

    @Transactional
    public ProfessionalAvailabilityDTO registerAvailability(ProfessionalAvailabilityFormDTO formDTO) {
        Optional<ProfessionalUnityModel> professional = professionalService.getProfessionalById(formDTO.professionalUnityId());
        Optional<UnityModel> unity = unityService.findUnityById(formDTO.unityId());
        if (professional.isEmpty() || unity.isEmpty()) {
            throw new EntityNotFoundException("Professional or unity not found");
        }
        ProfessionalAvailabilityModel availability = new ProfessionalAvailabilityModel(professional.get(), formDTO.unityId(), formDTO.availableTime());
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

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByDate(LocalDate date, UUID unityId) {
        return professionalAvailabilityRepository.findByAvailableByDateAndUnityId(date, unityId)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

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

    public void validateForScheduling(MessageBodyByScheduling message) {
        unityService.findUnityById(message.unityId())
                .ifPresentOrElse(
                        unityModel -> {
                            Optional<ProfessionalUnityModel> possibleProfessional = professionalService.getProfessional(message.professionalId());
                            boolean isSpecialityValid = unityService.validateEspeciality(unityModel, message);
                            boolean isProfessionalValid = possibleProfessional.isPresent();
                            boolean isAppointmentValid = possibleProfessional
                                    .map(professional -> professional.validateAppointment(message.appointment()))
                                    .orElse(false);

                            messageProducer.sendToScheduling(new MessageBodyForScheduler(isSpecialityValid,
                                    true,
                                    isProfessionalValid,
                                    isAppointmentValid,
                                    message.schedulingId()));
                        },
                        () -> messageProducer.sendToScheduling(new MessageBodyForScheduler(false,
                                false,
                                false,
                                false,
                                message.schedulingId()))
                );
    }
}
