package pl.kuligowy.pr3sf.config;

import org.slf4j.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;


@Configuration
public class RabbitmqConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Value("${queue.name}")
    private String queueName;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port:5672}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


//    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory ccf = new CachingConnectionFactory("localhost");
        ccf.setUsername("guest");
        ccf.setPassword("guest");
        logger.info("Creating connection factory with: " + username + "@" + host + ":" + port);

        return ccf;
    }

//    @Bean
    public RabbitAdmin amqpAdmin() {
        logger.info("Creating amqpAdmin...");
        return new RabbitAdmin(connectionFactory());
    }

//    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange("spring-boot-exchange");
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(queueName);
//    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        logger.info("Creating rabbitTemplate...");
        return new RabbitTemplate(connectionFactory());
    }


//    @Bean
//    public SimpleMessageListenerContainer container(){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setQueues(queue());
//        container.setRabbitAdmin(amqpAdmin());
//        container.setConnectionFactory(connectionFactory());
//        container.setErrorHandler(t-> logger.info("Handling error..."+t.getMessage()));
//        return container;
//    }
}
