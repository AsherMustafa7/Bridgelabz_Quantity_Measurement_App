
package com.asher.quantitymeasurement;

public class QuantityMeasurementApp {

    public static double demonstrateLengthConversion(
            double value,
            LengthUnit sourceUnit,
            LengthUnit targetUnit) {

        return QuantityLength.convert(value, sourceUnit, targetUnit);
    }

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

    public static QuantityLength demonstrateLengthAddition(
            QuantityLength first,
            QuantityLength second,
            LengthUnit targetUnit) {

        return QuantityLength.add(first, second, targetUnit);
    }

    public static QuantityLength demonstrateLengthAddition(
            double firstValue,
            LengthUnit firstUnit,
            double secondValue,
            LengthUnit secondUnit,
            LengthUnit targetUnit) {

        return QuantityLength.add(
                firstValue,
                firstUnit,
                secondValue,
                secondUnit,
                targetUnit
        );
    }

    public static boolean demonstrateLengthEquality(
            QuantityLength first,
            QuantityLength second) {

        return first != null && first.equals(second);
    }

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

        System.out.println("UC6: Addition of Two Length Units");
        System.out.println("---------------------------------");

        QuantityLength result1 = QuantityLength.add(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(2.0, LengthUnit.FEET),
                LengthUnit.FEET
        );

        System.out.println(result1);

        QuantityLength result2 = QuantityLength.add(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCHES),
                LengthUnit.FEET
        );

        System.out.println(result2);

        QuantityLength result3 = QuantityLength.add(
                new QuantityLength(12.0, LengthUnit.INCHES),
                new QuantityLength(1.0, LengthUnit.FEET),
                LengthUnit.INCHES
        );

        System.out.println(result3);

        QuantityLength result4 = QuantityLength.add(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(3.0, LengthUnit.FEET),
                LengthUnit.YARDS
        );

        System.out.println(result4);

        QuantityLength result5 = QuantityLength.add(
                new QuantityLength(2.54, LengthUnit.CENTIMETERS),
                new QuantityLength(1.0, LengthUnit.INCHES),
                LengthUnit.CENTIMETERS
        );

        System.out.println(result5);

        QuantityLength result6 = QuantityLength.add(
                new QuantityLength(5.0, LengthUnit.FEET),
                new QuantityLength(0.0, LengthUnit.INCHES),
                LengthUnit.FEET
        );

        System.out.println(result6);

        QuantityLength result7 = QuantityLength.add(
                new QuantityLength(5.0, LengthUnit.FEET),
                new QuantityLength(-2.0, LengthUnit.FEET),
                LengthUnit.FEET
        );

        System.out.println(result7);
    }
}
