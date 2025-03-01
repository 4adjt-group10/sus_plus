package br.com.susmanager.queue.producer;


import br.com.susmanager.config.RabbitConfig;
import br.com.susmanager.queue.producer.dto.MessageBodyForScheduling;
import br.com.susmanager.queue.producer.dto.MessageBodyForUnity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    
    private final RabbitTemplate rabbitTemplate;
    private final Jackson2JsonMessageConverter jackson2JsonMessageConverter;
    
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    }

    private static MessageProperties getProperties() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return messageProperties;
    }
    
    public void sendToUnity(MessageBodyForUnity message) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, getProperties());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_MANAGER_UNITY, rabbitMessage);
    }
}
