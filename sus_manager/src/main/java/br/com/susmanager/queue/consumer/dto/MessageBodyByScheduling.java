package br.com.susmanager.queue.consumer.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyByScheduling(LocalDateTime appointment, UUID professionalId, UUID schedulingId) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
