
package com.asher.quantitymeasurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength oneFoot =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength twelveInches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength twoFeet =
                new QuantityLength(2.0, LengthUnit.FEET);

        QuantityLength twentyFourInches =
                new QuantityLength(24.0, LengthUnit.INCHES);

        System.out.println(
                "1 Foot equals 12 Inches: "
                        + oneFoot.equals(twelveInches)
        );

        System.out.println(
                "2 Feet equals 24 Inches: "
                        + twoFeet.equals(twentyFourInches)
        );

        System.out.println(
                "1 Foot equals 2 Feet: "
                        + oneFoot.equals(twoFeet)
        );
    }
}
