package br.com.susintegrado.config;

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
    public static final String QUEUE_NAME_SCHEDULING_UNITY = "SchedulingUnityQueue";
    public static final String ROUTING_KEY_SCHEDULING_UNITY = "routing.key.scheduling_unity";

    public static final String QUEUE_NAME_SCHEDULING_INTEGRATED = "SchedulingIntegratedQueue";
    public static final String ROUTING_KEY_SCHEDULING_INTEGRATED = "routing.key.scheduling_integrated";

    //consumer
    public static final String QUEUE_NAME_UNITY_SCHEDULING = "UnitySchedulingQueue";
    public static final String ROUTING_KEY_UNITY_SCHEDULING = "routing.key.unity_scheduling";

    public static final String QUEUE_NAME_INTEGRATED_SCHEDULING = "IntegratedSchedulingQueue";
    public static final String ROUTING_KEY_INTEGRATED_SCHEDULING = "routing.key.integrated_scheduling";

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

    //producer
    @Bean
    public Queue queueSchedulingUnity() {
        return new Queue(QUEUE_NAME_SCHEDULING_UNITY, true);
    }

    @Bean
    public Binding bindingSchedulingUnity(Queue queueSchedulingUnity, TopicExchange exchange) {
        return BindingBuilder.bind(queueSchedulingUnity).to(exchange).with(ROUTING_KEY_SCHEDULING_UNITY);
    }

    @Bean
    public Queue queueSchedulingIntegrated() {
        return new Queue(QUEUE_NAME_SCHEDULING_INTEGRATED, true);
    }

    @Bean
    public Binding bindingSchedulingIntegrated(Queue queueSchedulingIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queueSchedulingIntegrated).to(exchange).with(ROUTING_KEY_SCHEDULING_INTEGRATED);
    }

    //consumer
    @Bean
    public Queue queueUnityScheduling() {
        return new Queue(QUEUE_NAME_UNITY_SCHEDULING, true);
    }

    @Bean
    public Binding bindingUnityScheduling(Queue queueUnityScheduling, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnityScheduling).to(exchange).with(ROUTING_KEY_UNITY_SCHEDULING);
    }

    @Bean
    public Queue queueIntegratedScheduling() {
        return new Queue(QUEUE_NAME_INTEGRATED_SCHEDULING, true);
    }

    @Bean
    public Binding bindingIntegratedScheduling(Queue queueIntegratedScheduling, TopicExchange exchange) {
        return BindingBuilder.bind(queueIntegratedScheduling).to(exchange).with(ROUTING_KEY_INTEGRATED_SCHEDULING);
    }
}
