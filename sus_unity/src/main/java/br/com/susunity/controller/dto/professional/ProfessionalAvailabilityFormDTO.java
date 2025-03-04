package br.com.susunity.controller.dto.professional;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProfessionalAvailabilityFormDTO(@NotNull UUID professionalUnityId,
                                              @NotNull UUID unityId,
                                              @NotNull LocalDateTime availableTime) {

}
