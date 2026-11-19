
# UC7 — Addition with Target Unit Specification

## Overview

UC7 extends the Quantity Measurement App by allowing callers to explicitly specify the target unit for addition results.

## Supported Units

- FEET
- INCHES
- YARDS
- CENTIMETERS

## Implemented Methods

### Explicit Target Unit Addition

```java
QuantityLength.add(first, second, targetUnit);
```

### Raw Value Addition

```java
QuantityLength.add(
    firstValue, firstUnit,
    secondValue, secondUnit,
    targetUnit
);
```

### Backward Compatibility

UC6 addition continues to return the result in the first operand's unit.

## Implementation

- Explicit target unit selection.
- Conversion through a common base unit.
- Shared private addition utility method.
- Input validation.
- Immutable result objects.
- Floating-point precision validation.

## Example Results

| Operation | Result |
|---|---|
| 1 FEET + 12 INCHES → FEET | 2 FEET |
| 1 FEET + 12 INCHES → INCHES | 24 INCHES |
| 1 FEET + 12 INCHES → YARDS | 0.6667 YARDS |
| 1 YARDS + 3 FEET → YARDS | 2 YARDS |
| 36 INCHES + 1 YARDS → FEET | 6 FEET |
| 2.54 CENTIMETERS + 1 INCH → CENTIMETERS | 5.08 CENTIMETERS |

## Testing

Run:

```bash
mvn clean test
```

Tests cover explicit target units, commutativity, zero values, negative values, null target validation, precision, and all supported unit combinations.

