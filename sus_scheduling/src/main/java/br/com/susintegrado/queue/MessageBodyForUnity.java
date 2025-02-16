package br.com.susintegrado.queue;

import br.com.susintegrado.controller.dto.scheduling.SchedulingFormDTO;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyForUnity(@NotNull UUID specialityId,
                                  @NotNull UUID unityId,
                                  @NotNull @FutureOrPresent LocalDateTime appointment) {

    public MessageBodyForUnity(SchedulingFormDTO formDTO) {
        this(formDTO.specialityId(), formDTO.unityId(), formDTO.appointment());
    }
}
