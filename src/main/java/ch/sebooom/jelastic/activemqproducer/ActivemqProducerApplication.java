package ch.sebooom.jelastic.activemqproducer;



import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

@Slf4j
@SpringBootApplication
public class ActivemqProducerApplication {

	@Value("spring.activemq.broker-url")
	private String JMS_BROKER_URI;

	private static final String ENV_BROKER_IP_KEY = "broker.uri";
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ActivemqProducerApplication.class, args);

	}


	@PostConstruct
	public void logJmsParams(){
		log.info("JMS Broker uri from properties: {}", JMS_BROKER_URI);

		String brokerIpEnv = System.getenv("JAVA_HOME");

		if(null!= brokerIpEnv){
			log.info("JMS Broker uri from env: {}", brokerIpEnv);
			log.info("JMS Broker uri env used...");

		}


	}
	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}


	@Bean
	public ConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(JMS_BROKER_URI);
		return connectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setMessageConverter(jacksonJmsMessageConverter());
		template.setConnectionFactory(connectionFactory());
		return template;
	}
}
