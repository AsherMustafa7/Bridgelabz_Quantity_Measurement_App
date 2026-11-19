
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
            throw new IllegalArgumentException("Measurement value must be finite.");
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

    private double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }

    public static double convert(
            double value,
            LengthUnit sourceUnit,
            LengthUnit targetUnit) {

        validateConversionInput(value, sourceUnit, targetUnit);

        if (sourceUnit == targetUnit) {
            return value;
        }

        double convertedValue = value
                * sourceUnit.getConversionFactor()
                / targetUnit.getConversionFactor();

        if (!Double.isFinite(convertedValue)) {
            throw new IllegalArgumentException(
                    "Converted value is outside the supported numeric range."
            );
        }

        return convertedValue;
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        return new QuantityLength(
                convert(value, unit, targetUnit),
                targetUnit
        );
    }

    /**
     * UC6: Adds two measurements using the first operand's unit.
     */
    public static QuantityLength add(
            QuantityLength first,
            QuantityLength second) {

        if (first == null) {
            throw new IllegalArgumentException("First measurement cannot be null.");
        }

        return add(first, second, first.unit);
    }

    /**
     * UC7: Adds two measurements using an explicitly specified target unit.
     */
    public static QuantityLength add(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        validateAdditionInput(first, second, targetUnit);

        return addInTargetUnit(first, second, targetUnit);
    }

    /**
     * UC7: Adds raw measurements using an explicitly specified target unit.
     */
    public static QuantityLength add(
            double firstValue,
            LengthUnit firstUnit,
            double secondValue,
            LengthUnit secondUnit,
            LengthUnit targetUnit) {

        if (firstUnit == null || secondUnit == null) {
            throw new IllegalArgumentException("Measurement units cannot be null.");
        }

        QuantityLength first = new QuantityLength(firstValue, firstUnit);
        QuantityLength second = new QuantityLength(secondValue, secondUnit);

        return add(first, second, targetUnit);
    }

    /**
     * UC6: Instance addition returns the result in this object's unit.
     */
    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    /**
     * Shared private addition implementation.
     */
    private static QuantityLength addInTargetUnit(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        double firstInBaseUnit = first.convertToBaseUnit();
        double secondInBaseUnit = second.convertToBaseUnit();

        if (!Double.isFinite(firstInBaseUnit)
                || !Double.isFinite(secondInBaseUnit)) {
            throw new IllegalArgumentException(
                    "Measurement exceeds the supported numeric range."
            );
        }

        double sumInBaseUnit = firstInBaseUnit + secondInBaseUnit;

        if (!Double.isFinite(sumInBaseUnit)) {
            throw new IllegalArgumentException(
                    "Addition result exceeds the supported numeric range."
            );
        }

        double result = convert(
                sumInBaseUnit,
                LengthUnit.FEET,
                targetUnit
        );

        return new QuantityLength(result, targetUnit);
    }

    private static void validateAdditionInput(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        if (first == null) {
            throw new IllegalArgumentException("First measurement cannot be null.");
        }

        if (second == null) {
            throw new IllegalArgumentException("Second measurement cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        if (!Double.isFinite(first.value)
                || !Double.isFinite(second.value)) {
            throw new IllegalArgumentException("Measurement values must be finite.");
        }
    }

    private static void validateConversionInput(
            double value,
            LengthUnit sourceUnit,
            LengthUnit targetUnit) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Measurement value must be finite.");
        }

        if (sourceUnit == null) {
            throw new IllegalArgumentException("Source unit cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
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
