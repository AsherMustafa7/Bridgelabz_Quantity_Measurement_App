package com.asher.quantitymeasurement.enums;

import com.asher.quantitymeasurement.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IMeasurableTest {

    @Test
    void shouldIdentifyMeasurementType() {
        assertEquals("LENGTH", IMeasurable.getMeasurementType("FEET"));
        assertEquals("WEIGHT", IMeasurable.getMeasurementType("GRAM"));
        assertEquals("VOLUME", IMeasurable.getMeasurementType("LITRE"));
        assertEquals("TEMPERATURE", IMeasurable.getMeasurementType("CELSIUS"));
    }

    @Test
    void shouldIdentifyMeasurementTypeIgnoringCase() {
        assertEquals("LENGTH", IMeasurable.getMeasurementType("feet"));
        assertEquals("WEIGHT", IMeasurable.getMeasurementType("Kilogram"));
    }

    @Test
    void shouldResolveUnitInstance() {
        assertEquals(
                LengthUnit.FEET,
                IMeasurable.getUnit("LENGTH", "FEET")
        );

        assertEquals(
                WeightUnit.GRAM,
                IMeasurable.getUnit("WEIGHT", "GRAM")
        );

        assertEquals(
                VolumeUnit.LITRE,
                IMeasurable.getUnit("VOLUME", "LITRE")
        );

        assertEquals(
                TemperatureUnit.CELSIUS,
                IMeasurable.getUnit("TEMPERATURE", "CELSIUS")
        );
    }

    @Test
    void shouldResolveNewlyAddedUnits() {
        assertEquals(
                LengthUnit.METERS,
                IMeasurable.getUnit("LENGTH", "METERS")
        );

        assertEquals(
                WeightUnit.OUNCES,
                IMeasurable.getUnit("WEIGHT", "OUNCES")
        );
    }

    @Test
    void shouldRejectUnknownUnitName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> IMeasurable.getMeasurementType("UNKNOWN")
        );
    }

    @Test
    void shouldRejectNullUnitName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> IMeasurable.getMeasurementType(null)
        );
    }

    @Test
    void shouldRejectUnknownMeasurementType() {
        assertThrows(
                IllegalArgumentException.class,
                () -> IMeasurable.getUnit("DISTANCE", "FEET")
        );
    }

    @Test
    void shouldRejectUnitFromWrongCategory() {
        assertThrows(
                IllegalArgumentException.class,
                () -> IMeasurable.getUnit("WEIGHT", "FEET")
        );
    }
}