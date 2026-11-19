# UC9 — Weight Measurement Equality, Conversion, and Addition

## Overview

UC9 extends the Quantity Measurement Application by introducing a new measurement category: Weight.

Weight measurements operate independently from length measurements and support equality comparison, unit conversion, and addition.

## Supported Weight Units

| Unit | Conversion Factor to Kilogram |
|---|---:|
| KILOGRAM | 1.0 |
| GRAM | 0.001 |
| POUND | 0.453592 |

Kilogram is the base unit.

## Implementation

### 1. WeightUnit Enum

A standalone enum that encapsulates weight conversion responsibilities.

Methods:
- `getConversionFactor()`
- `convertToBaseUnit(double value)`
- `convertFromBaseUnit(double baseValue)`

### 2. QuantityWeight Class

An immutable value object containing:
- `value`
- `unit`

Features:
- Equality across different weight units.
- Conversion through the kilogram base unit.
- Addition with an implicit target unit.
- Addition with an explicitly specified target unit.
- Validation of null units and non-finite values.
- Consistent `equals()` and `hashCode()` implementations.

### 3. Category Type Safety

`QuantityWeight` and `QuantityLength` are separate measurement categories.

Their equality implementations reject objects belonging to different classes.

Existing length functionality remains unchanged.

## Example Usage

```java
QuantityWeight kilogram =
        new QuantityWeight(1.0, WeightUnit.KILOGRAM);

QuantityWeight grams =
        new QuantityWeight(1000.0, WeightUnit.GRAM);

System.out.println(kilogram.equals(grams)); // true

QuantityWeight converted =
        kilogram.convertTo(WeightUnit.POUND);

QuantityWeight sum = QuantityWeight.add(
        kilogram,
        grams,
        WeightUnit.GRAM
);
```

## Testing

Run the complete test suite:

```bash
mvn clean test
```

### Test Results

- Existing length tests: 102 passed.
- New weight tests: 53 passed.
- Total: 155 passed.
- Failures: 0.
- Errors: 0.

## Concepts Practiced

- Multiple measurement categories.
- Enum-based conversion responsibility.
- Base-unit normalization.
- Immutable value objects.
- Method overloading.
- Equality and hash code contracts.
- Floating-point precision.
- Category type safety.
- Reusable object-oriented design.