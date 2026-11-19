# UC14 – Temperature Measurement with Selective Arithmetic Support

## Overview

UC14 extends the Quantity Measurement Application to support temperature measurements alongside the existing length, weight, and volume measurements.

Unlike conventional measurable quantities, temperature measurements have different arithmetic characteristics. Temperature conversion and equality comparisons are supported, but arithmetic operations such as addition, subtraction, and division are not supported in this implementation.

This use case also refactors the `IMeasurable` interface to introduce optional arithmetic operation support using default methods and a functional interface.

## Objectives

- Introduce temperature measurement support.
- Support Celsius, Fahrenheit, and Kelvin.
- Implement temperature conversion using standard conversion formulas.
- Support equality comparisons across temperature units.
- Prevent unsupported arithmetic operations on temperature measurements.
- Refactor `IMeasurable` to support optional arithmetic capabilities.
- Preserve existing functionality for length, weight, and volume.
- Maintain backward compatibility with previous use cases.

## Temperature Units

The application supports three temperature units:

| Unit | Description |
|---|---|
| CELSIUS | Base temperature unit |
| FAHRENHEIT | Fahrenheit temperature scale |
| KELVIN | Absolute temperature scale |

Celsius is used as the base unit for temperature conversion.

## Temperature Conversion

Temperature conversion requires formulas rather than simple multiplication by a conversion factor because temperature scales have different reference points.

### Fahrenheit to Celsius

```text
C = (F - 32) × 5 / 9
```

### Celsius to Fahrenheit

```text
F = C × 9 / 5 + 32
```

### Kelvin to Celsius

```text
C = K - 273.15
```

### Celsius to Kelvin

```text
K = C + 273.15
```

## Implementation

### 1. SupportsArithmetic Functional Interface

Introduced a functional interface to represent arithmetic support.

```java
@FunctionalInterface
public interface SupportsArithmetic {

    boolean isSupported();
}
```

This allows arithmetic support to be represented using lambda expressions.

### 2. Refactoring IMeasurable

The `IMeasurable` interface now includes optional arithmetic support through default methods.

New methods include:

- `supportsArithmetic()`
- `supportsAddition()`
- `supportsSubtraction()`
- `supportsDivision()`
- `validateOperationSupport(String operation)`

Existing measurement units inherit the default arithmetic support, preserving their previous behavior.

Temperature units override these methods to indicate that arithmetic operations are unsupported.

### 3. TemperatureUnit Enum

Created `TemperatureUnit` implementing `IMeasurable`.

Responsibilities:

- Define CELSIUS, FAHRENHEIT, and KELVIN.
- Convert values into the Celsius base unit.
- Convert Celsius base values into target units.
- Return the unit name.
- Reject unsupported arithmetic operations.
- Validate finite input and conversion values.

### 4. Quantity Class Integration

Updated the generic `Quantity<U extends IMeasurable>` class.

The arithmetic methods now validate whether the selected measurement unit supports the requested operation before executing it.

The following operations perform support validation:

- `add()`
- `subtract()`
- `divide()`

Unsupported operations throw `UnsupportedOperationException` with a meaningful error message.

The existing centralized arithmetic implementation from UC13 is preserved.

## Arithmetic Support

| Measurement Category | Conversion | Equality | Addition | Subtraction | Division |
|---|---|---|---|---|---|
| Length | Supported | Supported | Supported | Supported | Supported |
| Weight | Supported | Supported | Supported | Supported | Supported |
| Volume | Supported | Supported | Supported | Supported | Supported |
| Temperature | Supported | Supported | Unsupported | Unsupported | Unsupported |

## Exception Handling

Temperature arithmetic operations throw:

```java
UnsupportedOperationException
```

Example:

```java
Quantity<TemperatureUnit> temperature =
        new Quantity<>(25.0, TemperatureUnit.CELSIUS);

Quantity<TemperatureUnit> anotherTemperature =
        new Quantity<>(15.0, TemperatureUnit.CELSIUS);

temperature.add(anotherTemperature);
```

Expected exception:

```text
UnsupportedOperationException:
addition is not supported for temperature measurements.
```

## Example Usage

### Temperature Conversion

```java
Quantity<TemperatureUnit> celsius =
        new Quantity<>(100.0, TemperatureUnit.CELSIUS);

Quantity<TemperatureUnit> fahrenheit =
        celsius.convertTo(TemperatureUnit.FAHRENHEIT);

System.out.println(fahrenheit);
```

Output:

```text
212.0 FAHRENHEIT
```

### Temperature Equality

```java
Quantity<TemperatureUnit> celsius =
        new Quantity<>(0.0, TemperatureUnit.CELSIUS);

Quantity<TemperatureUnit> fahrenheit =
        new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

System.out.println(celsius.equals(fahrenheit));
```

Expected output:

```text
true
```

## Design Principles Applied

### Interface Segregation Principle (ISP)

The refactored interface allows measurement categories to declare which arithmetic capabilities they support instead of forcing every category to support every operation.

### Open/Closed Principle (OCP)

New measurement categories can implement `IMeasurable` and define their own operation-support behavior without requiring changes to existing measurement units.

### Don't Repeat Yourself (DRY)

The existing centralized arithmetic implementation from UC13 is retained, while operation-support validation is handled through the common interface.

### Backward Compatibility

Existing length, weight, and volume measurement behavior is preserved through default arithmetic support methods.

## Project Structure

```text
src/
├── main/
│   └── java/
│       └── com/asher/quantitymeasurement/
│           ├── IMeasurable.java
│           ├── SupportsArithmetic.java
│           ├── Quantity.java
│           ├── LengthUnit.java
│           ├── WeightUnit.java
│           ├── VolumeUnit.java
│           ├── TemperatureUnit.java
│           └── QuantityMeasurementApp.java
│
└── test/
    └── java/
        └── com/asher/quantitymeasurement/
```

## Running the Application

Run the Maven test suite:

```bash
mvn clean test
```

Run the application:

```bash
mvn clean compile
```

Then execute the main class:

```bash
java -cp target/classes com.asher.quantitymeasurement.QuantityMeasurementApp
```

## Git Workflow

Feature branch:

```text
feature/UC14-Temperature-Measurement
```

Commit:

```text
[Asher Mustafa]: Implement UC14 Temperature Measurement
```

Merge commit:

```text
Merge UC14 Temperature Measurement into develop
```

## Conclusion

UC14 extends the Quantity Measurement Application with temperature conversion and equality support while introducing selective arithmetic capabilities through interface default methods.

The implementation preserves existing measurement behavior and makes the generic quantity model more extensible for future measurement categories.