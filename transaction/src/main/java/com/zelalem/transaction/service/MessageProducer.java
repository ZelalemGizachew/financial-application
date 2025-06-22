package com.zelalem.transaction.service;

import com.zelalem.transaction.config.mq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String routingKey, String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, routingKey, message);
        System.out.println("[> Sent: " + message + " with key: " + routingKey);
    }
}
