package com.example.notification_api.application.port.out;

import com.example.notification_api.domain.Notification;

public interface NotificationPublisher {
    void publish(Notification notification);
}