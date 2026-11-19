# UC10 - Generic Quantity Class with Unit Interface

## Objective

Refactor the Quantity Measurement application to use a single generic
`Quantity<U extends IMeasurable>` class instead of maintaining separate
measurement implementations for length and weight.

The objective is to reduce duplicated code and make the application
extensible to additional measurement categories.

## Problems Addressed

Before UC10:

- `QuantityLength` and `QuantityWeight` duplicated measurement logic.
- `LengthUnit` and `WeightUnit` duplicated conversion methods.
- Equality, conversion, and addition logic had to be maintained separately.
- Adding a new measurement category required another quantity class.

## Implementation

### 1. IMeasurable Interface

Introduced a common interface implemented by all measurement units.

Methods:

- `getConversionFactor()`
- `convertToBaseUnit(double value)`
- `convertFromBaseUnit(double baseValue)`
- `getUnitName()`

### 2. Unit Enums

Both `LengthUnit` and `WeightUnit` implement `IMeasurable`.

Existing conversion factors are preserved.

### 3. Generic Quantity Class

Introduced:

`Quantity<U extends IMeasurable>`

Responsibilities:

- Measurement value and unit storage.
- Constructor validation.
- Equality and hash code.
- Unit conversion.
- Addition in the first operand's unit.
- Addition in a specified target unit.
- String representation.

The class is immutable.

### 4. Compatibility Wrappers

`QuantityLength` and `QuantityWeight` remain as compatibility wrappers
for earlier use cases.

They delegate measurement operations to the generic `Quantity` class
rather than duplicating the core implementation.

### 5. QuantityMeasurementApp

Demonstration methods now accept generic quantities.

The application uses:

- `Quantity<?>` for category-independent equality.
- Generic methods for conversion.
- Generic methods for addition.

## Concepts Covered

- Java Generics
- Bounded Type Parameters
- Interfaces
- Enums
- Polymorphism
- Delegation
- Encapsulation
- Immutability
- Method Reuse
- `equals()` and `hashCode()`
- Open/Closed Principle
- Single Responsibility Principle
- DRY Principle

## Testing

JUnit 5 tests cover:

- Generic quantity construction.
- Null and non-finite input validation.
- Length equality.
- Weight equality.
- Cross-category equality prevention.
- Hash code consistency.
- Length conversion.
- Weight conversion.
- Addition in the first operand's unit.
- Addition in a target unit.
- Invalid conversion and addition inputs.
- Immutability.
- Generic application methods.

Existing UC1-UC9 tests are retained to verify backward compatibility.

## Run Tests

```bash
mvn clean test