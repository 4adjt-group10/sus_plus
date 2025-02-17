package br.com.susmanager.queue.consumer;

import br.com.susmanager.config.RabbitConfig;
import br.com.susmanager.queue.consumer.dto.unity.UnityProfessional;
import br.com.susmanager.service.ProfessionalManagerService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RabbitMQReceiver {
    private final ProfessionalManagerService professionalManagerService;
    private final Logger logger = Logger.getLogger(RabbitMQReceiver.class.getName());

    public RabbitMQReceiver(ProfessionalManagerService professionalManagerService) {
        this.professionalManagerService = professionalManagerService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_UNITY_MANAGER, ackMode = "MANUAL", containerFactory = "rabbitListenerContainerFactory")
    public void receiveUnityMessage(UnityProfessional messageBody,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            logger.info(String.format("Received <%s>", messageBody.toString()));
            professionalManagerService.findProfessionalMQ(messageBody);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message: ".concat(e.getMessage()), e);
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
