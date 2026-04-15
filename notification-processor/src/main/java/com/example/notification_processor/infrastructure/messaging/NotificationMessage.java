package com.example.notification_processor.infrastructure.messaging;

import com.example.notification_processor.domain.NotificationStatus;
import com.example.notification_processor.domain.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationMessage(
        UUID id,
        String recipient,
        String message,
        NotificationType type,
        NotificationStatus status,
        LocalDateTime createdAt
) {}
