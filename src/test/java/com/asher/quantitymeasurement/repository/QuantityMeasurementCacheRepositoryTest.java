
package com.asher.quantitymeasurement.repository;

import com.asher.quantitymeasurement.LengthUnit;
import com.asher.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.asher.quantitymeasurement.entity.QuantityModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementCacheRepositoryTest {

    @TempDir
    Path tempDir;

    private QuantityMeasurementCacheRepository repository;

    @BeforeEach
    void setUp() {
        repository = new QuantityMeasurementCacheRepository(
                tempDir.resolve("test-measurements.dat"));
    }

    private QuantityMeasurementEntity createEntity(
            double firstValue,
            LengthUnit firstUnit,
            double secondValue,
            LengthUnit secondUnit,
            String operation) {

        QuantityModel<LengthUnit> first =
                new QuantityModel<>(firstValue, firstUnit);

        QuantityModel<LengthUnit> second =
                new QuantityModel<>(secondValue, secondUnit);

        return new QuantityMeasurementEntity(
                first, second, operation);
    }

    @Test
    void givenRepository_whenGettingInstance_thenReturnSameSingleton() {

        QuantityMeasurementCacheRepository first =
                QuantityMeasurementCacheRepository.getInstance();

        QuantityMeasurementCacheRepository second =
                QuantityMeasurementCacheRepository.getInstance();

        assertSame(first, second);
    }

    @Test
    void givenValidEntity_whenSaving_thenIncreaseRecordCount() {

        QuantityMeasurementEntity entity =
                createEntity(10, LengthUnit.FEET,
                        5, LengthUnit.INCHES, "ADD");

        repository.save(entity);

        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void givenSavedEntities_whenGettingAll_thenReturnAllRecords() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        repository.save(createEntity(
                20, LengthUnit.FEET,
                10, LengthUnit.INCHES, "SUBTRACT"));

        List<QuantityMeasurementEntity> records =
                repository.getAllMeasurements();

        assertEquals(2, records.size());
    }

    @Test
    void givenSavedEntity_whenGettingByOperation_thenReturnMatchingRecords() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        repository.save(createEntity(
                20, LengthUnit.FEET,
                10, LengthUnit.INCHES, "SUBTRACT"));

        repository.save(createEntity(
                30, LengthUnit.FEET,
                15, LengthUnit.INCHES, "ADD"));

        List<QuantityMeasurementEntity> results =
                repository.getMeasurementsByOperation("ADD");

        assertEquals(2, results.size());
    }

    @Test
    void givenSavedEntity_whenGettingByMeasurementType_thenReturnMatchingRecords() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        List<QuantityMeasurementEntity> results =
                repository.getMeasurementsByType("LENGTH");

        assertEquals(1, results.size());
    }

    @Test
    void givenSavedEntity_whenGettingByOperation_thenIgnoreCase() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        assertEquals(
                1,
                repository.getMeasurementsByOperation("add").size());
    }

    @Test
    void givenNoRecords_whenGettingAll_thenReturnEmptyList() {

        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    void givenSavedEntities_whenDeletingAll_thenClearRecords() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    void givenNullEntity_whenSaving_thenThrowException() {

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null));
    }

    @Test
    void givenNullOperation_whenFiltering_thenThrowException() {

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.getMeasurementsByOperation(null));
    }

    @Test
    void givenEmptyMeasurementType_whenFiltering_thenThrowException() {

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.getMeasurementsByType(" "));
    }

    @Test
    void givenSavedEntity_whenCreatingNewRepository_thenLoadPersistedRecords() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        QuantityMeasurementCacheRepository newRepository =
                new QuantityMeasurementCacheRepository(
                        tempDir.resolve("test-measurements.dat"));

        assertEquals(1, newRepository.getTotalCount());

        QuantityMeasurementEntity loaded =
                newRepository.getAllMeasurements().get(0);

        assertEquals("ADD", loaded.getOperation());
        assertEquals("LENGTH", loaded.getThisMeasurementType());
        assertEquals("FEET", loaded.getThisUnit());
    }

    @Test
    void givenMultipleSavedEntities_whenReloading_thenLoadEveryRecord() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        repository.save(createEntity(
                20, LengthUnit.FEET,
                10, LengthUnit.INCHES, "SUBTRACT"));

        QuantityMeasurementCacheRepository newRepository =
                new QuantityMeasurementCacheRepository(
                        tempDir.resolve("test-measurements.dat"));

        assertEquals(2, newRepository.getTotalCount());
    }

    @Test
    void givenReturnedRecords_whenModifyingList_thenRepositoryRemainsUnchanged() {

        repository.save(createEntity(
                10, LengthUnit.FEET,
                5, LengthUnit.INCHES, "ADD"));

        List<QuantityMeasurementEntity> records =
                repository.getAllMeasurements();

        records.clear();

        assertEquals(1, repository.getTotalCount());
    }
}
