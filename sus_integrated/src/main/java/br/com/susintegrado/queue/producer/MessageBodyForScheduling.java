package br.com.susintegrado.queue.producer;

import java.util.UUID;

public record MessageBodyForScheduling(boolean validatedPatient, UUID schedulingId) {

}
