package ch.sebooom.jelastic.activemqproducer;



import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Slf4j
@SpringBootApplication
public class ActivemqProducerApplication {



	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ActivemqProducerApplication.class, args);

	}


	@PostConstruct
	public void logJmsParams(){

		log.info("Application postConstruct");

		for( Object o : System.getenv().entrySet()  ){
			log.info("key" + o );
		}
	}


}
