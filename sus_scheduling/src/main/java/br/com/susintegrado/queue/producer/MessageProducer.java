package br.com.susintegrado.queue.producer;

import br.com.susintegrado.config.RabbitConfig;
import br.com.susintegrado.queue.MessageBodyForIntegrated;
import br.com.susintegrado.queue.MessageBodyForUnity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToUnity(MessageBodyForUnity message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_SCHEDULING_UNITY, message);
    }

    public void sendToIntegrated(MessageBodyForIntegrated message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_SCHEDULING_INTEGRATED, message);
    }
}
