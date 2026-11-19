# UC11 - Volume Measurement Equality, Conversion, and Addition

## Objective

Extend the Quantity Measurement Application to support volume measurements using the generic `Quantity<U extends IMeasurable>` architecture introduced in UC10.

Volume is an independent measurement category and supports equality, conversion, and addition.

## Supported Units

| Unit | Conversion Factor | Base Unit |
|---|---:|---|
| LITRE | 1.0 | Yes |
| MILLILITRE | 0.001 | No |
| GALLON | 3.78541 | No |

The litre is the base unit.

## Implementation

### VolumeUnit Enum

Created `VolumeUnit`, implementing `IMeasurable`.

Implemented methods:

- `getConversionFactor()`
- `convertToBaseUnit(double value)`
- `convertFromBaseUnit(double baseValue)`
- `getUnitName()`

### Generic Quantity Integration

The existing generic `Quantity<VolumeUnit>` class supports:

- Equality comparison between volume quantities.
- Conversion between volume units.
- Addition using the first operand's unit.
- Addition using an explicitly specified target unit.
- Immutable measurement operations.

No changes were required to:

- `Quantity.java`
- `IMeasurable.java`
- `QuantityMeasurementApp.java`

### Category Isolation

Volume quantities cannot be considered equal to length or weight quantities.

The generic type system provides compile-time protection against mixing measurement categories. The runtime category check in `Quantity.equals()` also prevents cross-category equality.

## Example Usage

```java
Quantity<VolumeUnit> litre =
        new Quantity<>(1.0, VolumeUnit.LITRE);

Quantity<VolumeUnit> millilitre =
        new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

// Equality
System.out.println(litre.equals(millilitre));

// Conversion
System.out.println(litre.convertTo(VolumeUnit.MILLILITRE));

// Addition
System.out.println(litre.add(millilitre));

// Addition with explicit target unit
System.out.println(
        litre.add(millilitre, VolumeUnit.GALLON)
);