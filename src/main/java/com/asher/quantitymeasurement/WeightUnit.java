package com.asher.quantitymeasurement;

/**
 * Defines supported weight units.
 * Kilogram is the base unit.
 */
public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592),
    OUNCES(0.0283495);
    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
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