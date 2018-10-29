package ch.sebooom.jelastic.activemqproducer.infrastructure.jms.test;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;


@ToString
public class TestMessage {
    private String message;
    private Date dateEnvoi;

    public TestMessage(String message) {
        this.message = message;
        this.dateEnvoi = new Date();
    }

    public TestMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getDateEnvoi() {
        return dateEnvoi.toString();
    }
}
