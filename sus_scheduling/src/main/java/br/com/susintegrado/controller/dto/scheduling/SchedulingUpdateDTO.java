package br.com.susintegrado.controller.dto.scheduling;

import br.com.susintegrado.model.scheduling.SchedulingStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record SchedulingUpdateDTO(
        @NotNull UUID professionalId,
        @FutureOrPresent Optional<LocalDateTime> appointment,
        @NotNull SchedulingStatus status
) {

}
