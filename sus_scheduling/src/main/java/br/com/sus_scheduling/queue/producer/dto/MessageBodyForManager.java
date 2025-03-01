package br.com.sus_scheduling.queue.producer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyForManager(LocalDateTime appointment, UUID professionalId, UUID schedulingId) {
}
