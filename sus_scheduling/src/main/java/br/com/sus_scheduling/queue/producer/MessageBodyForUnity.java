package br.com.sus_scheduling.queue.producer;

import br.com.sus_scheduling.controller.dto.scheduling.SchedulingFormDTO;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MessageBodyForUnity(UUID specialityId, @NotNull UUID unityId, UUID schedulingId) {

    public MessageBodyForUnity(SchedulingFormDTO formDTO, UUID schedulingId) {
        this(formDTO.specialityId(), formDTO.unityId(), schedulingId);
    }
}
