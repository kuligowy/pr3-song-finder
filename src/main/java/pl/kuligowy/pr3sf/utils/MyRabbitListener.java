package pl.kuligowy.pr3sf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.kuligowy.pr3sf.domain.SongEntry;

@Component
public class MyRabbitListener {

     private static final Logger logger = LoggerFactory.getLogger(RabbitListener.class);


    @RabbitListener(queues = "${queue.name}")
    public void processMessage(@Payload SongEntry data){
        logger.info(String.format("Object %s", data));

    }
}
