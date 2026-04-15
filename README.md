# notification-system-kafka

Event-driven notification system built with Apache Kafka and Spring Boot. The system is composed of two independent microservices that communicate asynchronously through a Kafka topic.

## System overview

```
Client
  |
  | POST /notifications
  v
notification-api          (port 8080)
  |  creates Notification with status PENDING
  |  serializes to JSON
  |  publishes to Kafka
  v
Kafka topic: notification.created
  |
  v
notification-processor    (port 8081)
  |  deserializes JSON to domain entity
  |  sets status to PROCESSED
  |  persists to PostgreSQL
  v
PostgreSQL database: notifications
```

## Services

| Service | Responsibility | Port |
|---|---|---|
| [notification-api](notification-api/) | Receives HTTP requests and publishes notification events to Kafka | 8080 |
| [notification-processor](notification-processor/) | Consumes notification events from Kafka and persists them to PostgreSQL | 8081 |

Each service has its own README with detailed documentation on architecture, configuration and how to run it.

## Architecture

Both services follow hexagonal architecture (ports and adapters), keeping the domain and application core free of framework dependencies. Infrastructure adapters plug in through well-defined port interfaces.

## Prerequisites

- Java 21
- Docker and Docker Compose

## Running locally

Start Kafka and PostgreSQL:

```bash
docker compose up -d
```

Start each service in a separate terminal:

```bash
# terminal 1
cd notification-api
./mvnw spring-boot:run

# terminal 2
cd notification-processor
./mvnw spring-boot:run
```

## Sending a notification

```bash
curl -X POST http://localhost:8080/notifications \
  -H "Content-Type: application/json" \
  -d '{
    "recipient": "user@example.com",
    "message": "Your order has been confirmed.",
    "type": "EMAIL"
  }'
```

The API responds with `202 Accepted`. The processor picks up the event from Kafka, marks it as `PROCESSED`, and saves it to the `notifications` table in PostgreSQL.

## Tech stack

- Java 21
- Spring Boot 3
- Apache Kafka
- PostgreSQL
- Flyway
- Docker Compose
