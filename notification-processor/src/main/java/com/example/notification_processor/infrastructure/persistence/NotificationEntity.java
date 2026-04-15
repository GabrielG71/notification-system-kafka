package com.example.notification_processor.infrastructure.persistence;

import com.example.notification_processor.domain.NotificationStatus;
import com.example.notification_processor.domain.NotificationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected NotificationEntity() {}

    public NotificationEntity(UUID id, String recipient, String message,
                               NotificationType type, NotificationStatus status,
                               LocalDateTime createdAt) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public NotificationType getType() { return type; }
    public NotificationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
