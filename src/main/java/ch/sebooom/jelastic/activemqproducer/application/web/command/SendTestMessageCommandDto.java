package ch.sebooom.jelastic.activemqproducer.application.web.command;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SendTestMessageCommandDto {

    private String message;

    public SendTestMessageCommandDto(String message) {
        this.message = message;
    }

    public SendTestMessageCommandDto() {
    }
}
