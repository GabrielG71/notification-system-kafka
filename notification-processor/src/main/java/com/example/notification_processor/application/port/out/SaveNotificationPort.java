package com.example.notification_processor.application.port.out;

import com.example.notification_processor.domain.Notification;

public interface SaveNotificationPort {

    void save(Notification notification);
}
