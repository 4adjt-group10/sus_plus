package br.com.susintegrado.queue.producer;

import br.com.susintegrado.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;


    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToManager(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_INTEGRATED_MANAGER, message);
    }

    public void sendToUnity(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_INTEGRATED_UNITY, message);
    }
}
