package com.asher.quantitymeasurement;
/**
 * Defines the common conversion and optional arithmetic contract
 * for measurable units.
 */
public interface IMeasurable {

    SupportsArithmetic DEFAULT_ARITHMETIC_SUPPORT = () -> true;

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    default boolean supportsArithmetic() {
        return DEFAULT_ARITHMETIC_SUPPORT.isSupported();
    }

    default boolean supportsAddition() {
        return supportsArithmetic();
    }

    default boolean supportsSubtraction() {
        return supportsArithmetic();
    }

    default boolean supportsDivision() {
        return supportsArithmetic();
    }

    /**
     * Validates whether the requested arithmetic operation is supported.
     */
    default void validateOperationSupport(String operation) {

        boolean supported = true;

        switch (operation.toLowerCase()) {
            case "addition":
            case "add":
                supported = supportsAddition();
                break;

            case "subtraction":
            case "subtract":
                supported = supportsSubtraction();
                break;

            case "division":
            case "divide":
                supported = supportsDivision();
                break;

            default:
                supported = true;
        }

        if (!supported) {
            throw new UnsupportedOperationException(
                    operation + " is not supported for " + getUnitName() + "."
            );
        }
    }
}