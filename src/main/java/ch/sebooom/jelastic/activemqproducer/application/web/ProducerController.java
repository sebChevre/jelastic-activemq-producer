package ch.sebooom.jelastic.activemqproducer.application.web;


import ch.sebooom.jelastic.activemqproducer.application.service.TestQueueService;
import ch.sebooom.jelastic.activemqproducer.application.web.command.SendTestMessageCommandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity sendTestMessage(@RequestBody SendTestMessageCommandDto sendTestMessageCommandDto) throws JMSException {
        testQueueService.sendMessageToTestQueue(sendTestMessageCommandDto);
        return ResponseEntity.ok("Message sending to queue test.q");
    }

    @PostMapping(value = "/test.q/process")
    public ResponseEntity startTestMessageProcess(){

        testQueueService.startProcessMessageToTestQueue();
        return ResponseEntity.ok("Process started...");
    }

    @DeleteMapping(value = "/test.q/process")
    public ResponseEntity stopTestMessageProcess(){

        testQueueService.stopProcessMessageToTestQueue();
        return ResponseEntity.ok("Process stoped...");
    }
}
