package br.com.sus_scheduling.queue.consumer.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record MessageBodyByIntegrated(boolean validatedPatient, UUID schedulingId) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
