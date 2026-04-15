package com.example.notification_processor.infrastructure.messaging;

import com.example.notification_processor.application.port.in.ProcessNotificationUseCase;
import com.example.notification_processor.domain.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    private final ProcessNotificationUseCase processNotificationUseCase;
    private final ObjectMapper objectMapper;

    public NotificationConsumer(ProcessNotificationUseCase processNotificationUseCase,
                                ObjectMapper objectMapper) {
        this.processNotificationUseCase = processNotificationUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "notification.created", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String payload) {
        try {
            NotificationMessage message = objectMapper.readValue(payload, NotificationMessage.class);
            Notification notification = Notification.reconstruct(
                    message.id(),
                    message.recipient(),
                    message.message(),
                    message.type(),
                    message.status(),
                    message.createdAt()
            );
            processNotificationUseCase.process(notification);
            log.info("Notification {} processed successfully", message.id());
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize notification payload: {}", payload, e);
            throw new RuntimeException("Failed to deserialize notification payload", e);
        }
    }
}
