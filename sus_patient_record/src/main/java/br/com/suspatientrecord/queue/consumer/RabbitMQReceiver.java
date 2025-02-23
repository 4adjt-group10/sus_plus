package br.com.suspatientrecord.queue.consumer;

import br.com.suspatientrecord.config.RabbitConfig;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByIntegrated;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByUnity;
import br.com.suspatientrecord.service.PatientRecordService;
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
    private final PatientRecordService service;
    private final Logger logger = Logger.getLogger(RabbitMQReceiver.class.getName());

    public RabbitMQReceiver(PatientRecordService service) {
        this.service = service;
    }


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_INTEGRATED_PATIENT_RECORD, ackMode = "MANUAL")
    public void receivePatientMessage(MessageBodyByIntegrated message,
                                      Channel channel,
                                      @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", message.toString()));
            service.updatePatientRecoedByIntegrated(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_UNITY_PATIENT_RECORD, ackMode = "MANUAL")
    public void receiveUnityMessage(MessageBodyByUnity message,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", message.toString()));
            service.updatePatientRecoedByUnity(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }

}
