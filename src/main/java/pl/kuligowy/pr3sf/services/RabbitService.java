package pl.kuligowy.pr3sf.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kuligowy.pr3sf.domain.SongEntry;

@Service
public class RabbitService {

    @Value("${queue.name}")
    private String queueName;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    public void sendMessage(SongEntry songEntry){
        rabbitTemplate.convertAndSend(queueName,songEntry);
    }


}
