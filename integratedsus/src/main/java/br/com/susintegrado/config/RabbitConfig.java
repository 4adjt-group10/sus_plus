package br.com.susintegrado.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //producer
    public static final String QUEUE_NAME_INTEGRATED_MANAGER = "IntegratedManagerQueue";
    public static final String ROUTING_KEY_INTEGRATED_MANAGER = "routing.key.integrated_manager";
    public static final String QUEUE_NAME_INTEGRATED_UNITY = "IntegratedUnityQueue";
    public static final String ROUTING_KEY_INTEGRATED_UNITY = "routing.key.integrated_unity";
    //consumer
    public static final String QUEUE_NAME_MANAGER_INTEGRATED = "ManagerIntegratedQueue";
    public static final String ROUTING_KEY_MANAGER_INTEGRATED = "routing.key.manager_integrated";


    public static final String EXCHANGE_NAME = "OrderExchange";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    //consumer
    @Bean
    public Queue queueManagerIntegrated() {
        return new Queue(QUEUE_NAME_MANAGER_INTEGRATED, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingManagerIntegrated(Queue queueManagerIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queueManagerIntegrated).to(exchange).with(ROUTING_KEY_MANAGER_INTEGRATED);
    }


    //producer
    @Bean
    public Queue queueIntegratedManager() {
        return new Queue(QUEUE_NAME_INTEGRATED_MANAGER, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingUnityManager(Queue queueIntegratedManager, TopicExchange exchange) {
        return BindingBuilder.bind(queueIntegratedManager).to(exchange).with(ROUTING_KEY_INTEGRATED_MANAGER);
    }

    @Bean
    public Queue queueIntegratedUnity() {
        return new Queue(QUEUE_NAME_INTEGRATED_UNITY, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingIntegratedUnity(Queue queueIntegratedUnity, TopicExchange exchange) {
        return BindingBuilder.bind(queueIntegratedUnity).to(exchange).with(ROUTING_KEY_INTEGRATED_UNITY);
    }


}