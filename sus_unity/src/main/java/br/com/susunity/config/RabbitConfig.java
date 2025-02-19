package br.com.susunity.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //producer
    public static final String QUEUE_NAME_UNITY_MANAGER = "UnityManagerQueue";
    public static final String ROUTING_KEY_UNITY_MANAGER = "routing.key.unity_manager";
    public static final String QUEUE_NAME_UNITY_INTEGRATED = "UnityIntegratedQueue";
    public static final String ROUTING_KEY_UNITY_INTEGRATED = "routing.key.unity_integrated";
    //consumer
    public static final String QUEUE_NAME_MANAGER_UNITY = "ManagerUnityQueue";
    public static final String ROUTING_KEY_MANAGER_UNITY = "routing.key.manager_unity";


    public static final String EXCHANGE_NAME = "OrderExchange";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    //consumer
    @Bean
    public Queue queueManagerUnity() {
        return new Queue(QUEUE_NAME_MANAGER_UNITY, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingManagerUnity(Queue queueManagerUnity, TopicExchange exchange) {
        return BindingBuilder.bind(queueManagerUnity).to(exchange).with(ROUTING_KEY_MANAGER_UNITY);
    }



    //producer
    @Bean
    public Queue queueUnityManager() {
        return new Queue(QUEUE_NAME_UNITY_MANAGER, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingUnityManager(Queue queueUnityManager, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnityManager).to(exchange).with(ROUTING_KEY_UNITY_MANAGER);
    }

    @Bean
    public Queue queueUnityIntegrated() {
        return new Queue(QUEUE_NAME_UNITY_INTEGRATED, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingUnityIntegrated(Queue queueUnityIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnityIntegrated).to(exchange).with(ROUTING_KEY_UNITY_INTEGRATED);
    }


}