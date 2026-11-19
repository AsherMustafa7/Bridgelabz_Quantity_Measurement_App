
package com.asher.quantitymeasurement;

/**
 * Defines supported length units and their conversion responsibilities.
 * The base unit for length is FEET.
 */
public enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    /**
     * Converts a value in this unit into the base unit (feet).
     */
    public double convertToBaseUnit(double value) {
        validateValue(value);

        double result = value * conversionFactor;

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(
                    "Converted value exceeds the supported numeric range."
            );
        }

        return result;
    }

    /**
     * Converts a value from the base unit (feet) into this unit.
     */
    public double convertFromBaseUnit(double baseValue) {
        validateValue(baseValue);

        double result = baseValue / conversionFactor;

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(
                    "Converted value exceeds the supported numeric range."
            );
        }

        return result;
    }

    private static void validateValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    "Measurement value must be finite."
            );
        }
    }
}
