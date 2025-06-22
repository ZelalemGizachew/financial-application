package com.zelalem.transaction.service;

import com.zelalem.transaction.model.TransactionHistory;
import com.zelalem.transaction.service.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MessageProducer messageProducer;

    @Async
    public void sendNotification(TransactionHistory transactionHistory) {
        messageProducer.sendMessage("events.transaction", transactionHistory.toString());
    }
}
