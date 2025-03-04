package br.com.susunity.queue.consumer.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyByScheduling(
        UUID specialityId,
        UUID unityId,
        UUID professionalId,
        LocalDateTime appointment,
        UUID schedulingId
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
