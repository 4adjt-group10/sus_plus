package br.com.sus_scheduling.queue.producer.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyForUnity(@NotNull UUID specialityId,
                                  @NotNull UUID unityId,
                                  @NotNull UUID professionalId,
                                  @NotNull @FutureOrPresent LocalDateTime appointment,
                                  @NotNull UUID schedulingId) {
}
