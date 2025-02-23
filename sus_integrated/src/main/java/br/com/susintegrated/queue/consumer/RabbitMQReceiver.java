package br.com.susintegrated.queue.consumer;

import br.com.susintegrated.config.RabbitConfig;
import br.com.susintegrated.queue.consumer.dto.MessageBodyByPatienteRecord;
import br.com.susintegrated.service.PatientService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RabbitMQReceiver {

    private final Logger logger = Logger.getLogger(RabbitMQReceiver.class.getName());

    private final PatientService patientService;

    public RabbitMQReceiver(PatientService patientService) {
        this.patientService = patientService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_MANAGER_INTEGRATED, ackMode = "MANUAL")
    public void receiveManagerMessage(Message message,
                                      Channel channel,
                                      @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            SimpleMessageConverter messageConverter = new SimpleMessageConverter();
            String messageBody = (String) messageConverter.fromMessage(message);
            logger.info(String.format("Received <%s>", messageBody));
            // Adicione aqui a lógica para processar a mensagem
            // Se o processamento for bem-sucedido, confirme a mensagem
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_SCHEDULING_INTEGRATED, ackMode = "MANUAL")
    public void receiveSchedulingMessage(MessageBodyByScheduling message,
                                         Channel channel,
                                         @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", message));
            patientService.validatePatientToScheduling(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_PATIENT_RECORD_INTEGRATED, ackMode = "MANUAL")
    public void receivePatientRecordMessage(MessageBodyByPatienteRecord message,
                                            Channel channel,
                                            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", message));
            patientService.validatePatientToPatientRecord(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
