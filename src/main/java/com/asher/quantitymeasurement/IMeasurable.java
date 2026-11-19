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

    static String getMeasurementType(String unitName) {
        if (unitName == null || unitName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Unit name cannot be null or empty."
            );
        }

        String normalizedUnit = unitName.trim().toUpperCase();

        if (containsUnit(LengthUnit.values(), normalizedUnit)) {
            return "LENGTH";
        }

        if (containsUnit(WeightUnit.values(), normalizedUnit)) {
            return "WEIGHT";
        }

        if (containsUnit(VolumeUnit.values(), normalizedUnit)) {
            return "VOLUME";
        }

        if (containsUnit(TemperatureUnit.values(), normalizedUnit)) {
            return "TEMPERATURE";
        }

        throw new IllegalArgumentException(
                "Unknown measurement unit: " + unitName
        );
    }

    static IMeasurable getUnit(String measurementType, String unitName) {
        if (measurementType == null || measurementType.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Measurement type cannot be null or empty."
            );
        }

        if (unitName == null || unitName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Unit name cannot be null or empty."
            );
        }

        String normalizedType = measurementType.trim().toUpperCase();
        String normalizedUnit = unitName.trim().toUpperCase();

        try {
            switch (normalizedType) {
                case "LENGTH":
                    return LengthUnit.valueOf(normalizedUnit);

                case "WEIGHT":
                    return WeightUnit.valueOf(normalizedUnit);

                case "VOLUME":
                    return VolumeUnit.valueOf(normalizedUnit);

                case "TEMPERATURE":
                    return TemperatureUnit.valueOf(normalizedUnit);

                default:
                    throw new IllegalArgumentException(
                            "Unknown measurement type: " + measurementType
                    );
            }
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Unit " + unitName + " does not belong to " + measurementType,
                    exception
            );
        }
    }

    // Interface helper methods are public static by default.
    static boolean containsUnit(IMeasurable[] units, String unitName) {
        for (IMeasurable unit : units) {
            if (unit.getUnitName().equalsIgnoreCase(unitName)) {
                return true;
            }
        }

        return false;
    }

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