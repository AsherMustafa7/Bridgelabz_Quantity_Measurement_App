
package com.asher.quantitymeasurement;

/**
 * Represents an immutable length measurement.
 *
 * Each measurement consists of a numeric value and a LengthUnit.
 * Equality is determined by normalizing measurements to the common
 * base unit (feet).
 */
public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    /**
     * Creates a length measurement.
     *
     * @param value numeric measurement
     * @param unit unit of measurement
     * @throws IllegalArgumentException if unit is null
     */
    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Length unit cannot be null.");
        }

        this.value = value;
        this.unit = unit;
    }

    /**
     * Returns the numeric measurement.
     *
     * @return measurement value
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the measurement unit.
     *
     * @return measurement unit
     */
    public LengthUnit getUnit() {
        return unit;
    }

    /**
     * Converts this measurement to the common base unit (feet).
     *
     * @return measurement expressed in feet
     */
    private double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }

    /**
     * Converts a numeric measurement from one length unit to another.
     *
     * The conversion uses the conversion factors stored in LengthUnit.
     *
     * Formula:
     * result = value * (source factor / target factor)
     *
     * @param value measurement to convert
     * @param sourceUnit original unit
     * @param targetUnit desired unit
     * @return converted measurement
     * @throws IllegalArgumentException if a unit is null, value is not
     * finite, or the result exceeds the supported double range
     */
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

    /**
     * Converts this measurement into the requested unit.
     *
     * The original object remains unchanged. A new QuantityLength
     * object is returned.
     *
     * @param targetUnit desired unit
     * @return a new QuantityLength expressed in the target unit
     * @throws IllegalArgumentException if targetUnit is null or the
     * conversion produces an unsupported value
     */
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double convertedValue = convert(value, unit, targetUnit);

        return new QuantityLength(convertedValue, targetUnit);
    }

    /**
     * Validates the inputs supplied to the conversion API.
     */
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

    /**
     * Compares two measurements after converting them to feet.
     *
     * @param object object to compare
     * @return true if both represent the same length
     */
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

    /**
     * Generates a hash code based on the normalized measurement.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    /**
     * Returns a readable representation of the measurement.
     *
     * @return formatted measurement
     */
    @Override
    public String toString() {
        return value + " " + unit;
    }
}
