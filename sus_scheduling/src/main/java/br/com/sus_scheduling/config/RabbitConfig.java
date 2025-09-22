package br.com.sus_scheduling.config;

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

import static br.com.sus_scheduling.queue.consumer.ConsumerUtils.*;
import static br.com.sus_scheduling.queue.producer.ProducerUtils.*;

@Configuration
public class RabbitConfig {

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
