package com.example.notification_api.infrastructure.web;

import com.example.notification_api.application.usecase.SendNotificationCommand;
import com.example.notification_api.application.usecase.SendNotificationUseCase;
import com.example.notification_api.infrastructure.web.dto.NotificationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final SendNotificationUseCase sendNotificationUseCase;

    public NotificationController(SendNotificationUseCase sendNotificationUseCase) {
        this.sendNotificationUseCase = sendNotificationUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@RequestBody @Valid NotificationRequest request) {
        SendNotificationCommand command = new SendNotificationCommand(
            request.recipient(),
            request.message(),
            request.type()
        );

        sendNotificationUseCase.execute(command);
    }

}