package br.com.susmanager.queue.producer.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyForScheduling(
        LocalDateTime appointment,
        UUID professionalId,
        boolean isValidAppointment,
        UUID schedulingId
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public MessageBodyForScheduling(UUID schedulingId, LocalDateTime appointment) {
        this(appointment, null, false, schedulingId);
    }
}
