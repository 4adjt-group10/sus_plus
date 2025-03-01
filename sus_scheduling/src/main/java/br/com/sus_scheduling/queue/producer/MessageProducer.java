package br.com.sus_scheduling.queue.producer;

import br.com.sus_scheduling.config.RabbitConfig;
import br.com.sus_scheduling.queue.producer.dto.MessageBodyForIntegrated;
import br.com.sus_scheduling.queue.producer.dto.MessageBodyForManager;
import br.com.sus_scheduling.queue.producer.dto.MessageBodyForUnity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    public MessageProducer(RabbitTemplate rabbitTemplate, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.jackson2JsonMessageConverter = jackson2JsonMessageConverter;
    }

    public void sendToUnity(MessageBodyForUnity message) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, getProperties());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_SCHEDULING_UNITY, rabbitMessage);
    }

    public void sendToIntegrated(MessageBodyForIntegrated message) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, getProperties());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_SCHEDULING_INTEGRATED, rabbitMessage);
    }

    public void sendToManager(MessageBodyForManager message) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, getProperties());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_SCHEDULING_MANAGER, rabbitMessage);
    }

    private static MessageProperties getProperties() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return messageProperties;
    }
}
