
package com.asher.quantitymeasurement.entity;

import com.asher.quantitymeasurement.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementEntityTest {

    @Test
    void givenTwoQuantities_whenCreatingEntity_thenStoreOperandsAndOperation() {

        QuantityModel<LengthUnit> first =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        QuantityModel<LengthUnit> second =
                new QuantityModel<>(5.0, LengthUnit.INCHES);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(first, second, "ADD");

        assertEquals(10.0, entity.getThisValue());
        assertEquals("FEET", entity.getThisUnit());
        assertEquals("LENGTH", entity.getThisMeasurementType());

        assertEquals(5.0, entity.getThatValue());
        assertEquals("INCHES", entity.getThatUnit());
        assertEquals("LENGTH", entity.getThatMeasurementType());

        assertEquals("ADD", entity.getOperation());
    }

    @Test
    void givenResultString_whenCreatingEntity_thenStoreResultString() {

        QuantityModel<LengthUnit> first =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        first,
                        null,
                        "CONVERT",
                        "120.0 INCHES");

        assertEquals("120.0 INCHES", entity.getResultString());
        assertEquals("LENGTH", entity.getThisMeasurementType());
    }

    @Test
    void givenResultQuantity_whenCreatingEntity_thenStoreResultDetails() {

        QuantityModel<LengthUnit> first =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        QuantityModel<LengthUnit> result =
                new QuantityModel<>(120.0, LengthUnit.INCHES);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        first,
                        null,
                        "CONVERT",
                        result);

        assertEquals(120.0, entity.getResultValue());
        assertEquals("INCHES", entity.getResultUnit());
        assertEquals("LENGTH", entity.getResultMeasurementType());
    }

    @Test
    void givenError_whenCreatingEntity_thenStoreErrorDetails() {

        QuantityModel<LengthUnit> first =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        first,
                        null,
                        "CONVERT",
                        "Invalid conversion",
                        true);

        assertTrue(entity.isError());
        assertEquals("Invalid conversion", entity.getErrorMessage());
    }

    @Test
    void givenEntity_whenCheckingDefaultErrorState_thenReturnFalse() {

        QuantityModel<LengthUnit> first =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(first, null, "CONVERT");

        assertFalse(entity.isError());
        assertNull(entity.getErrorMessage());
        assertNull(entity.getResultString());
    }
}
