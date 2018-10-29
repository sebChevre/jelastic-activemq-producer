package ch.sebooom.jelastic.activemqproducer.application.service;

import ch.sebooom.jelastic.activemqproducer.application.web.command.SendTestMessageCommandDto;

public interface TestQueueService {

    void sendMessageToTestQueue(SendTestMessageCommandDto sendTestMessageCommandDto);

    void startProcessMessageToTestQueue();

    void stopProcessMessageToTestQueue();
}
