
# UC2 – Feet and Inches Measurement Equality

## Objective

Extend the Quantity Measurement Application to support equality
comparison for both Feet and Inches measurements.

## Implementation

- Retained the existing Feet inner class from UC1.
- Introduced a separate Inches inner class.
- Both classes use a private final double field.
- Implemented value-based equals() methods.
- Used Double.compare() for measurement comparison.
- Used getClass() for strict type checking.
- Overrode hashCode() in both classes.
- Added separate static comparison methods:
  - areFeetEqual()
  - areInchesEqual()

## Equality Rules

| Comparison | Expected Result |
|---|---|
| Feet(1.0) and Feet(1.0) | true |
| Feet(1.0) and Feet(2.0) | false |
| Inches(1.0) and Inches(1.0) | true |
| Inches(1.0) and Inches(2.0) | false |
| Feet(1.0) and Inches(1.0) | false |
| Measurement and null | false |
| Same object reference | true |

## Concepts Covered

- Object Equality
- equals() and hashCode()
- Encapsulation
- Immutability
- Inner Classes
- Static Methods
- Type Safety
- Double.compare()
- JUnit 5
- Test-Driven Development

## TDD Implementation

1. RED – Created tests for Feet and Inches equality.
2. GREEN – Implemented the Inches class and static comparison methods.
3. REFACTOR – Retained common equality behavior and verified existing UC1 functionality.

## Verification

Run:

```bash
mvn clean test
```

All 13 tests should pass.
