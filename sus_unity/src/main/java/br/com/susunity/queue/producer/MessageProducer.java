package br.com.susunity.queue.producer;

import br.com.susunity.config.RabbitConfig;
import br.com.susunity.queue.consumer.dto.Professional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;


    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToManager(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_UNITY_MANAGER, message);
    }

    public void sendToIntegrated(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_UNITY_INTEGRATED, message);
    }

}
