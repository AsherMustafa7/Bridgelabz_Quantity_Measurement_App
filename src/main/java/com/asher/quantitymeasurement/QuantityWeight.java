package com.asher.quantitymeasurement;

/**
 * Compatibility wrapper for weight measurements.
 * Core measurement logic is implemented by Quantity.
 */
public class QuantityWeight {

    private final Quantity<WeightUnit> quantity;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Weight unit cannot be null.");
        }

        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public WeightUnit getUnit() {
        return quantity.getUnit();
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        Quantity<WeightUnit> result = quantity.convertTo(targetUnit);

        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    public static QuantityWeight add(
            QuantityWeight first,
            QuantityWeight second) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        return add(first, second, first.getUnit());
    }

    public static QuantityWeight add(
            QuantityWeight first,
            QuantityWeight second,
            WeightUnit targetUnit) {

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

        Quantity<WeightUnit> result =
                first.quantity.add(second.quantity, targetUnit);

        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    public QuantityWeight add(QuantityWeight other) {
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

        QuantityWeight that = (QuantityWeight) object;

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