# Payment Service - E-commerce Application
This is the Payment Service for an e-commerce application. It handles payment transactions, integrating with the User, Order,
and Notification services for seamless interservice communication using WebClient and Apache Kafka. The service is built with Spring Boot and provides a range of payment-related functionalities. It supports both synchronous and asynchronous communication, containerization with Docker, and service discovery via Eureka.

# Table of Contents
Technologies <br>
Features <br>
Installation <br>
Usage <br>
Endpoints <br>
Swagger/OpenAPI <br>
Docker <br>
Environment Variables <br>
Contributing <br>
License <br>
Contact
# Technologies
Spring Boot 3.2.7 - Backend framework <br>
WebFlux - For handling asynchronous non-blocking requests <br>
Apache Kafka - Message broker for asynchronous communication <br>
Eureka Client - Service discovery <br>
Spring Actuator - Monitoring and management <br>
OpenAPI (Swagger) - API documentation and testing tool <br>
Lombok - For reducing boilerplate code <br>
Docker - Containerization tool for deployment <br>
MySQL - Database for storing payment data
# Features
Payment processing and integration with the Order and User services <br>
Consumes messages from the Order service and publishes messages to the Notification service <br>
Asynchronous communication using Apache Kafka <br>
Synchronous communication using WebClient <br>
Service registration and discovery using Eureka <br>
Load balancing for handling requests efficiently <br>
RESTful API endpoints with exception handling <br>
API documentation with OpenAPI/Swagger <br>
Full support for containerization using Docker and orchestration via Docker Compose <br>
Health checks and application metrics via Spring Actuator <br>
# Installation
# Prerequisites
Ensure you have the following installed:
Java 17+ (JDK) <br>
Maven 3.6+ <br>
Docker <br>
Steps to Run Locally <br>
Clone the repository: <br>
git clone https://github.com/habeneyasu/paymentservice.git <br>
cd payment-service <br>
Set up environment variables by creating a .env file in the root directory and set the following variables:<br>
SPRING_DATASOURCE_PASSWORD=your_db_password <br>
Build the project: <br>
mvn clean install <br>

# Using Docker
To run the service using Docker, follow these steps: <br>

Build and start the services using Docker Compose:<br>
docker-compose up --build 
# Contributing <br>
If you wish to contribute to this project:<br>

Fork the repository. <br>
Create a new branch for your feature or bugfix. <br>
Commit your changes with clear messages. <br>
Open a pull request. <br>
# License <br>
This project is licensed under the MIT License. 
# Contact <br>
For any questions, feel free to reach out: <br>

Email: habeneyasu@gmail.com <br>
LinkedIn: https://www.linkedin.com/in/habeneyasu <br>