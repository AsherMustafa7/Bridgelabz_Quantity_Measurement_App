
package com.asher.quantitymeasurement.repository;

import com.asher.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> getAllMeasurements();

    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

    long getTotalCount();

    void deleteAll();

    default String getPoolStatistics() {
        return "Cache repository: no connection pool.";
    }

    default void releasingResources() {
        // No external resources to release for an in-memory cache.
    }
}
