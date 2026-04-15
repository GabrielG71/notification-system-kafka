package com.example.notification_api.infrastructure.messaging;

import com.example.notification_api.application.port.out.NotificationPublisher;
import com.example.notification_api.domain.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaNotificationPublisher implements NotificationPublisher {

    private static final String TOPIC = "notification.created";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaNotificationPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public void publish(Notification notification) {
        try {
            String payload = objectMapper.writeValueAsString(notification);
            kafkaTemplate.send(TOPIC, notification.getId().toString(), payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize notification: " + notification.getId(), e);
        }
    }
}