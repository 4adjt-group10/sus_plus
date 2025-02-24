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

    public static final String QUEUE_NAME_UNITY_SCHEDULE = "UnitySchedulingQueue";
    public static final String ROUTING_KEY_UNITY_SCHEDULE = "routing.key.unity_scheduling";

    public static final String QUEUE_NAME_UNITY_INTEGRATED = "UnityIntegratedQueue";
    public static final String ROUTING_KEY_UNITY_INTEGRATED = "routing.key.unity_integrated";

    public static final String QUEUE_NAME_UNITY_PATIENT_RECORD = "UnityPatientRecordQueue";
    public static final String ROUTING_KEY_UNITY_PATIENT_RECORD = "routing.key.unity_patient_record";
    //consumer
    public static final String QUEUE_NAME_MANAGER_UNITY = "ManagerUnityQueue";
    public static final String ROUTING_KEY_MANAGER_UNITY = "routing.key.manager_unity";
    public static final String QUEUE_NAME_SCHEDULING_UNITY = "SchedulingUnityQueue";
    public static final String ROUTING_KEY_SCHEDULING_UNITY = "routing.key.scheduling_unity";
    public static final String QUEUE_NAME_PATIENT_RECORD_UNITY = "PatientRecordUnityQueue";
    public static final String ROUTING_KEY_PATIENT_RECORD_UNITY = "routing.key.patient_record_Unity";

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

    @Bean
    public Queue queueSchedulingUnity() {
        return new Queue(QUEUE_NAME_SCHEDULING_UNITY, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingSchedulingUnity(Queue queueSchedulingUnity, TopicExchange exchange) {
        return BindingBuilder.bind(queueSchedulingUnity).to(exchange).with(ROUTING_KEY_SCHEDULING_UNITY);
    }

    @Bean
    public Queue queuePatientRecordUnity() {
        return new Queue(QUEUE_NAME_PATIENT_RECORD_UNITY, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingPatientRecordUnity(Queue queuePatientRecordUnity, TopicExchange exchange) {
        return BindingBuilder.bind(queuePatientRecordUnity).to(exchange).with(ROUTING_KEY_PATIENT_RECORD_UNITY);
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
    public Queue queueUnitySchedule() {
        return new Queue(QUEUE_NAME_UNITY_SCHEDULE, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingUnitySchedule(Queue queueUnitySchedule, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnitySchedule).to(exchange).with(ROUTING_KEY_UNITY_SCHEDULE);
    }

    @Bean
    public Queue queueUnityIntegrated() {
        return new Queue(QUEUE_NAME_UNITY_INTEGRATED, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingUnityIntegrated(Queue queueUnityIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnityIntegrated).to(exchange).with(ROUTING_KEY_UNITY_INTEGRATED);
    }

    @Bean
    public Queue queueUnityPatientRecord() {
        return new Queue(QUEUE_NAME_UNITY_PATIENT_RECORD, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingUnityPatientRecord(Queue queueUnityPatientRecord, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnityPatientRecord).to(exchange).with(ROUTING_KEY_UNITY_PATIENT_RECORD);
    }


}