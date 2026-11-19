
package com.asher.quantitymeasurement.repository;

import com.asher.quantitymeasurement.IMeasurable;
import com.asher.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.asher.quantitymeasurement.entity.QuantityModel;
import com.asher.quantitymeasurement.exception.DatabaseException;
import com.asher.quantitymeasurement.util.ApplicationConfig;
import com.asher.quantitymeasurement.util.ConnectionPool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementDatabaseRepositoryTest {

    private ConnectionPool connectionPool;
    private QuantityMeasurementDatabaseRepository repository;

    @BeforeEach
    void setUp() {
        System.setProperty(
                "db.url",
                "jdbc:h2:mem:repositorytest;MODE=MySQL"
        );

        System.setProperty("db.driver", "org.h2.Driver");

        connectionPool = new ConnectionPool(new ApplicationConfig());

        repository = new QuantityMeasurementDatabaseRepository(
                connectionPool
        );

        repository.deleteAll();
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

    private QuantityMeasurementEntity createEntity(
            String operation,
            String unit,
            String measurementType) {

        IMeasurable measurable =
                IMeasurable.getUnit(measurementType, unit);

        QuantityModel<IMeasurable> first =
                new QuantityModel<>(10.0, measurable);

        QuantityModel<IMeasurable> second =
                new QuantityModel<>(5.0, measurable);

        QuantityModel<IMeasurable> result =
                new QuantityModel<>(15.0, measurable);

        return new QuantityMeasurementEntity(
                first, second, operation, result
        );
    }

    @Test
    void givenValidEntity_whenSaved_thenEntityIsPersisted() {
        repository.save(
                createEntity("ADD", "FEET", "LENGTH")
        );

        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenMultipleEntities_whenRetrieved_thenAllAreReturned() {
        repository.save(
                createEntity("ADD", "FEET", "LENGTH")
        );

        repository.save(
                createEntity("CONVERT", "KILOGRAM", "WEIGHT")
        );

        List<QuantityMeasurementEntity> result =
                repository.getAllMeasurements();

        assertEquals(2, result.size());
        assertEquals("ADD", result.get(0).getOperation());
        assertEquals("CONVERT", result.get(1).getOperation());
    }

    @Test
    void givenDifferentOperations_whenFiltered_thenOnlyMatchingRecordsReturn() {
        repository.save(
                createEntity("ADD", "FEET", "LENGTH")
        );

        repository.save(
                createEntity("CONVERT", "FEET", "LENGTH")
        );

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation("ADD");

        assertEquals(1, result.size());
        assertEquals("ADD", result.get(0).getOperation());
    }

    @Test
    void givenDifferentMeasurementTypes_whenFiltered_thenMatchingRecordsReturn() {
        repository.save(
                createEntity("ADD", "FEET", "LENGTH")
        );

        repository.save(
                createEntity("ADD", "KILOGRAM", "WEIGHT")
        );

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByType("WEIGHT");

        assertEquals(1, result.size());
        assertEquals(
                "WEIGHT",
                result.get(0).getThisMeasurementType()
        );
    }

    @Test
    void givenSavedRecords_whenCountRequested_thenCorrectCountReturns() {
        repository.save(
                createEntity("ADD", "FEET", "LENGTH")
        );

        repository.save(
                createEntity("DIVIDE", "KILOGRAM", "WEIGHT")
        );

        assertEquals(2, repository.getTotalCount());
    }

    @Test
    void givenSavedRecords_whenDeleteAll_thenRepositoryBecomesEmpty() {
        repository.save(
                createEntity("ADD", "FEET", "LENGTH")
        );

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    void givenNullEntity_whenSaved_thenExceptionIsThrown() {
        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null)
        );
    }

    @Test
    void givenEmptyOperation_whenFiltered_thenExceptionIsThrown() {
        assertThrows(
                IllegalArgumentException.class,
                () -> repository.getMeasurementsByOperation(" ")
        );
    }

    @Test
    void givenSqlInjectionInput_whenFiltered_thenInputIsTreatedAsValue() {
        repository.save(
                createEntity("ADD", "FEET", "LENGTH")
        );

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation(
                        "' OR '1'='1"
                );

        assertTrue(result.isEmpty());
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenRepository_whenPoolStatisticsRequested_thenStatisticsReturn() {
        String statistics = repository.getPoolStatistics();

        assertNotNull(statistics);
        assertTrue(statistics.contains("Active"));
        assertTrue(statistics.contains("Idle"));
    }

    @Test
    void givenDatabaseError_whenSaving_thenDatabaseExceptionIsAvailable() {
        DatabaseException exception =
                new DatabaseException("Database operation failed.");

        assertEquals(
                "Database operation failed.",
                exception.getMessage()
        );
    }

    @Test
    void givenDatabaseRepository_whenCreated_thenTablesAreAvailable() {
        assertNotNull(repository.getAllMeasurements());
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    void givenEntityWithResultString_whenSaved_thenResultStringIsRetrieved() {
        IMeasurable unit =
                IMeasurable.getUnit("LENGTH", "FEET");

        QuantityModel<IMeasurable> first =
                new QuantityModel<>(10.0, unit);

        QuantityModel<IMeasurable> second =
                new QuantityModel<>(10.0, unit);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        first, second, "COMPARE", "true"
                );

        repository.save(entity);

        QuantityMeasurementEntity saved =
                repository.getAllMeasurements().get(0);

        assertEquals("true", saved.getResultString());
        assertEquals("COMPARE", saved.getOperation());
    }
}
