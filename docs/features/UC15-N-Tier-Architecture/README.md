
# UC15 - N-Tier Architecture Refactoring

## Overview

Refactored the Quantity Measurement Application into a layered architecture to improve separation of concerns, maintainability, testability, and extensibility.

The application supports Length, Weight, Volume, and Temperature measurements.

## Architecture

The application is organized into the following layers:

1. **Application Layer** - Entry point and application initialization.
2. **Controller Layer** - Handles application requests and delegates operations.
3. **Service Layer** - Contains measurement business logic.
4. **Entity / Model Layer** - Represents measurement models and persisted entities.
5. **Repository Layer** - Stores and retrieves measurement history.

## Architecture Flow

```text
Application
    |
    v
Factory
    |
    v
Facade
    |
    v
Controller
    |
    v
Service
    |
    v
Repository
```

## Design Patterns

### 1. Factory Pattern

`QuantityMeasurementFactory` creates and connects the repository, service, controller, and facade.

### 2. Facade Pattern

`QuantityMeasurementFacade` provides a simplified interface for measurement operations and delegates requests to the controller.

### 3. Singleton Pattern

`QuantityMeasurementCacheRepository` provides a shared repository instance.

## Dependency Injection

Constructor-based dependency injection is used to provide dependencies:

- Service receives `IQuantityMeasurementRepository`.
- Controller receives `IQuantityMeasurementService`.
- Facade receives `QuantityMeasurementController`.

This reduces coupling and improves testability.

## Repository and Persistence

The repository:

- Stores measurement history in an in-memory collection.
- Persists measurement records to a serialized file.
- Loads saved records when initialized.
- Supports filtering by operation and measurement type.
- Supports retrieving the total record count.

The appendable object output stream allows additional records to be written without adding another serialization header.

## Measurement Operations

The application supports:

- Equality comparison
- Unit conversion
- Addition
- Subtraction
- Division
- Temperature conversion

Unsupported operations are handled through meaningful exceptions and error history records.

## Exception Handling

`QuantityMeasurementException` provides a custom exception for measurement service errors.

The controller handles service exceptions and returns formatted results or error messages.

## Project Structure

```text
src/
├── main/
│   ├── java/com/asher/quantitymeasurement/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── enums/
│   │   ├── exception/
│   │   ├── facade/
│   │   ├── factory/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── util/
│   │   ├── IMeasurable.java
│   │   ├── Quantity.java
│   │   └── QuantityMeasurementApp.java
│   └── resources/
└── test/
    └── java/com/asher/quantitymeasurement/
        ├── controller/
        ├── dto/
        ├── entity/
        ├── enums/
        ├── exception/
        ├── integration/
        ├── repository/
        └── service/
```

## Running the Application

Compile and run:

```bash
mvn clean compile
mvn exec:java "-Dexec.mainClass=com.asher.quantitymeasurement.QuantityMeasurementApp"
```

## Running Tests

Run the complete test suite:

```bash
mvn clean test
```

The test suite includes unit tests and integration tests for the application layers.

## Technologies

- Java
- Maven
- JUnit 5
- Mockito
- Java Object Serialization

## Outcome

The application has been refactored into a layered design using interfaces, constructor-based dependency injection, and established design patterns while retaining the functionality developed in UC1–UC14.
