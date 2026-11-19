# UC8 — Refactoring Unit Enum to Standalone with Conversion Responsibility

## Overview

UC8 refactors the unit conversion design by assigning conversion responsibilities to the standalone `LengthUnit` enum.

## Key Changes

- Maintained `LengthUnit` as a standalone enum.
- Added `convertToBaseUnit(double value)`.
- Added `convertFromBaseUnit(double baseValue)`.
- Centralized unit conversion logic inside `LengthUnit`.
- Refactored `QuantityLength` to delegate conversion operations to `LengthUnit`.
- Preserved existing equality, conversion, and addition functionality.

## Supported Units

- FEET
- INCHES
- YARDS
- CENTIMETERS

## Base Unit

FEET is used as the common base unit.

## Implementation

```java
double baseValue = sourceUnit.convertToBaseUnit(value);
double result = targetUnit.convertFromBaseUnit(baseValue);
```

## Testing

Run:

```bash
mvn clean test
```

**Test results:** 102 tests passed, 0 failures, 0 errors.

Tests cover unit conversion responsibilities, invalid numeric values, conversion round trips, equality, addition, and backward compatibility with previous use cases.