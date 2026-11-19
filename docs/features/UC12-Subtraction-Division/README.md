# UC12 — Subtraction and Division Operations on Quantity Measurements

## Overview

UC12 extends the generic Quantity Measurement Application with two arithmetic operations:

- **Subtraction:** Calculates the difference between two quantities belonging to the same measurement category.
- **Division:** Calculates the dimensionless ratio between two quantities belonging to the same measurement category.

Both operations support cross-unit calculations while preserving the generic `Quantity<U extends IMeasurable>` architecture established in UC10.

## Features Implemented

### 1. Subtraction

Two overloaded methods were introduced:

```java
subtract(Quantity<U> other)
subtract(Quantity<U> other, U targetUnit)
```

**Behavior:**
- Converts both quantities into their common base unit.
- Calculates the difference.
- Uses the first operand's unit by default.
- Supports an explicitly specified target unit.
- Rounds the converted result to two decimal places using `RoundingMode.HALF_UP`.
- Returns a new immutable `Quantity<U>` object.

**Examples:**

```java
new Quantity<>(10.0, LengthUnit.FEET)
    .subtract(new Quantity<>(6.0, LengthUnit.INCHES));

// Result: 9.5 FEET
```

```java
new Quantity<>(10.0, LengthUnit.FEET)
    .subtract(
        new Quantity<>(6.0, LengthUnit.INCHES),
        LengthUnit.INCHES
    );

// Result: 114.0 INCHES
```

Negative and zero results are supported.

### 2. Division

The following method was introduced:

```java
divide(Quantity<U> other)
```

**Behavior:**
- Converts both quantities into their common base unit.
- Divides the base-unit values.
- Returns a primitive `double`.
- Produces a dimensionless ratio.
- Preserves floating-point precision without arbitrary rounding.

**Examples:**

```java
new Quantity<>(10.0, LengthUnit.FEET)
    .divide(new Quantity<>(2.0, LengthUnit.FEET));

// Result: 5.0
```

```java
new Quantity<>(24.0, LengthUnit.INCHES)
    .divide(new Quantity<>(2.0, LengthUnit.FEET));

// Result: 1.0
```

Division by zero throws `ArithmeticException`.

### 3. Measurement Categories

Both operations work with all existing measurement categories:

| Category | Units |
|---|---|
| Length | FEET, INCHES, YARDS, CENTIMETERS |
| Weight | KILOGRAM, GRAM, POUND |
| Volume | LITRE, MILLILITRE, GALLON |

No category-specific arithmetic implementation is required.

### 4. Application Demonstrations

`QuantityMeasurementApp` now includes generic demonstration methods:

```java
demonstrateSubtraction(first, second, targetUnit)
demonstrateDivision(first, second)
```

The application demonstrates equality, conversion, addition, subtraction, and division for length, weight, and volume.

## Validation and Error Handling

| Condition | Behavior |
|---|---|
| Null second operand | `IllegalArgumentException` |
| Null subtraction target unit | `IllegalArgumentException` |
| Cross-category arithmetic | `IllegalArgumentException` |
| Division by zero | `ArithmeticException` |
| Non-finite constructor value | `IllegalArgumentException` |
| Non-finite arithmetic result | Appropriate arithmetic/range exception |

## Design Principles

### Immutability

`Quantity` retains final fields. Subtraction creates a new quantity, while division returns a scalar. Neither operation modifies its original operands.

### Generic Programming

The implementation uses:

```java
Quantity<U extends IMeasurable>
```

The same arithmetic logic handles length, weight, volume, and future measurement categories.

### Code Reuse

Validation is centralized through private helper methods:

- `validateOther()`
- `validateTargetUnit()`
- `validateSameCategory()`

This avoids duplicating validation logic across arithmetic operations.

### SOLID and Object Calisthenics Review

- **Single Responsibility Principle:** `Quantity` continues to represent and operate on a measurement. Arithmetic methods remain part of that responsibility.
- **Open/Closed Principle:** New measurement categories can implement `IMeasurable` without requiring category-specific subtraction or division implementations.
- **Liskov Substitution Principle:** Measurement units continue to follow the conversion contract defined by `IMeasurable`.
- **Interface Segregation Principle:** The existing measurable-unit interface remains focused on conversion and unit identification.
- **Dependency Inversion Principle:** Generic quantity operations depend on the `IMeasurable` abstraction rather than concrete unit enums.

**Potential design consideration:** `Quantity` now contains several validation helpers and arithmetic operations. If the arithmetic model grows substantially, shared arithmetic behavior could be extracted into a dedicated component. This is not necessary for the current use case and would introduce additional complexity.

## Testing

The test class is:

```text
src/test/java/com/asher/quantitymeasurement/QuantityArithmeticTest.java
```

Coverage includes:

- Same-unit and cross-unit subtraction.
- Explicit and implicit target units.
- Negative and zero subtraction results.
- Rounding and precision.
- Subtraction non-commutativity.
- Same-unit and cross-unit division.
- Ratios greater than, less than, and equal to one.
- Division by zero.
- Large and small ratios.
- Cross-category validation.
- Null argument validation.
- Immutability.
- Division non-associativity.
- Integration with addition.
- Application demonstration methods.

## Running the Application

```powershell
mvn clean compile
java -cp target/classes com.asher.quantitymeasurement.QuantityMeasurementApp
```

## Running Tests

Run only the UC12 tests:

```powershell
mvn -Dtest=QuantityArithmeticTest test
```

Run the complete project test suite:

```powershell
mvn clean test
```

## Expected Outcome

UC12 extends the generic quantity model with subtraction and division while preserving the existing equality, conversion, and addition functionality from UC1–UC11.

The implementation remains reusable across all supported measurement categories.
