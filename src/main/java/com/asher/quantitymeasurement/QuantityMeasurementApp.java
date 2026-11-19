
package com.asher.quantitymeasurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength oneFoot =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength twelveInches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength oneCentimeter =
                new QuantityLength(1.0, LengthUnit.CENTIMETERS);

        QuantityLength equivalentInches =
                new QuantityLength(0.393701, LengthUnit.INCHES);

        System.out.println(
                "1 Foot equals 12 Inches: "
                        + oneFoot.equals(twelveInches)
        );

        System.out.println(
                "1 Yard equals 3 Feet: "
                        + oneYard.equals(threeFeet)
        );

        System.out.println(
                "1 Centimeter equals 0.393701 Inches: "
                        + oneCentimeter.equals(equivalentInches)
        );
    }
}
