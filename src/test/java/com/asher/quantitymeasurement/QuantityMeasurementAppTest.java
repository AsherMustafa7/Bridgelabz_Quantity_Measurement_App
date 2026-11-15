
package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    private final QuantityMeasurementApp app =
            new QuantityMeasurementApp();

    @Test
    void testEquality_SameValue() {
        QuantityMeasurementApp.Feet feet1 = app.new Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = app.new Feet(1.0);

        assertTrue(feet1.equals(feet2));
    }

    @Test
    void testEquality_DifferentValue() {
        QuantityMeasurementApp.Feet feet1 = app.new Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = app.new Feet(2.0);

        assertFalse(feet1.equals(feet2));
    }

    @Test
    void testEquality_NullComparison() {
        QuantityMeasurementApp.Feet feet = app.new Feet(1.0);

        assertFalse(feet.equals(null));
    }

    @Test
    void testEquality_NonNumericObject() {
        QuantityMeasurementApp.Feet feet = app.new Feet(1.0);

        assertFalse(feet.equals("1.0"));
    }

    @Test
    void testEquality_SameReference() {
        QuantityMeasurementApp.Feet feet = app.new Feet(1.0);

        assertTrue(feet.equals(feet));
    }
}
