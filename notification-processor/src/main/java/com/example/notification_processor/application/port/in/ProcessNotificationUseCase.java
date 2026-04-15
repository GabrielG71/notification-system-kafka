package com.example.notification_processor.application.port.in;

import com.example.notification_processor.domain.Notification;

public interface ProcessNotificationUseCase {

    void process(Notification notification);
}
