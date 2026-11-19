# UC5 — Unit-to-Unit Conversion (Same Measurement Type)

## Overview

UC5 extends the Quantity Measurement App by introducing explicit conversion between supported length units.

The application can now convert a measurement from one unit to another using centralized conversion factors defined in the `LengthUnit` enum.

Supported units:

- FEET
- INCHES
- YARDS
- CENTIMETERS

## Features Implemented

- Static unit conversion using `QuantityLength.convert()`.
- Instance-based conversion using `convertTo()`.
- Conversion between all supported length units.
- Conversion through centralized conversion factors.
- Validation of null units and non-finite numeric values.
- Overflow detection.
- Immutable conversion: the original object is not modified.
- Method overloading in `QuantityMeasurementApp`.
- JavaDoc documentation.
- Readable object representation using `toString()`.

## Conversion Formula

All conversion factors are relative to feet.

\[
\text{Result} =
\text{Value} \times
\frac{\text{Source Unit Factor}}{\text{Target Unit Factor}}
\]

Example:

```java
QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
```

Result:

```text
12.0
```

## Conversion APIs

### 1. Static Conversion

```java
public static double convert(
    double value,
    LengthUnit sourceUnit,
    LengthUnit targetUnit
)
```

Accepts a numeric value and its source and target units.

### 2. Instance Conversion

```java
public QuantityLength convertTo(LengthUnit targetUnit)
```

Converts an existing `QuantityLength` object and returns a new object.

The original measurement remains unchanged.

## Input Validation

The conversion API rejects:

- Null source units.
- Null target units.
- `NaN` values.
- Positive infinity.
- Negative infinity.
- Conversion results outside the supported `double` range.

Invalid inputs produce `IllegalArgumentException`.

## Method Overloading

`QuantityMeasurementApp` provides two overloaded methods:

```java
demonstrateLengthConversion(
    double value,
    LengthUnit sourceUnit,
    LengthUnit targetUnit
)
```

And:

```java
demonstrateLengthConversion(
    QuantityLength length,
    LengthUnit targetUnit
)
```

The first accepts raw values, while the second accepts an existing measurement object.

## Object Representation

`toString()` provides a readable representation of a measurement.

Example:

```text
3.0 FEET
```

## Test Coverage

UC5 tests cover:

- Feet to inches and inches to feet.
- Yards to inches and inches to yards.
- Centimeters to inches.
- Feet to yards.
- Cross-unit conversion.
- Round-trip conversion.
- Zero and negative values.
- Large and small values.
- Same-unit conversion.
- Null unit validation.
- NaN and infinity validation.
- Overflow handling.
- Instance conversion.
- Object immutability.
- `toString()` output.

Floating-point conversion tests use an epsilon tolerance to account for rounding differences.

## Concepts Learned

- Enum-based conversion factors.
- Immutability and value object semantics.
- Static methods and instance methods.
- Method overloading.
- Method overriding.
- JavaDoc documentation.
- Private helper methods.
- Input validation and exception handling.
- Floating-point precision.
- Unit conversion through base-unit normalization.

## Running the Tests

```powershell
mvn clean test
```

## Running the Application

```powershell
mvn clean compile
java -cp target/classes com.asher.quantitymeasurement.QuantityMeasurementApp
```

## Git Workflow

Feature branch:

```text
feature/UC5-UnitToUnitConversion
```

Commit message:

```text
[Asher Mustafa]: Implement UC5 Unit-to-Unit Conversion
```

UC5 is completed when the implementation and tests pass, the feature branch is pushed, and the feature is merged into `develop`.