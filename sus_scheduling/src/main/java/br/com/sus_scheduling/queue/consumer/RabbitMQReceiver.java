package br.com.sus_scheduling.queue.consumer;


import br.com.sus_scheduling.config.RabbitConfig;
import br.com.sus_scheduling.queue.consumer.dto.MessageBodyByIntegrated;
import br.com.sus_scheduling.queue.consumer.dto.MessageBodyByManager;
import br.com.sus_scheduling.queue.consumer.dto.MessageBodyByUnity;
import br.com.sus_scheduling.service.SchedulingService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class RabbitMQReceiver {

    private final Logger logger = Logger.getLogger(RabbitMQReceiver.class.getName());

    private final SchedulingService schedulingService;

    public RabbitMQReceiver(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_UNITY_SCHEDULING, ackMode = "MANUAL")
    public void receiveUnityMessage(MessageBodyByUnity message,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            logger.info(String.format("Received <%s>", message));
            schedulingService.postValidateUnity(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.severe("Error processing message: ".concat(e.getMessage()));
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ioException) {
                logger.severe("Error rejecting message: ".concat(ioException.getMessage()));
            }
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_INTEGRATED_SCHEDULING, ackMode = "MANUAL")
    public void receiveIntegratedMessage(MessageBodyByIntegrated message,
                                         Channel channel,
                                         @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            logger.info(String.format("Received <%s>", message));
            schedulingService.postValidateIntegrated(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.severe("Error processing message: ".concat(e.getMessage()));
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ioException) {
                logger.severe("Error rejecting message: ".concat(ioException.getMessage()));
            }
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_MANAGER_SCHEDULING, ackMode = "MANUAL")
    public void receiveManagerMessage(MessageBodyByManager message,
                                      Channel channel,
                                      @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            logger.info(String.format("Received <%s>", message));
            schedulingService.postValidateManager(message);
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            logger.severe("Error processing message: ".concat(e.getMessage()));
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ioException) {
                logger.severe("Error rejecting message: ".concat(ioException.getMessage()));
            }
        }
    }
}
