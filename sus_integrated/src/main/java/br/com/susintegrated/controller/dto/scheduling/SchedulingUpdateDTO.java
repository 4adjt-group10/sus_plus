package br.com.susintegrated.controller.dto.scheduling;

import br.com.susintegrated.model.scheduling.SchedulingStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record SchedulingUpdateDTO(
        @NotNull UUID procedureId,
        @NotNull UUID professionalId,
        @FutureOrPresent Optional<LocalDateTime> appointment,
        @NotNull SchedulingStatus status
) {

}
