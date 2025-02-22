package br.com.susscheduling.queue.consumer;

import java.io.Serializable;
import java.util.UUID;

public record MessageBodyByUnity(boolean validatedSpeciality, boolean validatedUnity, UUID schedulingId) implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean isValidated() {
        return validatedSpeciality && validatedUnity;
    }
}
