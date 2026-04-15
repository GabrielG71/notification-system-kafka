package com.example.notification_api.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

    private final UUID id;
    private final String recipient;
    private final String message;
    private final NotificationType type;
    private final NotificationStatus status;
    private final LocalDateTime createdAt;

    private Notification(UUID id, String recipient, String message,
                         NotificationType type, NotificationStatus status,
                         LocalDateTime createdAt) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Notification create(String recipient, String message, NotificationType type) {
        return new Notification(
            UUID.randomUUID(),
            recipient,
            message,
            type,
            NotificationStatus.PENDING,
            LocalDateTime.now()
        );
    }

    public UUID getId() { return id; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public NotificationType getType() { return type; }
    public NotificationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}