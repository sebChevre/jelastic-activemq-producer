package ch.sebooom.jelastic.activemqproducer.application.service.impl;

import ch.sebooom.jelastic.activemqproducer.application.process.JmsProcessProducerTask;
import ch.sebooom.jelastic.activemqproducer.infrastructure.jms.test.TestMessage;
import ch.sebooom.jelastic.activemqproducer.application.service.TestQueueService;
import ch.sebooom.jelastic.activemqproducer.application.web.command.SendTestMessageCommandDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestQueueServiceImpl implements TestQueueService {

    @Autowired
    private ThreadPoolTaskScheduler executor;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsProcessProducerTask jmsProcessProducerTask;



    public TestQueueServiceImpl(JmsTemplate jmsTemplate, ThreadPoolTaskScheduler executor,JmsProcessProducerTask processProducerTask){
        this.jmsTemplate = jmsTemplate;
        this.executor = executor;
        this.jmsProcessProducerTask = processProducerTask;
    }

    @Override
    public void sendMessageToTestQueue(SendTestMessageCommandDto sendTestMessageCommandDto){
        log.info("Sending message to test queue....");
        jmsTemplate.convertAndSend(new TestMessage(sendTestMessageCommandDto.getMessage()));
    }

    @Override
    public void startProcessMessageToTestQueue(){
        log.info("Starting message queue producer process....");
        jmsProcessProducerTask.enableJmsMessageProcess();
        executor.scheduleAtFixedRate(jmsProcessProducerTask,1234);

    }

    @Override
    public void stopProcessMessageToTestQueue(){
        log.info("Stoping message queue producer process....");
        jmsProcessProducerTask.disableJmsMessageProcess();

    }




}
