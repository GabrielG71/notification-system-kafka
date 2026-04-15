package com.example.notification_api.infrastructure.web.dto;

import com.example.notification_api.domain.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequest(
    @NotBlank(message = "recipient is required")
    String recipient,

    @NotBlank(message = "message is required")
    String message,

    @NotNull(message = "type is required")
    NotificationType type
) {}