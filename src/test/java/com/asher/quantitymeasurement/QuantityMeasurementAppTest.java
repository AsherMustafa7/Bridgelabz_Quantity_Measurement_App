
package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    // ---------------- UC3: FEET TESTS ----------------

    @Test
    void testFeetEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength second =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertEquals(first, second);
    }

    @Test
    void testFeetEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength second =
                new QuantityLength(2.0, LengthUnit.FEET);

        assertNotEquals(first, second);
    }

    // ---------------- UC3: INCHES TESTS ----------------

    @Test
    void testInchesEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength second =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(first, second);
    }

    @Test
    void testInchesEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength second =
                new QuantityLength(24.0, LengthUnit.INCHES);

        assertNotEquals(first, second);
    }

    // ---------------- UC3: CROSS-UNIT TESTS ----------------

    @Test
    void testFeetAndInchesEquality_OneFootEqualsTwelveInches() {
        QuantityLength oneFoot =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength twelveInches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(oneFoot, twelveInches);
    }

    @Test
    void testFeetAndInchesEquality_TwoFeetNotEqualToTwelveInches() {
        QuantityLength twoFeet =
                new QuantityLength(2.0, LengthUnit.FEET);

        QuantityLength twelveInches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertNotEquals(twoFeet, twelveInches);
    }

    @Test
    void testFeetAndInchesEquality_ThreeFeetEqualsThirtySixInches() {
        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(threeFeet, thirtySixInches);
    }

    // ---------------- UC3: OBJECT EQUALITY RULES ----------------

    @Test
    void testEquality_NullComparison() {
        QuantityLength measurement =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertNotEquals(measurement, null);
    }

    @Test
    void testEquality_DifferentObjectType() {
        QuantityLength measurement =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertNotEquals(measurement, "1.0");
    }

    @Test
    void testEquality_SameReference() {
        QuantityLength measurement =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertEquals(measurement, measurement);
    }

    @Test
    void testEquality_Symmetry() {
        QuantityLength feet =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength inches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
        assertEquals(inches, feet);
    }

    @Test
    void testEquality_HashCodeContract() {
        QuantityLength feet =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength inches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
        assertEquals(feet.hashCode(), inches.hashCode());
    }

    @Test
    void testConstructor_NullUnit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityLength(1.0, null)
        );
    }

    // ---------------- UC4: YARDS TESTS ----------------

    @Test
    void testYardsEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength second =
                new QuantityLength(1.0, LengthUnit.YARDS);

        assertEquals(first, second);
    }

    @Test
    void testYardsEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength second =
                new QuantityLength(2.0, LengthUnit.YARDS);

        assertNotEquals(first, second);
    }

    @Test
    void testYardsAndFeetEquality_OneYardEqualsThreeFeet() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        assertEquals(oneYard, threeFeet);
    }

    @Test
    void testYardsAndInchesEquality_OneYardEqualsThirtySixInches() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(oneYard, thirtySixInches);
    }

    @Test
    void testYardsAndFeetEquality_DifferentMeasurements() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength twoFeet =
                new QuantityLength(2.0, LengthUnit.FEET);

        assertNotEquals(oneYard, twoFeet);
    }

    // ---------------- UC4: CENTIMETERS TESTS ----------------

    @Test
    void testCentimetersEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(100.0, LengthUnit.CENTIMETERS);

        QuantityLength second =
                new QuantityLength(100.0, LengthUnit.CENTIMETERS);

        assertEquals(first, second);
    }

    @Test
    void testCentimetersEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(100.0, LengthUnit.CENTIMETERS);

        QuantityLength second =
                new QuantityLength(200.0, LengthUnit.CENTIMETERS);

        assertNotEquals(first, second);
    }

    @Test
    void testCentimetersAndInchesEquality_UsingSuppliedConversion() {
        QuantityLength oneCentimeter =
                new QuantityLength(1.0, LengthUnit.CENTIMETERS);

        QuantityLength equivalentInches =
                new QuantityLength(0.393701, LengthUnit.INCHES);

        assertEquals(oneCentimeter, equivalentInches);
    }

    @Test
    void testCentimetersAndFeetEquality_UsingSuppliedConversion() {
        QuantityLength thirtyPointFourEightCentimeters =
                new QuantityLength(30.48, LengthUnit.CENTIMETERS);

        QuantityLength equivalentFeet =
                new QuantityLength(
                        30.48 * (0.393701 / 12.0),
                        LengthUnit.FEET
                );

        assertEquals(
                thirtyPointFourEightCentimeters,
                equivalentFeet
        );
    }

    @Test
    void testCentimetersAndInchesEquality_DifferentMeasurements() {
        QuantityLength oneCentimeter =
                new QuantityLength(1.0, LengthUnit.CENTIMETERS);

        QuantityLength oneInch =
                new QuantityLength(1.0, LengthUnit.INCHES);

        assertNotEquals(oneCentimeter, oneInch);
    }

    // ---------------- UC4: MULTI-UNIT TESTS ----------------

    @Test
    void testMultiUnitEquality_YardsFeetAndInches() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(oneYard, threeFeet);
        assertEquals(threeFeet, thirtySixInches);
        assertEquals(oneYard, thirtySixInches);
    }

    @Test
    void testEquality_Transitivity() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(oneYard, threeFeet);
        assertEquals(threeFeet, thirtySixInches);
        assertEquals(oneYard, thirtySixInches);
    }
}
