package br.com.suspatientrecord.queue.producer;

import br.com.suspatientrecord.config.RabbitConfig;
import br.com.suspatientrecord.queue.producer.dto.RecordToIntegrated;
import br.com.suspatientrecord.queue.producer.dto.RecordToUnity;
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

    public void sendToIntegrated(RecordToIntegrated message) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, getProperties());
        rabbitTemplate.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_PATIENT_RECORD_INTEGRATED, rabbitMessage);    }

    public void sendToUnity(RecordToUnity recordToUnity) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(recordToUnity, getProperties());
        rabbitTemplate.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_PATIENT_RECORD_UNITY, rabbitMessage);
    }

    private static MessageProperties getProperties() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return messageProperties;
    }
}
