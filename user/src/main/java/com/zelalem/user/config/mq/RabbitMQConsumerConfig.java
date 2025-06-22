package com.zelalem.user.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {
    public static final String USER_QUEUE = "queue.user";

    @Bean
    Queue queueUser() {
        return new Queue(USER_QUEUE);
    }

    @Bean
    Binding userBinding(Queue queueUser, TopicExchange exchange) {
        return BindingBuilder.bind(queueUser).to(exchange).with("user.*");
    }
}
