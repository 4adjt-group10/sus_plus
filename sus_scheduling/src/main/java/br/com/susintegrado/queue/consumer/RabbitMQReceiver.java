package br.com.susintegrado.queue.consumer;

import br.com.susintegrado.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class RabbitMQReceiver {

    private final Logger logger = Logger.getLogger(RabbitMQReceiver.class.getName());

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_UNITY_SCHEDULING, ackMode = "MANUAL")
    public void receiveUnityMessage(Message message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            SimpleMessageConverter messageConverter = new SimpleMessageConverter();
            String messageBody = (String) messageConverter.fromMessage(message);
            logger.info(String.format("Received <%s>", messageBody));
            // Adicione aqui a lógica para processar a mensagem
            // Se o processamento for bem-sucedido, confirme a mensagem
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
    public void receiveIntegratedMessage(Message message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            SimpleMessageConverter messageConverter = new SimpleMessageConverter();
            String messageBody = (String) messageConverter.fromMessage(message);
            logger.info(String.format("Received <%s>", messageBody));
            // Adicione aqui a lógica para processar a mensagem
            // Se o processamento for bem-sucedido, confirme a mensagem
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
}
