package ch.sebooom.jelastic.activemqproducer.application.process;


import ch.sebooom.jelastic.activemqproducer.infrastructure.jms.test.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class JmsProcessProducerTask implements Runnable {

    private AtomicBoolean enabled = new AtomicBoolean(true);

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsProcessProducerTask(JmsTemplate jmsTemplate ){
        this.jmsTemplate = jmsTemplate;
    }


    public void enableJmsMessageProcess(){
        log.info("JMS Messag eprocess enabled");
        this.enabled = new AtomicBoolean(true);
    }

    public void disableJmsMessageProcess(){
        log.info("JMS Message process disabled");
        this.enabled = new AtomicBoolean(false);
    }

    @Override
    public void run() {

        if(enabled.get()){
            Thread thread = Thread.currentThread();

            log.info("generate message for process: {}", thread.getName());
            try{
                jmsTemplate.convertAndSend(new TestMessage("JMS Process message. Thread: {}" + thread.getName()));
            }catch(Exception ex){
                log.error("Error during sendin message for process. Process stopped now. {}",ex.getMessage());
                this.enabled = new AtomicBoolean(false);
            }

        }else{
            log.trace("run methode calling but jms message sending disabled");
        }

    }
}
