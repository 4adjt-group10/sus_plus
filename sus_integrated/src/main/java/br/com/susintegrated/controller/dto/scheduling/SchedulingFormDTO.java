package br.com.susintegrated.controller.dto.scheduling;

import br.com.susintegrated.model.scheduling.SchedulingStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingFormDTO(
        @NotBlank String patientName,
        @NotBlank String patientDocument,
        @NotBlank String patientPhone,
        @NotNull UUID procedureId,
        @NotNull UUID professionalId,
        @NotNull @FutureOrPresent LocalDateTime appointment,
        @NotNull SchedulingStatus status,
        @Nullable String observation
) {

}
