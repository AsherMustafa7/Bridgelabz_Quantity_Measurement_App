package com.asher.quantitymeasurement;

/**
 * Compatibility wrapper for length measurements.
 * Core measurement logic is implemented by Quantity.
 */
public class QuantityLength {

    private final Quantity<LengthUnit> quantity;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Length unit cannot be null.");
        }

        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public LengthUnit getUnit() {
        return quantity.getUnit();
    }

    public static double convert(
            double value,
            LengthUnit sourceUnit,
            LengthUnit targetUnit) {

        if (sourceUnit == null) {
            throw new IllegalArgumentException("Source unit cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        return new Quantity<>(value, sourceUnit)
                .convertTo(targetUnit)
                .getValue();
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        Quantity<LengthUnit> result = quantity.convertTo(targetUnit);

        return new QuantityLength(result.getValue(), result.getUnit());
    }

    public static QuantityLength add(
            QuantityLength first,
            QuantityLength second) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        return add(first, second, first.getUnit());
    }

    public static QuantityLength add(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        if (second == null) {
            throw new IllegalArgumentException(
                    "Second measurement cannot be null."
            );
        }

        Quantity<LengthUnit> result =
                first.quantity.add(second.quantity, targetUnit);

        return new QuantityLength(result.getValue(), result.getUnit());
    }

    public static QuantityLength add(
            double firstValue,
            LengthUnit firstUnit,
            double secondValue,
            LengthUnit secondUnit,
            LengthUnit targetUnit) {

        if (firstUnit == null || secondUnit == null) {
            throw new IllegalArgumentException(
                    "Measurement units cannot be null."
            );
        }

        return add(
                new QuantityLength(firstValue, firstUnit),
                new QuantityLength(secondValue, secondUnit),
                targetUnit
        );
    }

    public QuantityLength add(QuantityLength other) {
        return add(this, other, getUnit());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        QuantityLength that = (QuantityLength) object;

        return quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }

    @Override
    public String toString() {
        return getValue() + " " + getUnit();
    }
}