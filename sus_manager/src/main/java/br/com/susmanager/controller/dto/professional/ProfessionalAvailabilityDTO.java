package br.com.susmanager.controller.dto.professional;

import br.com.susmanager.model.ProfessionalAvailabilityModel;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProfessionalAvailabilityDTO(UUID id, String professionalName, LocalDateTime availableTime) {

    public ProfessionalAvailabilityDTO(ProfessionalAvailabilityModel professionalAvailability) {
        this(professionalAvailability.getId(),
                professionalAvailability.getProfessionalName(),
                professionalAvailability.getAvailableTime());
    }
}