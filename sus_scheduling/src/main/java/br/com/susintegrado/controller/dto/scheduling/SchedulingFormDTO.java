package br.com.susintegrado.controller.dto.scheduling;

import br.com.susintegrado.model.scheduling.SchedulingStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingFormDTO(
        @NotBlank UUID patientId,
        @NotNull UUID specialityId,
        @NotNull UUID unityId,
        @NotNull @FutureOrPresent LocalDateTime appointment,
        @NotNull SchedulingStatus status,
        @Nullable String observation
) {

}
