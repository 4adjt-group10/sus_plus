package br.com.susunity.queue.consumer;

import br.com.susunity.config.RabbitConfig;
import br.com.susunity.queue.consumer.dto.manager.MessageBodyByManager;
import br.com.susunity.queue.consumer.dto.MessageBodyByPatientRecord;
import br.com.susunity.queue.consumer.dto.MessageBodyByScheduling;
import br.com.susunity.service.ProfessionalAvailabilityService;
import br.com.susunity.service.UnityService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RabbitMQReceiver {

    private final UnityService unityService;
    private final ProfessionalAvailabilityService professionalAvailabilityService;

    private final Logger logger = Logger.getLogger(RabbitMQReceiver.class.getName());

    public RabbitMQReceiver(UnityService unityService, ProfessionalAvailabilityService professionalAvailabilityService) {
        this.unityService = unityService;
        this.professionalAvailabilityService = professionalAvailabilityService;
    }


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_MANAGER_UNITY, ackMode = "MANUAL")
    public void receiveProfessionalMessage(MessageBodyByManager message,
                                           Channel channel,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", message.toString()));
            unityService.updateProfessional(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_SCHEDULING_UNITY, ackMode = "MANUAL")
    public void receiveSchedulingMessage(MessageBodyByScheduling message,
                                         Channel channel,
                                         @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", message.toString()));
            professionalAvailabilityService.validateForScheduling(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_PATIENT_RECORD_UNITY, ackMode = "MANUAL")
    public void receivePatientRecordMessage(MessageBodyByPatientRecord message,
                                            Channel channel,
                                            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", message.toString()));
            unityService.getUnityForPatientRecord(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }

}
