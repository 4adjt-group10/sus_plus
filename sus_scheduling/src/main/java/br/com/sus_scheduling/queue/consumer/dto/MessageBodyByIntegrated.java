package br.com.sus_scheduling.queue.consumer.dto;

import java.util.UUID;

public record MessageBodyByIntegrated(boolean validatedPatient, UUID schedulingId) {

}
