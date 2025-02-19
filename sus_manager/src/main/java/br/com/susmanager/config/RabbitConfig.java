package br.com.susmanager.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // Producer
    public static final String QUEUE_NAME_MANAGER_UNITY = "ManagerUnityQueue";
    public static final String ROUTING_KEY_MANAGER_UNITY = "routing.key.manager_unity";
    public static final String QUEUE_NAME_MANAGER_INTEGRATED = "ManagerIntegratedQueue";
    public static final String ROUTING_KEY_MANAGER_INTEGRATED = "routing.key.Manager_integrated";

    // Consumer
    public static final String QUEUE_NAME_UNITY_MANAGER = "UnityManagerQueue";
    public static final String ROUTING_KEY_UNITY_MANAGER = "routing.key.unity_manager";

    public static final String EXCHANGE_NAME = "OrderExchange";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
//
//    @Bean
//    public DirectMessageListenerContainer directMessageListenerContainer(CachingConnectionFactory connectionFactory) {
//        DirectMessageListenerContainer container = new DirectMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        return container;
//    }

    @Bean
    public Queue queueUnityManager() {
        return new Queue(QUEUE_NAME_UNITY_MANAGER, true); // true indica que a fila é durável
    }

    @Bean
    public Binding bindingUnityManager(Queue queueUnityManager, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnityManager).to(exchange).with(ROUTING_KEY_UNITY_MANAGER);
    }

    @Bean
    public Queue queueManagerUnity() {
        return new Queue(QUEUE_NAME_MANAGER_UNITY, true); // true indica que a fila é durável
    }

    @Bean
    public Binding bindingManagerUnity(Queue queueManagerUnity, TopicExchange exchange) {
        return BindingBuilder.bind(queueManagerUnity).to(exchange).with(ROUTING_KEY_MANAGER_UNITY);
    }

    @Bean
    public Queue queueManagerIntegrated() {
        return new Queue(QUEUE_NAME_MANAGER_INTEGRATED, true); // true indica que a fila é durável
    }

    @Bean
    public Binding bindingManagerIntegrated(Queue queueManagerIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queueManagerIntegrated).to(exchange).with(ROUTING_KEY_MANAGER_INTEGRATED);
    }
}
