package br.com.susintegrado.queue.producer;

import java.io.Serializable;
import java.util.UUID;

public record MessageBodyForIntegrated(UUID patientId, UUID schedulingId) implements Serializable {

    private static final long serialVersionUID = 1L;

}
