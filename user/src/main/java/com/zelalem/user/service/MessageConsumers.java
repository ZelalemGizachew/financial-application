package com.zelalem.user.service;

import com.zelalem.user.config.mq.RabbitMQConsumerConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumers {

    @RabbitListener(queues = RabbitMQConsumerConfig.USER_QUEUE)
    public void handleUserMessages(String message) {
        System.out.println("User Queue Received: " + message);
    }
}
