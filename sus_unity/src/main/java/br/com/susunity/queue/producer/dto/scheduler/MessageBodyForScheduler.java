package br.com.susunity.queue.producer.dto.scheduler;

import java.io.Serializable;
import java.util.UUID;

public record MessageBodyForScheduler(boolean validatedSpeciality, boolean validatedUnity, UUID schedulingId) implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean isValidated() {
        return validatedSpeciality && validatedUnity;
    }
}
