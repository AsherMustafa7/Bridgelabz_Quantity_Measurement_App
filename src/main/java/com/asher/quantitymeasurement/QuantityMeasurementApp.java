package com.asher.quantitymeasurement;

public class QuantityMeasurementApp {

    public static boolean demonstrateEquality(
            Quantity<?> first,
            Quantity<?> second) {

        return first != null && first.equals(second);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
            Quantity<U> quantity,
            U targetUnit) {

        if (quantity == null) {
            throw new IllegalArgumentException(
                    "Measurement cannot be null."
            );
        }

        return quantity.convertTo(targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> first,
            Quantity<U> second,
            U targetUnit) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        return first.add(second, targetUnit);
    }

    public static void main(String[] args) {

        System.out.println("UC10: Generic Quantity Measurement");
        System.out.println("----------------------------------");

        Quantity<LengthUnit> length1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> length2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println("Length equality: "
                + demonstrateEquality(length1, length2));

        System.out.println("Length conversion: "
                + demonstrateConversion(length1, LengthUnit.INCHES));

        System.out.println("Length addition: "
                + demonstrateAddition(
                        length1,
                        length2,
                        LengthUnit.FEET
                ));

        Quantity<WeightUnit> weight1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> weight2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("Weight equality: "
                + demonstrateEquality(weight1, weight2));

        System.out.println("Weight conversion: "
                + demonstrateConversion(weight1, WeightUnit.GRAM));

        System.out.println("Weight addition: "
                + demonstrateAddition(
                        weight1,
                        weight2,
                        WeightUnit.KILOGRAM
                ));
    }
}