package com.example.notification_processor.infrastructure.persistence;

import com.example.notification_processor.application.port.out.SaveNotificationPort;
import com.example.notification_processor.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationPersistenceAdapter implements SaveNotificationPort {

    private final NotificationJpaRepository repository;

    public NotificationPersistenceAdapter(NotificationJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Notification notification) {
        NotificationEntity entity = new NotificationEntity(
                notification.getId(),
                notification.getRecipient(),
                notification.getMessage(),
                notification.getType(),
                notification.getStatus(),
                notification.getCreatedAt()
        );
        repository.save(entity);
    }
}
