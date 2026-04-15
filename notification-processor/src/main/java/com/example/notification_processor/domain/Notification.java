package com.example.notification_processor.domain;

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

    public static Notification reconstruct(UUID id, String recipient, String message,
                                           NotificationType type, NotificationStatus status,
                                           LocalDateTime createdAt) {
        return new Notification(id, recipient, message, type, status, createdAt);
    }

    public Notification markAsProcessed() {
        return new Notification(this.id, this.recipient, this.message,
                this.type, NotificationStatus.PROCESSED, this.createdAt);
    }

    public UUID getId() { return id; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public NotificationType getType() { return type; }
    public NotificationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
