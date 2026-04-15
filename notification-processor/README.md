# notification-processor

Microservice responsible for consuming notification events from Apache Kafka, updating their status to processed, and persisting the result to a PostgreSQL database. Built with Spring Boot 3 and organized following hexagonal architecture principles.

## Architecture

The service is structured into three main layers that enforce a strict dependency rule: outer layers depend on inner layers, never the reverse.

```
notification-processor/
  src/main/java/com/example/notification_processor/
    domain/
      Notification.java
      NotificationStatus.java
      NotificationType.java
    application/
      port/
        in/  ProcessNotificationUseCase.java
        out/ SaveNotificationPort.java
      usecase/
        ProcessNotificationService.java
    infrastructure/
      messaging/
        NotificationConsumer.java
        NotificationMessage.java
      persistence/
        NotificationEntity.java
        NotificationJpaRepository.java
        NotificationPersistenceAdapter.java
  src/main/resources/
    application.yaml
    db/migration/
      V1__create_notifications_table.sql
```

**Domain** holds the core business entity and enums with no framework dependencies. **Application** defines the use case and its required ports as interfaces. **Infrastructure** contains all framework-specific adapters that implement those ports.

## Flow

```
Kafka topic: notification.created
        |
        v
NotificationConsumer        (infrastructure/messaging)
        |  deserializes JSON to NotificationMessage record
        |  reconstructs Notification domain entity
        v
ProcessNotificationUseCase  (application/port/in)
        |
        v
ProcessNotificationService  (application/usecase)
        |  calls notification.markAsProcessed()
        |  delegates to SaveNotificationPort
        v
NotificationPersistenceAdapter (infrastructure/persistence)
        |  maps domain entity to JPA entity
        v
PostgreSQL via JpaRepository
```

## Prerequisites

- Java 21
- Apache Kafka running on `localhost:9092`
- PostgreSQL running on `localhost:5432` with a database named `notifications`
  - User: `admin`
  - Password: `admin`

## Running locally

Start the required infrastructure:

```bash
docker compose up -d
```

Run the application:

```bash
./mvnw spring-boot:run
```

The service starts on port `8081`. There are no HTTP endpoints; all activity is driven by Kafka messages.

## Database migration

Flyway manages schema versioning automatically on startup. The initial migration creates the `notifications` table:

| Column     | Type         | Constraints   |
|------------|--------------|---------------|
| id         | UUID         | PRIMARY KEY   |
| recipient  | VARCHAR(255) | NOT NULL      |
| message    | TEXT         | NOT NULL      |
| type       | VARCHAR(50)  | NOT NULL      |
| status     | VARCHAR(50)  | NOT NULL      |
| created_at | TIMESTAMP    | NOT NULL      |

## Configuration

All environment-specific settings are in `src/main/resources/application.yaml`.

| Property                                    | Default value              |
|---------------------------------------------|----------------------------|
| `server.port`                               | `8081`                     |
| `spring.datasource.url`                     | `jdbc:postgresql://localhost:5432/notifications` |
| `spring.datasource.username`                | `admin`                    |
| `spring.datasource.password`                | `admin`                    |
| `spring.kafka.bootstrap-servers`            | `localhost:9092`           |
| `spring.kafka.consumer.group-id`            | `notification-processor`   |

## Kafka topic

The service subscribes to the `notification.created` topic. Messages are expected as JSON with the following structure:

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "recipient": "user@example.com",
  "message": "Your order has been confirmed.",
  "type": "EMAIL",
  "status": "PENDING",
  "createdAt": "2026-04-15T10:00:00"
}
```

## Building

```bash
./mvnw clean package -DskipTests
```

## Tech stack

- Spring Boot 3.5
- Spring Kafka
- Spring Data JPA
- PostgreSQL
- Flyway
- Java 21
