package com.zelalem.notification.service;

import com.zelalem.notification.config.mq.RabbitMQConsumerConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumers {
    @RabbitListener(queues = RabbitMQConsumerConfig.EVENTS_QUEUE)
    public void handleUserMessages(String message) {
        System.out.println(">] Notification Received: " + message);
    }
}
