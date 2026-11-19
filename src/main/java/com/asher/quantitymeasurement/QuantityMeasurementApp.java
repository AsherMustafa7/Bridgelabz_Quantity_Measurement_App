
package com.asher.quantitymeasurement;

public class QuantityMeasurementApp {

    /**
     * Demonstrates conversion using a raw numeric value.
     */
    public static double demonstrateLengthConversion(
            double value,
            LengthUnit sourceUnit,
            LengthUnit targetUnit) {

        return QuantityLength.convert(value, sourceUnit, targetUnit);
    }

    /**
     * Demonstrates conversion using an existing QuantityLength object.
     */
    public static QuantityLength demonstrateLengthConversion(
            QuantityLength length,
            LengthUnit targetUnit) {

        if (length == null) {
            throw new IllegalArgumentException(
                    "Length measurement cannot be null."
            );
        }

        return length.convertTo(targetUnit);
    }

    /**
     * Demonstrates equality between two length measurements.
     */
    public static boolean demonstrateLengthEquality(
            QuantityLength first,
            QuantityLength second) {

        return first != null && first.equals(second);
    }

    /**
     * Creates two measurements and compares their equality.
     */
    public static boolean demonstrateLengthComparison(
            double firstValue,
            LengthUnit firstUnit,
            double secondValue,
            LengthUnit secondUnit) {

        QuantityLength first =
                new QuantityLength(firstValue, firstUnit);

        QuantityLength second =
                new QuantityLength(secondValue, secondUnit);

        return demonstrateLengthEquality(first, second);
    }

    public static void main(String[] args) {

        System.out.println("UC5: Unit-to-Unit Length Conversion");
        System.out.println("------------------------------------");

        System.out.println(
                "1 Foot to Inches: "
                        + demonstrateLengthConversion(
                                1.0, LengthUnit.FEET, LengthUnit.INCHES)
        );

        System.out.println(
                "3 Yards to Feet: "
                        + demonstrateLengthConversion(
                                3.0, LengthUnit.YARDS, LengthUnit.FEET)
        );

        System.out.println(
                "36 Inches to Yards: "
                        + demonstrateLengthConversion(
                                36.0, LengthUnit.INCHES, LengthUnit.YARDS)
        );

        System.out.println(
                "1 Centimeter to Inches: "
                        + demonstrateLengthConversion(
                                1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES)
        );

        System.out.println(
                "0 Feet to Inches: "
                        + demonstrateLengthConversion(
                                0.0, LengthUnit.FEET, LengthUnit.INCHES)
        );

        QuantityLength lengthInYards =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength lengthInInches =
                demonstrateLengthConversion(
                        lengthInYards, LengthUnit.INCHES);

        System.out.println(
                "Instance conversion: " + lengthInYards
                        + " = " + lengthInInches
        );

        System.out.println(
                "Equality check (1 Yard and 3 Feet): "
                        + demonstrateLengthComparison(
                                1.0, LengthUnit.YARDS,
                                3.0, LengthUnit.FEET)
        );
    }
}
