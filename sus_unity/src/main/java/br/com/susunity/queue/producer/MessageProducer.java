package br.com.susunity.queue.producer;

import br.com.susunity.config.RabbitConfig;
import br.com.susunity.queue.producer.dto.MessageBodyForManager;
import br.com.susunity.queue.producer.dto.MessageBodyForPatientRecord;
import br.com.susunity.queue.producer.dto.MessageBodyForScheduler;
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

    public void sendToManager(MessageBodyForManager message) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, getProperties());
        rabbitTemplate.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_UNITY_MANAGER, rabbitMessage);
    }

    public void sendToScheduling(MessageBodyForScheduler message) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(message, getProperties());
        rabbitTemplate.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_UNITY_SCHEDULE, rabbitMessage);
    }

    public void sendToPatientRecord(MessageBodyForPatientRecord messageBodyForPatientRecord) {
        Message rabbitMessage = jackson2JsonMessageConverter.toMessage(messageBodyForPatientRecord, getProperties());
        rabbitTemplate.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_UNITY_PATIENT_RECORD, rabbitMessage);
    }

    private static MessageProperties getProperties() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return messageProperties;
    }
}
