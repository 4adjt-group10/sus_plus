package br.com.susintegrated.config;
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
    public static final String QUEUE_NAME_INTEGRATED_MANAGER = "IntegratedManagerQueue";
    public static final String ROUTING_KEY_INTEGRATED_MANAGER = "routing.key.integrated_manager";

    public static final String QUEUE_NAME_INTEGRATED_UNITY = "IntegratedUnityQueue";
    public static final String ROUTING_KEY_INTEGRATED_UNITY = "routing.key.integrated_unity";

    public static final String QUEUE_NAME_INTEGRATED_SCHEDULING = "IntegratedSchedulingQueue";
    public static final String ROUTING_KEY_INTEGRATED_SCHEDULING = "routing.key.integrated_scheduling";

    public static final String QUEUE_NAME_INTEGRATED_PATIENT_RECORD = "IntegratedPatientRecordQueue";
    public static final String ROUTING_KEY_INTEGRATED_PATIENT_RECORD = "routing.key.integrated_patient_record";

    //consumer
    public static final String QUEUE_NAME_MANAGER_INTEGRATED = "ManagerIntegratedQueue";
    public static final String ROUTING_KEY_MANAGER_INTEGRATED = "routing.key.manager_integrated";

    public static final String QUEUE_NAME_SCHEDULING_INTEGRATED = "SchedulingIntegratedQueue";
    public static final String ROUTING_KEY_SCHEDULING_INTEGRATED = "routing.key.scheduling_integrated";

    public static final String QUEUE_NAME_PATIENT_RECORD_INTEGRATED = "PatientRecordIntegratedQueue";
    public static final String ROUTING_KEY_PATIENT_RECORD_INTEGRATED = "routing.key.patient_record_integrated";




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
    public Queue queueManagerIntegrated() {
        return new Queue(QUEUE_NAME_MANAGER_INTEGRATED, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingManagerIntegrated(Queue queueManagerIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queueManagerIntegrated).to(exchange).with(ROUTING_KEY_MANAGER_INTEGRATED);
    }

    @Bean
    public Queue queueSchedulingIntegrated() {
        return new Queue(QUEUE_NAME_SCHEDULING_INTEGRATED, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingSchedulingIntegrated(Queue queueSchedulingIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queueSchedulingIntegrated).to(exchange).with(ROUTING_KEY_SCHEDULING_INTEGRATED);
    }


    @Bean
    public Queue queuePatientRecordIntegrated() {
        return new Queue(QUEUE_NAME_PATIENT_RECORD_INTEGRATED, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingPatientRecordIntegrated(Queue queuePatientRecordIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queuePatientRecordIntegrated).to(exchange).with(ROUTING_KEY_PATIENT_RECORD_INTEGRATED);
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

    @Bean
    public Queue queueIntegratedScheduling() {
        return new Queue(QUEUE_NAME_INTEGRATED_SCHEDULING, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingIntegratedScheduling(Queue queueIntegratedScheduling, TopicExchange exchange) {
        return BindingBuilder.bind(queueIntegratedScheduling).to(exchange).with(ROUTING_KEY_INTEGRATED_SCHEDULING);
    }

    @Bean
    public Queue queueIntegratedPatientRecord() {
        return new Queue(QUEUE_NAME_INTEGRATED_PATIENT_RECORD, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingIntegratedPatientRecord(Queue queueIntegratedPatientRecord, TopicExchange exchange) {
        return BindingBuilder.bind(queueIntegratedPatientRecord).to(exchange).with(ROUTING_KEY_INTEGRATED_PATIENT_RECORD);
    }
}