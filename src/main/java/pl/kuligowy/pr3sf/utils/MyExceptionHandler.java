package pl.kuligowy.pr3sf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class MyExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler({AmqpConnectException.class})
    public void handleAMQPException(AmqpConnectException ex){
        logger.info("Exception: "+ex.getMessage());
    }

    protected void handleExpcetion(){

    }

}
