
package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    // Same unit equality

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

    // Cross-unit equality

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

    // Object equality rules

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

    // Equality contract

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

    // Unit validation

    @Test
    void testConstructor_NullUnit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityLength(1.0, null)
        );
    }
}
