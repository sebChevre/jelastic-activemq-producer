package ch.sebooom.jelastic.activemqproducer.application.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ProducerControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { UncategorizedJmsException.class})
    protected ResponseEntity<Object> handleJmsException(
            RuntimeException ex, WebRequest request) {

        log.error(ex.getMessage());

        String bodyOfResponse = "JMS Broker unavailable";

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
