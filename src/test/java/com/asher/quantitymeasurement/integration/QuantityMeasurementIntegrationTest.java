
package com.asher.quantitymeasurement.integration;

import com.asher.quantitymeasurement.dto.QuantityDTO;
import com.asher.quantitymeasurement.facade.QuantityMeasurementFacade;
import com.asher.quantitymeasurement.factory.QuantityMeasurementFactory;
import com.asher.quantitymeasurement.repository.QuantityMeasurementCacheRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementFacade facade;
    private QuantityMeasurementCacheRepository repository;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        repository.deleteAll();

        facade = QuantityMeasurementFactory.createFacade();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void givenLength_whenConverted_thenResultIsReturnedAndSaved() {
        QuantityDTO length =
                new QuantityDTO(10.0, "FEET", "LENGTH");

        String result = facade.convert(length, "INCHES");

        assertEquals("Conversion Result: 120.0 INCHES", result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenLengthQuantities_whenAdded_thenResultIsReturnedAndSaved() {
        QuantityDTO first =
                new QuantityDTO(10.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(6.0, "INCHES", "LENGTH");

        String result = facade.add(first, second, "FEET");

        assertEquals("Addition Result: 10.5 FEET", result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenTemperature_whenConverted_thenResultIsReturnedAndSaved() {
        QuantityDTO temperature =
                new QuantityDTO(25.0, "CELSIUS", "TEMPERATURE");

        String result = facade.convert(temperature, "FAHRENHEIT");

        assertEquals("Conversion Result: 77.0 FAHRENHEIT", result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenUnsupportedTemperatureAddition_whenExecuted_thenErrorIsReturnedAndSaved() {
        QuantityDTO first =
                new QuantityDTO(25.0, "CELSIUS", "TEMPERATURE");

        QuantityDTO second =
                new QuantityDTO(30.0, "CELSIUS", "TEMPERATURE");

        String result = facade.add(first, second);

        assertTrue(result.startsWith("Error:"));
        assertEquals(1, repository.getTotalCount());
    }
}
