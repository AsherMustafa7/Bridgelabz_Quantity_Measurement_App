
# UC13 - Centralized Arithmetic Logic to Enforce DRY

## Overview

UC13 refactors the arithmetic operations implemented in UC12.

The objective is to eliminate duplicated validation and conversion logic from `add()`, `subtract()`, and `divide()` by introducing a centralized arithmetic helper.

The public API remains unchanged, and the existing UC12 behavior is preserved.

## Objectives

- Eliminate duplicated arithmetic logic.
- Centralize operand validation.
- Centralize base-unit conversion.
- Introduce an `ArithmeticOperation` enum.
- Preserve existing public method signatures.
- Maintain backward compatibility with UC12.
- Improve maintainability and extensibility.

## Architecture

```text
                 Public Arithmetic Methods
                  /          |          \
               add()     subtract()    divide()
                  \          |          /
                   performBaseArithmetic()
                              |
                              v
                  validateArithmeticOperands()
                              |
                              v
                   Convert to Base Units
                              |
                              v
                    ArithmeticOperation
                              |
                              v
                     Base-Unit Result
```

## Implementation

### 1. ArithmeticOperation Enum

A private enum defines the supported operations:

- `ADD`
- `SUBTRACT`
- `DIVIDE`

Each enum constant implements the `compute()` method.

### 2. validateArithmeticOperands()

This private helper centralizes validation:

- Rejects null operands.
- Checks measurement category compatibility.
- Validates finite numeric values.
- Validates target units when required.

### 3. performBaseArithmetic()

This private helper:

1. Validates the operands.
2. Converts both quantities into their category's base unit.
3. Executes the requested arithmetic operation.
4. Checks the result for numeric overflow.
5. Returns the result in base units.

### 4. Public Arithmetic Methods

| Method | Behavior |
|---|---|
| `add(other)` | Adds and returns result in the first quantity's unit |
| `add(other, targetUnit)` | Adds and returns result in the specified unit |
| `subtract(other)` | Subtracts and returns result in the first quantity's unit |
| `subtract(other, targetUnit)` | Subtracts and returns result in the specified unit |
| `divide(other)` | Returns a dimensionless `double` |

Addition preserves its UC12 precision behavior. Subtraction continues to round to two decimal places using `HALF_UP`. Division remains unrounded.

## Example Operations

### Addition

```java
new Quantity<>(1.0, LengthUnit.FEET)
    .add(new Quantity<>(12.0, LengthUnit.INCHES));

// Result: 2.0 FEET
```

### Subtraction

```java
new Quantity<>(10.0, LengthUnit.FEET)
    .subtract(new Quantity<>(6.0, LengthUnit.INCHES));

// Result: 9.5 FEET
```

### Division

```java
new Quantity<>(10.0, LengthUnit.FEET)
    .divide(new Quantity<>(2.0, LengthUnit.FEET));

// Result: 5.0
```

## Validation

The centralized validation handles:

- Null operands.
- Cross-category arithmetic.
- Non-finite numeric values.
- Null target units for addition and subtraction.
- Division by zero.
- Numeric overflow during conversion and arithmetic.

## Backward Compatibility

UC13 does not introduce new public arithmetic signatures.

Existing functionality from UC12 is retained:

- Addition supports implicit and explicit target units.
- Subtraction supports implicit and explicit target units.
- Division returns a dimensionless scalar.
- Cross-category operations are rejected.
- Original quantities remain immutable.
- Existing UC12 tests remain unchanged.

## Testing

The UC13 refactoring test class verifies:

- Addition, subtraction, and division across length, weight, and volume.
- Cross-unit arithmetic.
- Explicit target units.
- Validation consistency.
- Division-by-zero handling.
- Subtraction rounding and addition precision.
- Immutability.
- Chained arithmetic operations.
- Private helper visibility.
- Existence of the private arithmetic enum.

The complete Maven test suite must pass before merging.

## Design Principles

- **DRY:** Common validation and arithmetic logic is centralized.
- **Encapsulation:** Internal helpers and the enum are private.
- **Single Responsibility:** Public methods select operations and handle result formatting.
- **Open/Closed Principle:** New arithmetic operations can extend the enum and reuse the helper.

## Conclusion

UC13 reduces duplication in the generic `Quantity` class by centralizing validation, conversion, and arithmetic execution.

This provides a cleaner foundation for future operations while preserving the behavior established in UC12.
