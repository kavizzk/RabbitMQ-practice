# RabbitMQ Practice – Spring Boot Microservices

A hands-on microservices project demonstrating **asynchronous communication using RabbitMQ and Spring Boot**.

The project follows a shipment-management workflow in which the Shipment Service publishes events to RabbitMQ. Multiple independent services consume those events to perform notification, audit, and analytics operations.

## Project Overview

This project contains four Spring Boot microservices:

### 1. Shipment Service – Event Producer

- Creates shipment events.
- Publishes shipment events to RabbitMQ.
- Publishes status updates such as PAID, PACKED, SHIPPED, DELIVERED, CANCELLED, and FAILED.
- Uses `RabbitTemplate` to publish messages.
- Uses Spring WebFlux and Project Reactor.

**Port:** `8081`

### 2. Notification Service – Event Consumer

- Listens for shipment events from RabbitMQ.
- Processes notification-related events.
- Contains email and SMS service components.
- Uses `@RabbitListener` for message consumption.

**Port:** `8082`

### 3. Audit Service – Event Consumer

- Consumes shipment and delivery events.
- Maintains shipment audit information.
- Uses Spring Data JPA and MySQL.
- Processes events asynchronously.

**Port:** `8083`

### 4. Analytics Service – Event Consumer

- Consumes shipment and delivery events.
- Tracks shipment status counts.
- Maintains analytics for created, paid, packed, shipped, delivered, cancelled, and failed shipments.
- Exposes an analytics endpoint.

**Port:** `8084`

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.1 | Backend framework |
| Spring WebFlux | Reactive REST APIs |
| Spring AMQP | RabbitMQ integration |
| RabbitMQ | Message broker |
| RabbitTemplate | Publishing messages |
| @RabbitListener | Consuming messages |
| Jackson | JSON message conversion |
| Spring Data JPA | Database operations |
| MySQL | Relational database |
| Maven | Build management |
| Project Reactor | Mono and reactive processing |

## Architecture

```text
                 Client / Postman
                        |
                        v
               Shipment Service
                  Port 8081
                        |
                        |
                  RabbitTemplate
                        |
                        v
                RabbitMQ Broker
                 shipment.exchange
                        |
          +-------------+-------------+
          |             |             |
          v             v             v
   Notification     Audit Service   Analytics
     Service          Port 8083     Service
    Port 8082                        Port 8084
          |             |             |
          v             v             v
    Notification     MySQL       Analytics Data
     Processing     Audit DB       Processing
```

## RabbitMQ Exchange and Queues

The project uses a **Topic Exchange** named:

```text
shipment.exchange
```

Queues:

```text
shipment.audit.queue
shipment.analytics.queue
```

The notification service also listens to shipment events using its configured RabbitMQ listener.

Routing key examples:

```text
shipment.created
shipment.paid
shipment.packed
shipment.shipped
shipment.delivered
shipment.cancelled
shipment.failed

delivery.assigned
delivery.failed
```

The audit and analytics services bind their queues using topic patterns:

```text
shipment.*
delivery.*
```

This allows multiple consumers to receive relevant events independently.

## Project Structure

```text
rabbitmq/
│
├── shipment-service/
│   ├── src/main/java/com/example/
│   │   ├── config/
│   │   ├── constants/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── event/
│   │   ├── service/
│   │   └── stream/
│   ├── src/main/resources/
│   └── pom.xml
│
├── notification-service/
│   ├── src/main/java/com/example/
│   │   ├── config/
│   │   ├── constants/
│   │   ├── consumer/
│   │   ├── event/
│   │   └── service/
│   ├── src/main/resources/
│   └── pom.xml
│
├── audit-service/
│   ├── src/main/java/com/example/
│   │   ├── config/
│   │   ├── consumer/
│   │   ├── entity/
│   │   ├── event/
│   │   └── service/
│   ├── src/main/resources/
│   └── pom.xml
│
└── analytics-service/
    ├── src/main/java/com/example/
    │   ├── config/
    │   ├── consumer/
    │   ├── controller/
    │   ├── event/
    │   └── service/
    ├── src/main/resources/
    └── pom.xml
```

## Prerequisites

Install the following:

- JDK 21
- Apache Maven
- MySQL Server
- RabbitMQ Server
- Postman
- STS / Eclipse / IntelliJ IDEA / VS Code

Verify Java and Maven:

```bash
java -version
mvn -version
```

## RabbitMQ Setup

Start RabbitMQ and make sure the broker is running.

If using Docker, you can start RabbitMQ with the management interface:

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:management
```

RabbitMQ connection details:

```text
Host: localhost
AMQP Port: 5672
Management UI: http://localhost:15672
Username: guest
Password: guest
```

Open the management dashboard:

[http://localhost:15672](http://localhost:15672)

## MySQL Database Setup

Create the databases used by the project:

```sql
CREATE DATABASE fleetflowdb;
CREATE DATABASE fleetflow_audit_db;
```

The project currently uses the following database configurations:

| Service | Database |
|---|---|
| Shipment Service | fleetflowdb |
| Audit Service | fleetflow_audit_db |

Configure database credentials in each service's `application.properties` file before running the application.

**Security:** Do not upload real database passwords to GitHub. Use environment variables or a local, untracked configuration file.

## Application Configuration

The following ports and RabbitMQ settings are configured in the project:

| Service | Port | RabbitMQ Port |
|---|---:|---:|
| Shipment Service | 8081 | 5672 |
| Notification Service | 8082 | 5672 |
| Audit Service | 8083 | 5672 |
| Analytics Service | 8084 | 5672 |

RabbitMQ connection settings:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

## How to Run the Project

Start MySQL and RabbitMQ before starting the microservices.

Open four separate terminals from the project root directory.

### Step 1: Start Shipment Service

```bash
cd shipment-service
mvn clean install
mvn spring-boot:run
```

### Step 2: Start Notification Service

```bash
cd notification-service
mvn clean install
mvn spring-boot:run
```

### Step 3: Start Audit Service

```bash
cd audit-service
mvn clean install
mvn spring-boot:run
```

### Step 4: Start Analytics Service

```bash
cd analytics-service
mvn clean install
mvn spring-boot:run
```

All four services should now be running.

## API Endpoints

### Shipment Service

Base URL:

```text
http://localhost:8081
```

| Method | Endpoint | Description |
|---|---|---|
| POST | `/shipments` | Create shipment |
| PUT | `/shipments/{shipmentId}/pay` | Update shipment to paid |
| PUT | `/shipments/{shipmentId}/pack` | Update shipment to packed |
| PUT | `/shipments/{shipmentId}/ship` | Update shipment to shipped |
| PUT | `/shipments/{shipmentId}/deliver` | Mark shipment as delivered |
| PUT | `/shipments/{shipmentId}/cancel` | Cancel shipment |
| PUT | `/shipments/{shipmentId}/fail` | Mark shipment as failed |
| PUT | `/shipments/{shipmentId}/assign-delivery` | Assign delivery |
| PUT | `/shipments/{shipmentId}/delivery-failed` | Mark delivery as failed |

### Create Shipment

Request:

```http
POST http://localhost:8081/shipments
Content-Type: application/json
```

Example JSON:

```json
{
  "customerId": 101,
  "customerName": "Kavitha"
}
```

The Shipment Service generates a shipment ID, creates a shipment event, and publishes it to RabbitMQ.

### Update Shipment Status

Example:

```http
PUT http://localhost:8081/shipments/SHP-123/pay
```

Other examples:

```http
PUT http://localhost:8081/shipments/SHP-123/pack

PUT http://localhost:8081/shipments/SHP-123/ship

PUT http://localhost:8081/shipments/SHP-123/deliver

PUT http://localhost:8081/shipments/SHP-123/cancel
```

Replace `SHP-123` with the shipment ID returned by your create-shipment request.

### Analytics Service

Retrieve analytics:

```http
GET http://localhost:8084/analytics
```

The endpoint returns counts such as:

```json
{
  "totalShipments": 1,
  "created": 1,
  "paid": 1,
  "packed": 1,
  "shipped": 0,
  "delivered": 0,
  "cancelled": 0,
  "failed": 0,
  "deliveryAssigned": 0,
  "deliveryFailed": 0
}
```

The values above are illustrative; actual counts depend on the events processed during runtime.

## Important RabbitMQ Concepts Practiced

| Concept | Usage |
|---|---|
| Producer | Shipment Service |
| Consumer | Notification, Audit, Analytics Services |
| Exchange | Routes published messages |
| Topic Exchange | Routes using patterns |
| Queue | Stores messages for consumers |
| Routing Key | Identifies event type |
| Binding | Connects queues to exchanges |
| RabbitTemplate | Sends messages |
| @RabbitListener | Receives messages |
| MessageConverter | Converts Java objects to JSON |
| Asynchronous Communication | Services process events independently |

## Learning Objectives

Through this project, I practiced:

- Understanding asynchronous communication in microservices.
- Implementing event-driven architecture.
- Creating RabbitMQ producers and consumers.
- Configuring topic exchanges, queues, and bindings.
- Publishing JSON messages using RabbitTemplate.
- Consuming messages using @RabbitListener.
- Implementing multiple consumers for the same events.
- Integrating RabbitMQ with Spring Boot.
- Working with Spring WebFlux and Mono.
- Understanding event-based shipment status updates.

## Author

**Kavitha Selvam**

GitHub: [@kavizzk](https://github.com/kavizzk)

---

*This project is developed for learning and practicing RabbitMQ, Spring Boot, and event-driven microservices architecture.*
