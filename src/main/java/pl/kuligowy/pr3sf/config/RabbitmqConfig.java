package pl.kuligowy.pr3sf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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


    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory ccf = new CachingConnectionFactory("localhost");
        ccf.setUsername("guest");
        ccf.setPassword("guest");
        logger.info("Creating connection factory with: " + username + "@" + host + ":" + port);

        return ccf;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        logger.info("Creating amqpAdmin...");
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
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

}
