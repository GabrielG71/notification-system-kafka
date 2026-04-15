# notification-api

REST API responsible for receiving notification requests and publishing them as events to Apache Kafka. Built with Spring Boot 3 and organized following hexagonal architecture principles.

## Architecture

The service is structured into three main layers that enforce a strict dependency rule: outer layers depend on inner layers, never the reverse.

```
notification-api/
  src/main/java/com/example/notification_api/
    domain/
      Notification.java
      NotificationStatus.java
      NotificationType.java
    application/
      port/
        out/ NotificationPublisher.java
      usecase/
        SendNotificationCommand.java
        SendNotificationUseCase.java
    infrastructure/
      messaging/
        KafkaNotificationPublisher.java
      web/
        dto/ NotificationRequest.java
        NotificationController.java
  src/main/resources/
    application.yml
```

**Domain** holds the Notification aggregate root and its enums with no framework dependencies. **Application** defines the use case and the outbound port as an interface. **Infrastructure** contains the HTTP controller and the Kafka publisher that implement the adapters.

## Flow

```
POST /notifications
        |
        v
NotificationController      (infrastructure/web)
        |  maps request body to SendNotificationCommand
        v
SendNotificationUseCase     (application/usecase)
        |  creates Notification domain entity with status PENDING
        |  delegates to NotificationPublisher port
        v
KafkaNotificationPublisher  (infrastructure/messaging)
        |  serializes Notification to JSON
        v
Kafka topic: notification.created
```

## Prerequisites

- Java 21
- Apache Kafka running on `localhost:9092`

## Running locally

Start the required infrastructure:

```bash
docker compose up -d
```

Run the application:

```bash
./mvnw spring-boot:run
```

The service starts on port `8080`.

## Endpoint

### Send a notification

```
POST /notifications
```

Request body:

```json
{
  "recipient": "user@example.com",
  "message": "Your order has been confirmed.",
  "type": "EMAIL"
}
```

Accepted values for `type`: `EMAIL`, `SMS`, `PUSH`.

Response: `202 Accepted` with no body.

## Configuration

All environment-specific settings are in `src/main/resources/application.yml`.

| Property                           | Default value    |
|------------------------------------|------------------|
| `server.port`                      | `8080`           |
| `spring.kafka.bootstrap-servers`   | `localhost:9092` |

## Building

```bash
./mvnw clean package -DskipTests
```

## Tech stack

- Spring Boot 3.2
- Spring Kafka
- Spring Web
- Spring Validation
- Java 21
