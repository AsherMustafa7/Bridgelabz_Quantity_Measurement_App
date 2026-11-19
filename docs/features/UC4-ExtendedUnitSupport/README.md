
# UC4 – Extended Unit Support

## Objective

Extend the generic Quantity Measurement Application to support
additional length units without modifying the existing equality
implementation.

## Supported Units

| Unit | Conversion Factor to Feet |
|---|---:|
| FEET | 1.0 |
| INCHES | 1.0 / 12.0 |
| YARDS | 3.0 |
| CENTIMETERS | 0.393701 / 12.0 |

## Implementation

- Extended the LengthUnit enum.
- Added YARDS as a supported unit.
- Added CENTIMETERS as a supported unit.
- Maintained feet as the common base unit.
- Reused the existing QuantityLength conversion logic.
- Kept the QuantityLength equals() implementation unchanged.
- Preserved previous equality functionality.

## Test Coverage

- Yard-to-yard equality.
- Yard-to-feet equality.
- Yard-to-inches equality.
- Centimeter-to-centimeter equality.
- Centimeter-to-inches equality.
- Centimeter-to-feet equality.
- Different measurement comparisons.
- Multi-unit equality.
- Equality transitivity.

## Concepts Covered

- Java Enums
- Extensibility
- DRY Principle
- Abstraction
- Unit Conversion
- Floating-point Precision
- Object Equality
- JUnit 5
- Test-Driven Development

## TDD Implementation

1. RED – Added tests for yards and centimeters.
2. GREEN – Extended LengthUnit with new conversion factors.
3. REFACTOR – Reused existing generic conversion and equality logic.

## Verification

Run:

```bash
mvn clean test
```

All 25 tests should pass.
