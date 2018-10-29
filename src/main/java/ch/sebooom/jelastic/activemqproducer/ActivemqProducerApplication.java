package ch.sebooom.jelastic.activemqproducer;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;


import javax.annotation.PostConstruct;

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
			log.info("key: {}",o);
		}
	}


}
