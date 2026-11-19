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

    /**
     * Demonstrates subtraction with an explicitly specified target unit.
     */
    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(
            Quantity<U> first,
            Quantity<U> second,
            U targetUnit) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        return first.subtract(second, targetUnit);
    }

    /**
     * Demonstrates division between two quantities of the same category.
     */
    public static <U extends IMeasurable> double demonstrateDivision(
            Quantity<U> first,
            Quantity<U> second) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null."
            );
        }

        return first.divide(second);
    }

    public static void main(String[] args) {

        System.out.println("Quantity Measurement Application");
        System.out.println("---------------------------------");

        // LENGTH
        Quantity<LengthUnit> length1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> length2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println("\nLENGTH");

        System.out.println("Equality: "
                + demonstrateEquality(length1, length2));

        System.out.println("Conversion: "
                + demonstrateConversion(length1, LengthUnit.INCHES));

        System.out.println("Addition: "
                + demonstrateAddition(
                        length1, length2, LengthUnit.FEET
                ));

        System.out.println("Subtraction: "
                + demonstrateSubtraction(
                        length1, length2, LengthUnit.FEET
                ));

        System.out.println("Division: "
                + demonstrateDivision(
                        new Quantity<>(24.0, LengthUnit.INCHES),
                        new Quantity<>(2.0, LengthUnit.FEET)
                ));

        // WEIGHT
        Quantity<WeightUnit> weight1 =
                new Quantity<>(10.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> weight2 =
                new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println("\nWEIGHT");

        System.out.println("Equality: "
                + demonstrateEquality(weight1, weight2));

        System.out.println("Conversion: "
                + demonstrateConversion(weight1, WeightUnit.GRAM));

        System.out.println("Addition: "
                + demonstrateAddition(
                        weight1, weight2, WeightUnit.KILOGRAM
                ));

        System.out.println("Subtraction: "
                + demonstrateSubtraction(
                        weight1, weight2, WeightUnit.KILOGRAM
                ));

        System.out.println("Division: "
                + demonstrateDivision(weight1, weight2));

        // VOLUME
        Quantity<VolumeUnit> volume1 =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> volume2 =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println("\nVOLUME");

        System.out.println("Equality: "
                + demonstrateEquality(volume1, volume2));

        System.out.println("Conversion: "
                + demonstrateConversion(volume1, VolumeUnit.MILLILITRE));

        System.out.println("Addition: "
                + demonstrateAddition(
                        volume1, volume2, VolumeUnit.LITRE
                ));

        System.out.println("Subtraction: "
                + demonstrateSubtraction(
                        volume1, volume2, VolumeUnit.LITRE
                ));

        System.out.println("Division: "
                + demonstrateDivision(
                        volume1,
                        new Quantity<>(2.5, VolumeUnit.LITRE)
                ));
    }
}