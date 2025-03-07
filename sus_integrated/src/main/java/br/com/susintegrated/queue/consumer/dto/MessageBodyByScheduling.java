package br.com.susintegrated.queue.consumer.dto;

import java.io.Serializable;
import java.util.UUID;

public record MessageBodyByScheduling(UUID patientId, UUID schedulingId) implements Serializable {

    private static final long serialVersionUID = 1L;

}
