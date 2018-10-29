package ch.sebooom.jelastic.activemqproducer.application.web;


import ch.sebooom.jelastic.activemqproducer.application.service.TestQueueService;
import ch.sebooom.jelastic.activemqproducer.application.web.command.SendTestMessageCommandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;

@EnableJms
@RestController
@RequestMapping("/send")
public class ProducerController {

    private final TestQueueService testQueueService;

    @Autowired
    public ProducerController(TestQueueService testQueueService){
        this.testQueueService = testQueueService;
    }


    @PostMapping(value = "/test.q")
    public void sendTestMessage(@RequestBody SendTestMessageCommandDto sendTestMessageCommandDto) throws JMSException {
        testQueueService.sendMessageToTestQueue(sendTestMessageCommandDto);
    }

    @PostMapping(value = "/test.q/process")
    public void startTestMessageProcess(){
        testQueueService.startProcessMessageToTestQueue();
    }

    @DeleteMapping(value = "/test.q/process")
    public void stopTestMessageProcess(){
        testQueueService.stopProcessMessageToTestQueue();
    }
}
