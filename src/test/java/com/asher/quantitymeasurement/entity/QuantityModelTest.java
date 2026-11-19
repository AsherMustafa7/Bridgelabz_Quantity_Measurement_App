
package com.asher.quantitymeasurement.entity;

import com.asher.quantitymeasurement.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityModelTest {

    @Test
    void givenValidValueAndUnit_whenCreatingModel_thenReturnCorrectValues() {
        QuantityModel<LengthUnit> model =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        assertEquals(10.0, model.getValue());
        assertEquals(LengthUnit.FEET, model.getUnit());
    }

    @Test
    void givenModel_whenConvertingToString_thenReturnFormattedValue() {
        QuantityModel<LengthUnit> model =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        assertEquals("10.0 FEET", model.toString());
    }

    @Test
    void givenDifferentUnits_whenCreatingModels_thenMaintainTheirUnits() {
        QuantityModel<LengthUnit> feet =
                new QuantityModel<>(10.0, LengthUnit.FEET);

        QuantityModel<LengthUnit> meters =
                new QuantityModel<>(5.0, LengthUnit.METERS);

        assertNotEquals(feet.getUnit(), meters.getUnit());
        assertEquals(10.0, feet.getValue());
        assertEquals(5.0, meters.getValue());
    }
}
