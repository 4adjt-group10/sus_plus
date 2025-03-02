package br.com.susintegrated.queue.producer.dto;

import java.util.UUID;

public record MessageBodyForScheduling(boolean validatedPatient, UUID schedulingId) {

}
