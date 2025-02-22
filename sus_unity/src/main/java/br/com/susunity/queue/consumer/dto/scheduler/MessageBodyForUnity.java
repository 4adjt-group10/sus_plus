package br.com.susunity.queue.consumer.dto.scheduler;


import java.util.UUID;

public record MessageBodyForUnity(UUID specialityId, UUID unityId, UUID schedulingId) {

}
