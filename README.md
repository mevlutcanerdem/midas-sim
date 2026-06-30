# 🚀 Midas-Sim

> A simulation of a high-load portfolio trading backend built with modern event-driven architecture patterns.

This project demonstrates how financial systems can safely process trading operations using **Spring Boot**, **Apache Kafka**, **Transactional Outbox Pattern**, and **Idempotent Consumers**.

The primary goal is to simulate how production-grade trading platforms maintain **data consistency**, **reliability**, and **scalability** under concurrent requests.

---

# ✨ Features

- Buy asset operation
- Portfolio management
- GraphQL API
- PostgreSQL persistence
- Transactional Outbox Pattern
- Apache Kafka event publishing
- Scheduled Outbox Poller
- Idempotent Kafka Consumer
- Redis integration
- Docker Compose infrastructure

---

# 🏛️ Architecture

```
                    Client
                       │
                       ▼
              GraphQL Controller
                       │
                       ▼
               Portfolio Service
                       │
        ┌──────────────┴──────────────┐
        ▼                             ▼
 Update User Balance          Save Outbox Event
        │                             │
        └──────────────┬──────────────┘
                       ▼
              PostgreSQL Transaction
                       │
                (Transaction Commit)
                       │
                       ▼
               Scheduled Outbox Poller
                       │
                       ▼
                  Apache Kafka
                       │
                       ▼
              Trade Event Consumer
                       │
              Idempotency Check
                       │
                       ▼
          Notification / Future Services
```

---

# 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| API | Spring GraphQL |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Messaging | Apache Kafka |
| Cache | Redis |
| Containerization | Docker |
| Build Tool | Maven |

---

# 📂 Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── consumer
 ├── graphql
 └── resources
```

Each layer has a single responsibility following the **Layered Architecture** principle.

---

# 🔄 Transaction Flow

## 1. Client sends a Buy Asset mutation

↓

## 2. PortfolioService starts a database transaction

↓

## 3. User balance is updated

↓

## 4. Portfolio information is saved

↓

## 5. Outbox event is inserted into PostgreSQL

↓

## 6. Transaction commits

↓

## 7. OutboxPoller publishes event to Kafka

↓

## 8. Event status becomes SENT

↓

## 9. Kafka Consumer processes the event

↓

## 10. Idempotency check prevents duplicate processing

---

# 📌 Design Patterns

## Transactional Outbox Pattern

Instead of publishing Kafka events directly inside the business transaction, the event is first stored in the database.

This guarantees that database changes and event creation happen atomically.

---

## Polling Publisher

A scheduled background worker periodically scans pending outbox events and publishes them to Kafka.

---

## Idempotent Consumer

Kafka may redeliver messages.

Each event contains a unique Event ID.

Previously processed IDs are ignored to prevent duplicate processing.

---

# 📊 Entity Relationship

```
User
 │
 │ 1
 │
 │
 ▼
Portfolio
 ▲
 │
 │ N
 │
Asset
```

Portfolio acts as the junction table between User and Asset.

---

# 🐳 Running the Project

## Clone

```bash
git clone https://github.com/yourusername/midas-sim.git
```

## Start Infrastructure

```bash
cd infrastructure

docker compose up -d
```

This starts:

- PostgreSQL
- Kafka
- Redis

---

## Run Application

```bash
mvn spring-boot:run
```

or run

```
PortfolioTrackerApplication
```

from IntelliJ IDEA.

---

# 📡 GraphQL

Example mutation

```graphql
mutation {

  buyAsset(

    userId:1

    assetId:2

    quantity:5

  )

}
```

---

# 🎯 What This Project Demonstrates

- Layered Architecture
- Event-Driven Architecture
- ACID Transactions
- Transactional Outbox Pattern
- Kafka Producer & Consumer
- Scheduled Background Jobs
- Idempotent Consumer Design
- GraphQL API Development
- Dockerized Development Environment

---

# 🚀 Future Improvements

- JWT Authentication
- Spring Security
- Portfolio valuation using live market prices
- Distributed locking
- Kafka Retry Topics
- Dead Letter Queue (DLQ)
- Integration Tests with Testcontainers
- Prometheus & Grafana Monitoring
- Kubernetes Deployment

---

# 📷 Architecture Overview

```
                 +--------------------+
                 |    GraphQL Client  |
                 +---------+----------+
                           |
                           v
                 +--------------------+
                 | Portfolio Service  |
                 +---------+----------+
                           |
         +-----------------+------------------+
         |                                    |
         v                                    v
+--------------------+             +----------------------+
| PostgreSQL         |             | Outbox Events Table  |
+--------------------+             +----------------------+
                                             |
                                             v
                                   +----------------------+
                                   | Scheduled Poller     |
                                   +----------+-----------+
                                              |
                                              v
                                      Apache Kafka
                                              |
                                              v
                                   TradeEventConsumer
                                              |
                                              v
                                    Notification Layer
```

---

# 👨‍💻 Author

**Mevlüt Can Erdem**

Backend Developer

Java • Spring Boot • Kafka • PostgreSQL • GraphQL
