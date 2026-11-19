package com.asher.quantitymeasurement;

/**
 * Represents an immutable weight measurement.
 */
public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException(
                    "Weight unit cannot be null."
            );
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

    public WeightUnit getUnit() {
        return unit;
    }

    /**
     * Converts this measurement into kilograms.
     */
    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    /**
     * Converts this measurement into the specified unit.
     */
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException(
                    "Target unit cannot be null."
            );
        }

        double baseValue = convertToBaseUnit();
        double convertedValue =
                targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityWeight(convertedValue, targetUnit);
    }

    /**
     * Addition with the result in the first operand's unit.
     */
    public static QuantityWeight add(
            QuantityWeight first,
            QuantityWeight second) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        return add(first, second, first.unit);
    }

    /**
     * Addition with an explicitly specified target unit.
     */
    public static QuantityWeight add(
            QuantityWeight first,
            QuantityWeight second,
            WeightUnit targetUnit) {

        validateAdditionInput(first, second, targetUnit);

        return addInTargetUnit(first, second, targetUnit);
    }

    /**
     * Instance addition returns the result in this object's unit.
     */
    public QuantityWeight add(QuantityWeight other) {
        return add(this, other, this.unit);
    }

    /**
     * Performs addition using kilograms as the common base unit.
     */
    private static QuantityWeight addInTargetUnit(
            QuantityWeight first,
            QuantityWeight second,
            WeightUnit targetUnit) {

        double firstInBaseUnit = first.convertToBaseUnit();
        double secondInBaseUnit = second.convertToBaseUnit();

        double sumInBaseUnit =
                firstInBaseUnit + secondInBaseUnit;

        if (!Double.isFinite(sumInBaseUnit)) {
            throw new IllegalArgumentException(
                    "Addition result exceeds the supported numeric range."
            );
        }

        double result =
                targetUnit.convertFromBaseUnit(sumInBaseUnit);

        return new QuantityWeight(result, targetUnit);
    }

    private static void validateAdditionInput(
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

        if (targetUnit == null) {
            throw new IllegalArgumentException(
                    "Target unit cannot be null."
            );
        }
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

        return Double.compare(
                this.convertToBaseUnit(),
                that.convertToBaseUnit()
        ) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}