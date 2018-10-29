package ch.sebooom.jelastic.activemqproducer.application.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Configuration
public class JmsConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String JMS_BROKER_URI_PROPS;

    @Value("#{systemEnvironment['broker.uri']}")
    private String JMS_BROKER_URI_ENV;

    @Value("${app.activemq.test.queue.name}")
    private String TEST_QUEUE_NAME;

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }


    @Bean
    public ConnectionFactory connectionFactory(){
        log.info("JMS Broker uri from properties: {}", JMS_BROKER_URI_PROPS);
        log.info("JMS Broker uri from environnement: {}", JMS_BROKER_URI_ENV);

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        if(null!= JMS_BROKER_URI_ENV){
            log.info("JMS Broker uri from env used...");
            connectionFactory.setBrokerURL(JMS_BROKER_URI_ENV);
        }else{
            log.info("JMS Broker uri from properties used...");
            connectionFactory.setBrokerURL(JMS_BROKER_URI_PROPS);
        }

        return connectionFactory;
    }

    @Bean
    public Queue testQueue(){
        return new ActiveMQQueue(TEST_QUEUE_NAME);
    }

    @Bean
    public JmsTemplate jmsTestTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setMessageConverter(jacksonJmsMessageConverter());
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestination(testQueue());
        return template;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        return scheduler;

    }
}
