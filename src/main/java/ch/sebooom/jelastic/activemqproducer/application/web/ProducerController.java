package ch.sebooom.jelastic.activemqproducer.application.web;


import ch.sebooom.jelastic.activemqproducer.TestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;

@EnableJms
@RestController
@RequestMapping("/send")
public class ProducerController {

    @Autowired
    JmsTemplate jmsTemplate;

    private final static String QUEUE_TEST_NAME  = "q.test";

    @PostMapping
    public void sendTestMessage(@RequestBody SendTestMessageCommandDto sendTestMessageCommandDto) throws JMSException {

        jmsTemplate.convertAndSend(QUEUE_TEST_NAME,new TestMessage(sendTestMessageCommandDto.getMessage()));
    }
}
