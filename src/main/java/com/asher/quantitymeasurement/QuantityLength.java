
package com.asher.quantitymeasurement;

/**
 * Represents an immutable length measurement.
 */
public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Length unit cannot be null.");
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

    public LengthUnit getUnit() {
        return unit;
    }

    /**
     * Delegates conversion to the unit.
     */
    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    /**
     * Converts a value from one unit to another.
     */
    public static double convert(
            double value,
            LengthUnit sourceUnit,
            LengthUnit targetUnit) {

        validateConversionInput(value, sourceUnit, targetUnit);

        if (sourceUnit == targetUnit) {
            return value;
        }

        double baseValue = sourceUnit.convertToBaseUnit(value);

        return targetUnit.convertFromBaseUnit(baseValue);
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        double convertedValue = convert(value, unit, targetUnit);

        return new QuantityLength(convertedValue, targetUnit);
    }

    /**
     * UC6: Addition using the first operand's unit.
     */
    public static QuantityLength add(
            QuantityLength first,
            QuantityLength second) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        return add(first, second, first.unit);
    }

    /**
     * UC7: Addition using an explicitly specified target unit.
     */
    public static QuantityLength add(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        validateAdditionInput(first, second, targetUnit);

        return addInTargetUnit(first, second, targetUnit);
    }

    /**
     * UC7: Addition using raw measurement values.
     */
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

        QuantityLength first =
                new QuantityLength(firstValue, firstUnit);

        QuantityLength second =
                new QuantityLength(secondValue, secondUnit);

        return add(first, second, targetUnit);
    }

    /**
     * Instance addition returns the result in this object's unit.
     */
    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    /**
     * Shared addition implementation.
     * Conversion responsibility belongs to LengthUnit.
     */
    private static QuantityLength addInTargetUnit(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        double firstInBaseUnit = first.convertToBaseUnit();
        double secondInBaseUnit = second.convertToBaseUnit();

        double sumInBaseUnit = firstInBaseUnit + secondInBaseUnit;

        if (!Double.isFinite(sumInBaseUnit)) {
            throw new IllegalArgumentException(
                    "Addition result exceeds the supported numeric range."
            );
        }

        double result =
                targetUnit.convertFromBaseUnit(sumInBaseUnit);

        return new QuantityLength(result, targetUnit);
    }

    private static void validateAdditionInput(
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

        if (targetUnit == null) {
            throw new IllegalArgumentException(
                    "Target unit cannot be null."
            );
        }
    }

    private static void validateConversionInput(
            double value,
            LengthUnit sourceUnit,
            LengthUnit targetUnit) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    "Measurement value must be finite."
            );
        }

        if (sourceUnit == null) {
            throw new IllegalArgumentException(
                    "Source unit cannot be null."
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

        QuantityLength that = (QuantityLength) object;

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
