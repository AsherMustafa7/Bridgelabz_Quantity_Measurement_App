
package com.asher.quantitymeasurement.integration;

import com.asher.quantitymeasurement.dto.QuantityDTO;
import com.asher.quantitymeasurement.facade.QuantityMeasurementFacade;
import com.asher.quantitymeasurement.factory.QuantityMeasurementFactory;
import com.asher.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.asher.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.asher.quantitymeasurement.util.ApplicationConfig;
import com.asher.quantitymeasurement.util.ConnectionPool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementDatabaseIntegrationTest {

    private ConnectionPool connectionPool;
    private IQuantityMeasurementRepository repository;
    private QuantityMeasurementFacade facade;

    @BeforeEach
    void setUp() {
        System.setProperty(
                "db.url",
                "jdbc:h2:mem:integrationtest;DB_CLOSE_DELAY=-1;MODE=MySQL");

        System.setProperty(
                "db.driver",
                "org.h2.Driver");

        connectionPool =
                new ConnectionPool(new ApplicationConfig());

        repository =
                new QuantityMeasurementDatabaseRepository(
                        connectionPool);

        repository.deleteAll();

        facade =
                QuantityMeasurementFactory.createFacade(repository);
    }

    @AfterEach
    void tearDown() {
        if (repository != null) {
            repository.deleteAll();
        }

        if (connectionPool != null) {
            connectionPool.close();
        }

        System.clearProperty("db.url");
        System.clearProperty("db.driver");
    }

    @Test
    void givenLength_whenConverted_thenResultIsPersisted() {
        QuantityDTO length =
                new QuantityDTO(10.0, "FEET", "LENGTH");

        String result =
                facade.convert(length, "INCHES");

        assertEquals(
                "Conversion Result: 120.0 INCHES",
                result);

        assertEquals(1, repository.getTotalCount());

        assertEquals(
                "CONVERT",
                repository.getAllMeasurements()
                        .get(0)
                        .getOperation());
    }

    @Test
    void givenLengthQuantities_whenAdded_thenResultIsPersisted() {
        QuantityDTO first =
                new QuantityDTO(10.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(6.0, "INCHES", "LENGTH");

        String result =
                facade.add(first, second, "FEET");

        assertEquals(
                "Addition Result: 10.5 FEET",
                result);

        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenTemperature_whenConverted_thenResultIsPersisted() {
        QuantityDTO temperature =
                new QuantityDTO(
                        25.0, "CELSIUS", "TEMPERATURE");

        String result =
                facade.convert(temperature, "FAHRENHEIT");

        assertEquals(
                "Conversion Result: 77.0 FAHRENHEIT",
                result);

        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenUnsupportedOperation_whenExecuted_thenErrorIsPersisted() {
        QuantityDTO first =
                new QuantityDTO(
                        25.0, "CELSIUS", "TEMPERATURE");

        QuantityDTO second =
                new QuantityDTO(
                        30.0, "CELSIUS", "TEMPERATURE");

        String result =
                facade.add(first, second);

        assertTrue(result.startsWith("Error:"));
        assertEquals(1, repository.getTotalCount());

        assertTrue(
                repository.getAllMeasurements()
                        .get(0)
                        .isError());
    }

    @Test
    void givenSavedMeasurements_whenQueriedByType_thenMatchingRecordsReturn() {
        facade.convert(
                new QuantityDTO(10.0, "FEET", "LENGTH"),
                "INCHES");

        facade.convert(
                new QuantityDTO(5.0, "KILOGRAM", "WEIGHT"),
                "GRAM");

        assertEquals(
                1,
                repository.getMeasurementsByType("LENGTH").size());

        assertEquals(
                1,
                repository.getMeasurementsByType("WEIGHT").size());
    }

    @Test
    void givenDatabaseRepository_whenStatisticsRequested_thenPoolIsAvailable() {
        assertNotNull(repository.getPoolStatistics());
    }
}
