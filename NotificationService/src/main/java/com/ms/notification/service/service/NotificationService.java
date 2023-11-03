package com.ms.notification.service.service;

import com.ms.notification.service.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @KafkaListener(topics = "notification-order-topic", groupId = "group-1")
    public void handleNotification(String orderPlacedEvent){

        // send out an email notification

        log.info("Received Notification for Order - {}", orderPlacedEvent);
    }
}
