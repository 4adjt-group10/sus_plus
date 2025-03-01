package br.com.sus_scheduling.queue.consumer.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyByManager(
        LocalDateTime appointment,
        UUID professionalId,
        boolean isValidAppointment,
        UUID schedulingId
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
