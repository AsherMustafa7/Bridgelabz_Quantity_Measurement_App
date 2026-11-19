# UC6 — Addition of Two Length Units (Same Category)

## Overview

UC6 introduces addition between two length measurements, including measurements represented in different units.

The result is returned as a new `QuantityLength` object in the specified target unit.

## Supported Units

- FEET
- INCHES
- YARDS
- CENTIMETERS

## Implemented Methods

### Static Addition

```java
QuantityLength.add(
    QuantityLength first,
    QuantityLength second,
    LengthUnit targetUnit
);
```

### Raw-Value Addition

```java
QuantityLength.add(
    double firstValue,
    LengthUnit firstUnit,
    double secondValue,
    LengthUnit secondUnit,
    LengthUnit targetUnit
);
```

### Instance Addition

```java
first.add(second);
```

The instance method returns the result in the first object's unit.

## Conversion and Addition Logic

1. Validate both operands and the target unit.
2. Validate that both measurement values are finite.
3. Convert both operands into feet.
4. Add the normalized values.
5. Convert the sum into the target unit.
6. Return a new QuantityLength object.

## Examples

| Operation | Result |
|---|---|
| 1 FEET + 2 FEET | 3 FEET |
| 1 FEET + 12 INCHES | 2 FEET |
| 12 INCHES + 1 FEET | 24 INCHES |
| 1 YARDS + 3 FEET | 2 YARDS |
| 5 FEET + 0 INCHES | 5 FEET |
| 5 FEET + (-2 FEET) | 3 FEET |

## Validation

The addition API throws `IllegalArgumentException` for:

- Null first operand.
- Null second operand.
- Null target unit.
- NaN measurement values.
- Infinite measurement values.
- Numeric overflow during conversion or addition.

## Design Principles

- Immutable value objects.
- Reusable unit conversion.
- Base-unit normalization.
- Static and instance methods.
- Method overloading.
- Factory-style object creation.
- Floating-point precision handling.

## Mathematical Properties Tested

- Same-unit addition.
- Cross-unit addition.
- Commutativity.
- Identity element (zero).
- Negative values.
- Large and small values.
- Result unit consistency.
- Original object immutability.

## Running Tests

```powershell
mvn clean test
```

## Running Application

```powershell
mvn clean compile
java -cp target/classes com.asher.quantitymeasurement.QuantityMeasurementApp
```

## Git Branch

```text
feature/UC6-LengthAddition
```

## Commit Message

```text
[Asher Mustafa]: Implement UC6 Length Addition
```