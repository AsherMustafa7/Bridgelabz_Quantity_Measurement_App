    package com.asher.quantitymeasurement;

/**
 * Defines supported length units.
 * The base unit for length is FEET.
 */
public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
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

    @Override
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

    @Override
    public String getUnitName() {
        return name();
    }

    private static void validateValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    "Measurement value must be finite."
            );
        }
    }
}