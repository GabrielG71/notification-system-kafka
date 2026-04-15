package com.example.notification_api.application.usecase;

import com.example.notification_api.application.port.out.NotificationPublisher;
import com.example.notification_api.domain.Notification;
import org.springframework.stereotype.Service;

@Service
public class SendNotificationUseCase {
    private final NotificationPublisher notificationPublisher;

    public SendNotificationUseCase(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    public void execute(SendNotificationCommand command) {
        Notification notification = Notification.create(
            command.recipient(),
            command.message(),
            command.type()
        );

        notificationPublisher.publish(notification);
    }
    
}