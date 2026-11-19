
package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    private final QuantityMeasurementApp app =
            new QuantityMeasurementApp();

    // ---------------- FEET TESTS ----------------

    @Test
    void testFeetEquality_SameValue() {
        QuantityMeasurementApp.Feet feet1 = app.new Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = app.new Feet(1.0);

        assertTrue(feet1.equals(feet2));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        QuantityMeasurementApp.Feet feet1 = app.new Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = app.new Feet(2.0);

        assertFalse(feet1.equals(feet2));
    }

    @Test
    void testFeetEquality_NullComparison() {
        QuantityMeasurementApp.Feet feet = app.new Feet(1.0);

        assertFalse(feet.equals(null));
    }

    @Test
    void testFeetEquality_DifferentObjectType() {
        QuantityMeasurementApp.Feet feet = app.new Feet(1.0);

        assertFalse(feet.equals("1.0"));
    }

    @Test
    void testFeetEquality_SameReference() {
        QuantityMeasurementApp.Feet feet = app.new Feet(1.0);

        assertTrue(feet.equals(feet));
    }

    // ---------------- INCHES TESTS ----------------

    @Test
    void testInchesEquality_SameValue() {
        QuantityMeasurementApp.Inches inches1 = app.new Inches(1.0);
        QuantityMeasurementApp.Inches inches2 = app.new Inches(1.0);

        assertTrue(inches1.equals(inches2));
    }

    @Test
    void testInchesEquality_DifferentValue() {
        QuantityMeasurementApp.Inches inches1 = app.new Inches(1.0);
        QuantityMeasurementApp.Inches inches2 = app.new Inches(2.0);

        assertFalse(inches1.equals(inches2));
    }

    @Test
    void testInchesEquality_NullComparison() {
        QuantityMeasurementApp.Inches inches = app.new Inches(1.0);

        assertFalse(inches.equals(null));
    }

    @Test
    void testInchesEquality_DifferentObjectType() {
        QuantityMeasurementApp.Inches inches = app.new Inches(1.0);

        assertFalse(inches.equals("1.0"));
    }

    @Test
    void testInchesEquality_SameReference() {
        QuantityMeasurementApp.Inches inches = app.new Inches(1.0);

        assertTrue(inches.equals(inches));
    }

    // ---------------- CROSS-UNIT TESTS ----------------

    @Test
    void testFeetAndInches_AreDifferentTypes() {
        QuantityMeasurementApp.Feet feet = app.new Feet(1.0);
        QuantityMeasurementApp.Inches inches = app.new Inches(1.0);

        assertFalse(feet.equals(inches));
        assertFalse(inches.equals(feet));
    }

    // ---------------- STATIC METHOD TESTS ----------------

    @Test
    void testAreFeetEqual() {
        QuantityMeasurementApp.Feet feet1 = app.new Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = app.new Feet(1.0);

        assertTrue(QuantityMeasurementApp.areFeetEqual(feet1, feet2));
    }

    @Test
    void testAreInchesEqual() {
        QuantityMeasurementApp.Inches inches1 = app.new Inches(1.0);
        QuantityMeasurementApp.Inches inches2 = app.new Inches(1.0);

        assertTrue(QuantityMeasurementApp.areInchesEqual(inches1, inches2));
    }
}
