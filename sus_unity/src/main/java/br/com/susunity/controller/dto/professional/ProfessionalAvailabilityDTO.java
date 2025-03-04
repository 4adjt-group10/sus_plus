package br.com.susunity.controller.dto.professional;

import br.com.susunity.model.ProfessionalAvailabilityModel;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProfessionalAvailabilityDTO(UUID id, UUID unityId, String professionalName, LocalDateTime availableTime) {

    public ProfessionalAvailabilityDTO(ProfessionalAvailabilityModel professionalAvailability) {
        this(professionalAvailability.getId(),
                professionalAvailability.getUnityId(),
                professionalAvailability.getProfessionalName(),
                professionalAvailability.getAvailableTime());
    }
}