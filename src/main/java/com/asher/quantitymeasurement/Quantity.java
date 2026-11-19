package com.asher.quantitymeasurement;

import java.util.Objects;

/**
 * Represents an immutable quantity of any measurable unit.
 *
 * @param <U> the unit type implementing IMeasurable
 */
public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Measurement unit cannot be null.");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    "Measurement value must be finite."
            );
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    /**
     * Converts this quantity into the specified target unit.
     */
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        if (unit == targetUnit) {
            return new Quantity<>(value, targetUnit);
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(convertedValue, targetUnit);
    }

    /**
     * Adds another quantity and returns the result in this quantity's unit.
     */
    public Quantity<U> add(Quantity<U> other) {
        return add(other, unit);
    }

    /**
     * Adds another quantity and returns the result in the specified unit.
     */
    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException(
                    "Second measurement cannot be null."
            );
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double firstInBaseUnit = unit.convertToBaseUnit(value);
        double secondInBaseUnit =
                other.unit.convertToBaseUnit(other.value);

        double sumInBaseUnit = firstInBaseUnit + secondInBaseUnit;

        if (!Double.isFinite(sumInBaseUnit)) {
            throw new IllegalArgumentException(
                    "Addition result exceeds the supported numeric range."
            );
        }

        double result = targetUnit.convertFromBaseUnit(sumInBaseUnit);

        return new Quantity<>(result, targetUnit);
    }

    /**
     * Converts this quantity into its category's base unit.
     */
    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Quantity<?> that = (Quantity<?>) object;

        // Prevent comparisons across different measurement categories.
        if (!unit.getClass().equals(that.unit.getClass())) {
            return false;
        }

        return Double.compare(
                convertToBaseUnit(),
                that.convertToBaseUnit()
        ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                unit.getClass(),
                Double.hashCode(convertToBaseUnit())
        );
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}