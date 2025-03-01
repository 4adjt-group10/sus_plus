package br.com.sus_scheduling.queue.consumer;

import java.util.UUID;

public record MessageBodyByIntegrated(boolean validatedPatient, UUID schedulingId) {

}
