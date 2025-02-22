package br.com.susscheduling.queue.consumer;

import java.util.UUID;

public record MessageBodyByIntegrated(boolean validatedPatient, UUID schedulingId) {

}
