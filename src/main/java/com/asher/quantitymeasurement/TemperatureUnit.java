package com.asher.quantitymeasurement;

/**
 * Defines temperature units.
 * Celsius is the base unit.
 *
 * Temperature measurements support conversion and equality,
 * but not arithmetic operations.
 */
public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {

        validateValue(value);

        double result;

        switch (this) {
            case CELSIUS:
                result = value;
                break;

            case FAHRENHEIT:
                result = (value - 32.0) * 5.0 / 9.0;
                break;

            case KELVIN:
                result = value - 273.15;
                break;

            default:
                throw new IllegalStateException("Unknown temperature unit.");
        }

        validateResult(result);
        return result;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {

        validateValue(baseValue);

        double result;

        switch (this) {
            case CELSIUS:
                result = baseValue;
                break;

            case FAHRENHEIT:
                result = baseValue * 9.0 / 5.0 + 32.0;
                break;

            case KELVIN:
                result = baseValue + 273.15;
                break;

            default:
                throw new IllegalStateException("Unknown temperature unit.");
        }

        validateResult(result);
        return result;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }

    @Override
    public boolean supportsAddition() {
        return false;
    }

    @Override
    public boolean supportsSubtraction() {
        return false;
    }

    @Override
    public boolean supportsDivision() {
        return false;
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                operation + " is not supported for temperature measurements."
        );
    }

    private static void validateValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    "Temperature value must be finite."
            );
        }
    }

    private static void validateResult(double result) {
        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(
                    "Converted temperature exceeds the supported numeric range."
            );
        }
    }
}