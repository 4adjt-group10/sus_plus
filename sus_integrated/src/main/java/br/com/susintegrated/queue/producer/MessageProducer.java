package br.com.susintegrated.queue.producer;

import br.com.susintegrated.config.RabbitConfig;
import br.com.susintegrated.queue.producer.dto.MessageBodyForPatientRecord;
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

    public void sendToManager(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_INTEGRATED_MANAGER, rabbitMessage);
    }

    public void sendToUnity(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_INTEGRATED_UNITY, rabbitMessage);
    }

    public void sendToScheduling(MessageBodyForScheduling message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_INTEGRATED_SCHEDULING, rabbitMessage);
    }

    public void sendToPatientRecord(MessageBodyForPatientRecord messageBodyForPatientRecord) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(messageBodyForPatientRecord, messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_PATIENT_RECORD_INTEGRATED, rabbitMessage);
    }
}
