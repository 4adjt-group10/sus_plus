package br.com.susmanager.queue.producer;


import br.com.susmanager.config.RabbitConfig;
import br.com.susmanager.queue.producer.dto.Professional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;


    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToUnity(Professional message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_MANAGER_UNITY, message);
    }

    public void sendToIntegrated(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_MANAGER_INTEGRATED, message);
    }
}
