package br.com.suspatientrecord.config;
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
    public static final String QUEUE_NAME_PATIENT_RECORD_UNITY = "PatientRecordUnityQueue";
    public static final String ROUTING_KEY_PATIENT_RECORD_UNITY = "routing.key.patient_record_Unity";
    public static final String QUEUE_NAME_PATIENT_RECORD_INTEGRATED = "PatientRecordIntegratedQueue";
    public static final String ROUTING_KEY_PATIENT_RECORD_INTEGRATED = "routing.key.patient_record_integrated";
    //consumer
    public static final String QUEUE_NAME_UNITY_PATIENT_RECORD = "UnityPatientRecordQueue";
    public static final String ROUTING_KEY_UNITY_PATIENT_RECORD = "routing.key.unity_patient_record";
    public static final String QUEUE_NAME_INTEGRATED_PATIENT_RECORD = "IntegratedPatientRecordQueue";
    public static final String ROUTING_KEY_INTEGRATED_PATIENT_RECORD = "routing.key.Integrated_patient_record";

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
    public Queue queueUnityPatientRecord() {
        return new Queue(QUEUE_NAME_UNITY_PATIENT_RECORD, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingUnityPatientRecord(Queue queueUnityPatientRecord, TopicExchange exchange) {
        return BindingBuilder.bind(queueUnityPatientRecord).to(exchange).with(ROUTING_KEY_UNITY_PATIENT_RECORD);
    }

    @Bean
    public Queue queueIntegratedPatientRecord() {
        return new Queue(QUEUE_NAME_INTEGRATED_PATIENT_RECORD, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingIntegratedPatientRecord(Queue queueIntegratedPatientRecord, TopicExchange exchange) {
        return BindingBuilder.bind(queueIntegratedPatientRecord).to(exchange).with(ROUTING_KEY_INTEGRATED_PATIENT_RECORD);
    }



    //producer
    @Bean
    public Queue queuePatientRecordUnity() {
        return new Queue(QUEUE_NAME_PATIENT_RECORD_UNITY, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingPatientRecordUnity(Queue queuePatientRecordUnity, TopicExchange exchange) {
        return BindingBuilder.bind(queuePatientRecordUnity).to(exchange).with(ROUTING_KEY_PATIENT_RECORD_UNITY);
    }

    @Bean
    public Queue queuePatientRecordIntegrated() {
        return new Queue(QUEUE_NAME_PATIENT_RECORD_INTEGRATED, true); // true indica que a fila é durável
    }
    @Bean
    public Binding bindingPatientRecordIntegrated(Queue queuePatientRecordIntegrated, TopicExchange exchange) {
        return BindingBuilder.bind(queuePatientRecordIntegrated).to(exchange).with(ROUTING_KEY_PATIENT_RECORD_INTEGRATED);
    }


}