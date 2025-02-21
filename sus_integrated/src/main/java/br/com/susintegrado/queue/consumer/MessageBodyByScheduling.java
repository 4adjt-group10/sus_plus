package br.com.susintegrado.queue.consumer;

import java.io.Serializable;
import java.util.UUID;

public record MessageBodyByScheduling(UUID patientId, UUID schedulingId) implements Serializable {

    private static final long serialVersionUID = 1L;

}
