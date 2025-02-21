package br.com.susintegrado.queue.consumer;

import java.util.UUID;

public record MessageBodyByIntegrated(boolean validatedPatient, UUID schedulingId) {

}
