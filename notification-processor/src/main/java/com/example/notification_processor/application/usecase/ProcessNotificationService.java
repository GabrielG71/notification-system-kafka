package com.example.notification_processor.application.usecase;

import com.example.notification_processor.application.port.in.ProcessNotificationUseCase;
import com.example.notification_processor.application.port.out.SaveNotificationPort;
import com.example.notification_processor.domain.Notification;
import org.springframework.stereotype.Service;

@Service
public class ProcessNotificationService implements ProcessNotificationUseCase {

    private final SaveNotificationPort saveNotificationPort;

    public ProcessNotificationService(SaveNotificationPort saveNotificationPort) {
        this.saveNotificationPort = saveNotificationPort;
    }

    @Override
    public void process(Notification notification) {
        Notification processed = notification.markAsProcessed();
        saveNotificationPort.save(processed);
    }
}
