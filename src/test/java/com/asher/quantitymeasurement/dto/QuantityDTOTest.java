
package com.asher.quantitymeasurement.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityDTOTest {

    @Test
    void givenValidData_whenCreatingDTO_thenReturnCorrectValues() {
        QuantityDTO dto = new QuantityDTO(10.0, "FEET", "LENGTH");

        assertEquals(10.0, dto.getValue());
        assertEquals("FEET", dto.getUnit());
        assertEquals("LENGTH", dto.getMeasurementType());
    }

    @Test
    void givenDTO_whenUpdatingValues_thenReturnUpdatedValues() {
        QuantityDTO dto = new QuantityDTO(10.0, "FEET", "LENGTH");

        dto.setValue(20.0);
        dto.setUnit("METERS");
        dto.setMeasurementType("LENGTH");

        assertEquals(20.0, dto.getValue());
        assertEquals("METERS", dto.getUnit());
        assertEquals("LENGTH", dto.getMeasurementType());
    }

    @Test
    void givenLengthUnit_whenGettingMeasurementType_thenReturnLength() {
        assertEquals("LENGTH",
                QuantityDTO.LengthUnit.FEET.getMeasurementType());
        assertEquals("LENGTH",
                QuantityDTO.LengthUnit.METERS.getMeasurementType());
    }

    @Test
    void givenWeightUnit_whenGettingMeasurementType_thenReturnWeight() {
        assertEquals("WEIGHT",
                QuantityDTO.WeightUnit.KILOGRAM.getMeasurementType());
        assertEquals("WEIGHT",
                QuantityDTO.WeightUnit.OUNCES.getMeasurementType());
    }

    @Test
    void givenVolumeUnit_whenGettingMeasurementType_thenReturnVolume() {
        assertEquals("VOLUME",
                QuantityDTO.VolumeUnit.LITRE.getMeasurementType());
    }

    @Test
    void givenTemperatureUnit_whenGettingMeasurementType_thenReturnTemperature() {
        assertEquals("TEMPERATURE",
                QuantityDTO.TemperatureUnit.CELSIUS.getMeasurementType());
    }

    @Test
    void givenUnit_whenGettingUnitName_thenReturnEnumName() {
        assertEquals("FEET",
                QuantityDTO.LengthUnit.FEET.getUnitName());
        assertEquals("KILOGRAM",
                QuantityDTO.WeightUnit.KILOGRAM.getUnitName());
    }

    @Test
    void givenDTO_whenConvertingToString_thenReturnFormattedValue() {
        QuantityDTO dto = new QuantityDTO(10.0, "FEET", "LENGTH");

        assertEquals("10.0 FEET", dto.toString());
    }
}
