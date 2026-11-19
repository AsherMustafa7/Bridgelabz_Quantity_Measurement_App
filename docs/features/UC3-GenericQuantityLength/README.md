
# UC3 – Generic Quantity Class

## Objective

Replace separate measurement classes with a generic QuantityLength
class that supports equality comparison across different length units.

## Implementation

- Introduced the LengthUnit enum.
- Added FEET and INCHES enum constants.
- Stored conversion factors relative to feet.
- Introduced the generic QuantityLength class.
- Used immutable fields for measurement value and unit.
- Validated the unit during object construction.
- Implemented conversion to a common base unit.
- Implemented cross-unit equality using equals().
- Implemented hashCode() using the normalized base-unit value.

## Conversion Factors

| Unit | Conversion Factor to Feet |
|---|---:|
| FEET | 1.0 |
| INCHES | 1.0 / 12.0 |

## Equality Examples

| First Measurement | Second Measurement | Result |
|---|---|---|
| 1 Foot | 1 Foot | true |
| 12 Inches | 12 Inches | true |
| 1 Foot | 12 Inches | true |
| 2 Feet | 12 Inches | false |
| 3 Feet | 36 Inches | true |

## Concepts Covered

- DRY Principle
- Generic Class Design
- Java Enums
- Encapsulation
- Immutability
- Abstraction
- Object Equality
- equals() and hashCode()
- Unit Conversion
- JUnit 5
- Test-Driven Development

## TDD Implementation

1. RED – Wrote tests for generic quantity equality.
2. GREEN – Implemented LengthUnit and QuantityLength.
3. REFACTOR – Removed duplicated Feet and Inches classes.

## Verification

Run:

```bash
mvn clean test
```

All 13 tests should pass.
