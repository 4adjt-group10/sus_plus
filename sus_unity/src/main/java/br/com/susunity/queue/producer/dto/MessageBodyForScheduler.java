package br.com.susunity.queue.producer.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record MessageBodyForScheduler(
        boolean validatedSpeciality,
        boolean validatedUnity,
        boolean validatedProfessional,
        boolean validatedAppointment,
        UUID schedulingId
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public boolean isValidated() {
        return validatedSpeciality && validatedUnity;
    }
}
