package br.com.susunity.queue.consumer.dto.scheduler;


import java.time.LocalDateTime;
import java.util.UUID;

public record MessageBodyByScheduling(
        UUID specialityId,
        UUID unityId,
        UUID professionalId,
        LocalDateTime appointment,
        UUID schedulingId
) {

}
