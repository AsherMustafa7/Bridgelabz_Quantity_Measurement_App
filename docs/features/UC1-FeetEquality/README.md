
# UC1 – Feet Measurement Equality

## Objective

Implement equality comparison between two numerical measurements
represented in feet.

## Implementation

- Created QuantityMeasurementApp.
- Implemented Feet as an inner class.
- Used a private final double field.
- Implemented constructor-based initialization.
- Overrode equals() for value-based equality.
- Used Double.compare() for floating-point comparison.
- Overrode hashCode() to maintain the equality contract.

## Test Cases

| Test Case | Expected Result |
|---|---|
| Same values | true |
| Different values | false |
| Null comparison | false |
| Different object type | false |
| Same reference | true |

## Concepts Covered

- Object Equality
- equals() and hashCode()
- Encapsulation
- Immutability
- Inner Classes
- Floating-point Comparison
- Null Safety
- Type Safety
- JUnit 5
- Test-Driven Development

## TDD Implementation

1. RED – Wrote failing tests.
2. GREEN – Implemented the Feet class.
3. REFACTOR – Improved implementation while preserving tests.

## Verification

Run:

```bash
mvn clean test
```

Expected result: All five tests pass.
