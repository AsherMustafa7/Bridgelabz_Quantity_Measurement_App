
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

        double conversionRatio =
                sourceUnit.getConversionFactor()
                        / targetUnit.getConversionFactor();

        double convertedValue = value * conversionRatio;

        if (!Double.isFinite(convertedValue)) {
            throw new IllegalArgumentException(
                    "Converted value is outside the supported numeric range."
            );
        }

        return convertedValue;
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        double convertedValue = convert(value, unit, targetUnit);
        return new QuantityLength(convertedValue, targetUnit);
    }

    /**
     * Adds two length measurements and returns the result
     * in the specified target unit.
     */
    public static QuantityLength add(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        validateAdditionInput(first, second, targetUnit);

        double firstInFeet = first.convertToBaseUnit();
        double secondInFeet = second.convertToBaseUnit();

        if (!Double.isFinite(firstInFeet)
                || !Double.isFinite(secondInFeet)) {
            throw new IllegalArgumentException(
                    "Measurement exceeds the supported numeric range."
            );
        }

        double sumInFeet = firstInFeet + secondInFeet;

        if (!Double.isFinite(sumInFeet)) {
            throw new IllegalArgumentException(
                    "Addition result exceeds the supported numeric range."
            );
        }

        double result = convert(
                sumInFeet,
                LengthUnit.FEET,
                targetUnit
        );

        return new QuantityLength(result, targetUnit);
    }

    /**
     * Adds two raw measurements and returns a QuantityLength.
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
     * Adds another measurement using this object's unit as the result unit.
     */
    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
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

        if (!Double.isFinite(first.value)
                || !Double.isFinite(second.value)) {
            throw new IllegalArgumentException(
                    "Measurement values must be finite."
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
