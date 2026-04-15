package com.example.notification_api.application.usecase;

import com.example.notification_api.domain.NotificationType;

public record SendNotificationCommand(
    String recipient,
    String message,
    NotificationType type
) {}