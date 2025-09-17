# 💳 Payment Service - E-Commerce Microservices Platform

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-blue.svg)](https://spring.io/projects/spring-cloud)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Messaging-black.svg)](https://kafka.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A robust, **secure Payment Processing Service** built as part of a comprehensive e-commerce microservices platform. This service handles multiple payment methods, transaction processing, and financial operations using industry-standard security practices and design patterns.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Technologies](#technologies)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Monitoring & Observability](#monitoring--observability)
- [Docker Deployment](#docker-deployment)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

The Payment Service is a critical component of our e-commerce microservices platform, providing:

- **Multi-Payment Gateway Integration** with support for major payment providers
- **Secure Transaction Processing** with encryption and fraud detection
- **Payment Method Abstraction** using Strategy and Factory patterns
- **Event-Driven Architecture** with Apache Kafka for payment events
- **Financial Ledger Management** with comprehensive transaction tracking
- **Observer Pattern Implementation** for payment notifications

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │───▶│  Payment Service│───▶│   MySQL         │
│                 │    │   (Secure)      │    │   Database      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       ▼                       │
         │              ┌─────────────────┐              │
         │              │   Apache Kafka  │              │
         │              │   Event Bus     │              │
         │              └─────────────────┘              │
         │                       │                       │
         │                       ▼                       │
         │              ┌─────────────────┐              │
         │              │ Payment Gateways│              │
         │              │ • Stripe        │              │
         │              │ • PayPal        │              │
         │              │ • Apple Pay     │              │
         │              │ • Google Pay    │              │
         └──────────────┴─────────────────┴──────────────┘
```

## ✨ Features

### 💳 Payment Processing
- **Multiple Payment Methods** - Credit Card, PayPal, Apple Pay, Google Pay
- **Secure Transaction Handling** with PCI DSS compliance considerations
- **Payment Validation** with comprehensive input validation
- **Transaction Status Tracking** with real-time updates
- **Refund Processing** with automated and manual options
- **Payment History** with detailed audit trails

### 🔒 Security & Compliance
- **Data Encryption** for sensitive payment information
- **Tokenization** for secure card data storage
- **Fraud Detection** with basic validation rules
- **PCI DSS Guidelines** following industry best practices
- **Secure API Endpoints** with proper authentication
- **Audit Logging** for compliance requirements

### 🏗️ Design Patterns
- **Strategy Pattern** - Different payment method implementations
- **Factory Pattern** - Payment gateway creation
- **Observer Pattern** - Payment event notifications
- **Proxy Pattern** - Payment gateway abstraction
- **Repository Pattern** - Data access abstraction

### 🔄 Event-Driven Architecture
- **Kafka Integration** for payment event publishing
- **Event Sourcing** for payment state management
- **Saga Pattern** for distributed payment transactions
- **Eventual Consistency** across microservices

### 🔗 Service Integration
- **Order Service Integration** for payment validation
- **User Service Integration** for customer verification
- **Notification Service Integration** for payment alerts
- **Service Discovery** with Eureka client

### 📊 Financial Management
- **Ledger System** for transaction tracking
- **Payment Reconciliation** with external gateways
- **Financial Reporting** with transaction analytics
- **Currency Support** for international payments

## 🛠️ Technologies

| Category | Technology | Version |
|----------|------------|---------|
| **Framework** | Spring Boot | 3.2.7 |
| **Cloud** | Spring Cloud | 2023.0.0 |
| **Service Discovery** | Eureka Client | 3.0+ |
| **Messaging** | Apache Kafka | 3.0+ |
| **Database** | MySQL | 8.0+ |
| **ORM** | Spring Data JPA | 3.0+ |
| **API** | GraphQL | 3.0+ |
| **Documentation** | OpenAPI 3.0 (Swagger) | 3.0+ |
| **Security** | Spring Security | 6.0+ |
| **Monitoring** | Spring Actuator | 3.0+ |
| **Containerization** | Docker | Latest |
| **Build Tool** | Maven | 3.6+ |
| **Java** | OpenJDK | 17+ |

## 🚀 Quick Start

### Prerequisites

- **Java 17+** (OpenJDK recommended)
- **Maven 3.6+**
- **Docker & Docker Compose**
- **MySQL 8.0+** (or use Docker)
- **Apache Kafka** (or use Docker)

### 1. Clone the Repository

```bash
git clone https://github.com/habeneyasu/e-commerce.git
cd e-commerce/paymentservice
```

### 2. Environment Setup

Create a `.env` file in the project root:

```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3310/payment_service_db
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=your_secure_password

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
KAFKA_TOPIC_PAYMENT_EVENT=payment-event

# Payment Gateway Configuration
STRIPE_SECRET_KEY=sk_test_your_stripe_secret_key
PAYPAL_CLIENT_ID=your_paypal_client_id
PAYPAL_CLIENT_SECRET=your_paypal_client_secret
APPLE_PAY_MERCHANT_ID=your_apple_pay_merchant_id
GOOGLE_PAY_MERCHANT_ID=your_google_pay_merchant_id

# Service Discovery
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka
```

### 3. Run with Docker Compose (Recommended)

```bash
# Start all services including dependencies
docker-compose up -d

# Check service status
docker-compose ps

# View logs
docker-compose logs -f payment-service
```

### 4. Run Locally

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 5. Verify Installation

- **Service Health**: http://localhost:8183/actuator/health
- **API Documentation**: http://localhost:8183/swagger-ui/index.html
- **GraphQL Playground**: http://localhost:8183/graphql

## 📚 API Documentation

### Base URL
```
http://localhost:8183
```

### Payment Processing Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/payments` | Process payment | ✅ |
| `GET` | `/payments` | Get all payments (paginated) | ✅ |
| `GET` | `/payments/{id}` | Get payment by ID | ✅ |
| `POST` | `/payments/{id}/refund` | Process refund | ✅ |
| `GET` | `/payments/user/{userId}` | Get payments by user | ✅ |
| `GET` | `/payments/order/{orderId}` | Get payments by order | ✅ |

### Payment Method Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/payment-methods` | Get available payment methods | ✅ |
| `POST` | `/payment-methods/validate` | Validate payment method | ✅ |
| `GET` | `/payment-methods/user/{userId}` | Get user's payment methods | ✅ |

### Transaction Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/transactions` | Get transaction history | ✅ |
| `GET` | `/transactions/{id}` | Get transaction details | ✅ |
| `POST` | `/transactions/{id}/capture` | Capture authorized payment | ✅ |
| `POST` | `/transactions/{id}/void` | Void transaction | ✅ |

### GraphQL Endpoints

| Endpoint | Description |
|----------|-------------|
| `POST /graphql` | GraphQL query endpoint |
| `GET /graphql` | GraphQL playground |

### Example API Usage

#### 1. Process Payment
```bash
curl -X POST http://localhost:8183/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "orderId": "ORD-12345",
    "userId": 1,
    "amount": 99.99,
    "currency": "USD",
    "paymentMethod": "CREDIT_CARD",
    "paymentDetails": {
      "cardNumber": "4111111111111111",
      "expiryMonth": "12",
      "expiryYear": "2025",
      "cvv": "123",
      "cardholderName": "John Doe"
    }
  }'
```

#### 2. Process PayPal Payment
```bash
curl -X POST http://localhost:8183/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "orderId": "ORD-12345",
    "userId": 1,
    "amount": 99.99,
    "currency": "USD",
    "paymentMethod": "PAYPAL",
    "paymentDetails": {
      "paypalOrderId": "PAYPAL-ORDER-12345"
    }
  }'
```

#### 3. Process Refund
```bash
curl -X POST http://localhost:8183/payments/PAY-12345/refund \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "amount": 50.00,
    "reason": "Customer request"
  }'
```

#### 4. Get Payment History
```bash
curl -X GET http://localhost:8183/payments/user/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### 5. GraphQL Query
```graphql
query {
  payments(userId: 1) {
    id
    orderId
    amount
    currency
    status
    paymentMethod
    createdAt
    transactionId
  }
}
```

## ⚙️ Configuration

### Application Properties

The service uses Spring Boot's configuration system with profiles:

- **`application.properties`** - Base configuration
- **`application-container.properties`** - Container-specific settings
- **`application-dev.properties`** - Development environment
- **`application-prod.properties`** - Production environment

### Key Configuration Options

```properties
# Server Configuration
server.port=8183
spring.application.name=PAYMENT-SERVICE

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3310/payment_service_db
spring.datasource.username=user
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate

# Kafka Configuration
spring.kafka.bootstrap-servers=${KAFKA_BOOTSTRAP_SERVERS}
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

# Payment Gateway Configuration
payment.stripe.secret-key=${STRIPE_SECRET_KEY}
payment.paypal.client-id=${PAYPAL_CLIENT_ID}
payment.paypal.client-secret=${PAYPAL_CLIENT_SECRET}
payment.apple-pay.merchant-id=${APPLE_PAY_MERCHANT_ID}
payment.google-pay.merchant-id=${GOOGLE_PAY_MERCHANT_ID}

# Service Discovery
eureka.client.service-url.defaultZone=${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE}
```

## 📊 Monitoring & Observability

### Spring Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Service health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | Application metrics |

### Business Metrics

The service tracks various payment-related metrics:

- **Payment Success Rate** - Successful payments percentage
- **Payment Processing Time** - Average processing time
- **Payment Method Distribution** - Usage by payment method
- **Transaction Volume** - Total transaction value
- **Error Rate** - Failed payment percentage
- **Refund Rate** - Refund percentage

### Security Monitoring

- **Failed Payment Attempts** - Suspicious activity tracking
- **Payment Validation Errors** - Input validation failures
- **Gateway Communication** - External service health
- **Fraud Detection Alerts** - Security event monitoring

### Logging

Structured JSON logging with different levels:

```json
{
  "timestamp": "2024-01-15T10:30:00.000Z",
  "level": "INFO",
  "logger": "com.ecommerce.paymentservice.service.PaymentProcessor",
  "message": "Payment processed successfully",
  "paymentId": "PAY-12345",
  "orderId": "ORD-67890",
  "amount": 99.99,
  "currency": "USD",
  "paymentMethod": "CREDIT_CARD"
}
```

## 🐳 Docker Deployment

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/payment_app.jar app.jar
EXPOSE 8183
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Docker Compose

```yaml
version: '3.8'
services:
  payment-service:
    build: ./paymentservice
    ports:
      - "8183:8183"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://payment-service-db:3306/payment_service_db
      - SPRING_DATASOURCE_USERNAME=user
      - SPRING_DATASOURCE_PASSWORD=test@123
      - KAFKA_BOOTSTRAP_SERVERS=kafka:9092
      - STRIPE_SECRET_KEY=${STRIPE_SECRET_KEY}
      - PAYPAL_CLIENT_ID=${PAYPAL_CLIENT_ID}
      - PAYPAL_CLIENT_SECRET=${PAYPAL_CLIENT_SECRET}
    depends_on:
      - payment-service-db
      - kafka
    networks:
      - microservice-net

  payment-service-db:
    image: mysql:8.0
    environment:
      - MYSQL_DATABASE=payment_service_db
      - MYSQL_USER=user
      - MYSQL_PASSWORD=test@123
      - MYSQL_ROOT_PASSWORD=test@123
    ports:
      - "3310:3306"
    networks:
      - microservice-net
```

### Deployment Commands

```bash
# Build and start services
docker-compose up --build

# Run in background
docker-compose up -d

# View logs
docker-compose logs -f payment-service

# Stop services
docker-compose down
```

## 🛠️ Development

### Project Structure

```
paymentservice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/paymentservice/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST & GraphQL controllers
│   │   │       ├── exception/       # Exception handling
│   │   │       ├── integration/     # External service integration
│   │   │       ├── model/           # JPA entities
│   │   │       ├── modeldto/        # Data Transfer Objects
│   │   │       ├── repository/      # Data repositories
│   │   │       ├── service/         # Business logic & strategies
│   │   │       └── util/            # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/        # Database migrations
│   └── test/                        # Test classes
├── Dockerfile
├── pom.xml
└── README.md
```

### Design Patterns Used

- **Strategy Pattern** - Different payment method implementations
- **Factory Pattern** - Payment gateway creation
- **Observer Pattern** - Payment event notifications
- **Proxy Pattern** - Payment gateway abstraction
- **Repository Pattern** - Data access abstraction
- **Command Pattern** - Payment operation encapsulation

### Payment Gateway Integration

The service supports multiple payment gateways:

#### Stripe Integration
```java
@Service
public class StripePayment implements PaymentStrategy {
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // Stripe payment processing logic
    }
}
```

#### PayPal Integration
```java
@Service
public class PayPalPayment implements PaymentStrategy {
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // PayPal payment processing logic
    }
}
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=PaymentServiceTest

# Run tests with coverage
mvn test jacoco:report
```

### Code Quality

The project follows:
- **Java Coding Standards** (Google Java Style)
- **Spring Boot Best Practices**
- **Security Best Practices** (PCI DSS guidelines)
- **RESTful API Design Principles**
- **SOLID Principles**
- **Clean Architecture**

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/amazing-feature`
3. **Commit** your changes: `git commit -m 'Add amazing feature'`
4. **Push** to the branch: `git push origin feature/amazing-feature`
5. **Open** a Pull Request

### Development Guidelines

- Write **unit tests** for new features
- Follow **existing code style**
- Update **documentation** as needed
- Ensure **all tests pass**
- Add **appropriate logging**
- Consider **security implications**
- Follow **PCI DSS guidelines** for payment processing

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

**Haben Eyasu** - Senior Backend Developer

- **Email**: [habeneyasu@gmail.com](mailto:habeneyasu@gmail.com)
- **LinkedIn**: [linkedin.com/in/habeneyasu](https://linkedin.com/in/habeneyasu)
- **GitHub**: [github.com/habeneyasu](https://github.com/habeneyasu)

---

## �� Project Status

- ✅ **Core Features**: Complete
- ✅ **Payment Gateway Integration**: Stripe, PayPal, Apple Pay, Google Pay
- ✅ **Security Implementation**: Encryption, validation, fraud detection
- ✅ **Event-Driven Architecture**: Kafka integration
- ✅ **Service Integration**: Order, User, Notification services
- ✅ **API Documentation**: OpenAPI 3.0 with Swagger UI
- ✅ **GraphQL Support**: Flexible data querying
- ✅ **Docker Support**: Containerized deployment ready
- 🔄 **Testing**: Unit and integration tests in progress
- 🔄 **Security Audit**: PCI DSS compliance review ongoing

---

**Built with ❤️ by [Haben Eyasu](https://github.com/habeneyasu)**
