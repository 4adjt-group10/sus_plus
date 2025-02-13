package br.com.susunity.queue.consumer;

import br.com.susunity.config.RabbitConfig;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RabbitMQReceiver {

    private final Logger logger = Logger.getLogger(RabbitMQReceiver.class.getName());


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_MANAGER_UNITY, ackMode = "MANUAL")
    public void receiveDeliveryMessage(Message message,
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
}
