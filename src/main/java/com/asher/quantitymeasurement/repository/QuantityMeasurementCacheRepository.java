
package com.asher.quantitymeasurement.repository;

import com.asher.quantitymeasurement.IMeasurable;
import com.asher.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private static final String FILE_NAME = "quantity-measurements.dat";

    private static QuantityMeasurementCacheRepository instance;

    private final List<QuantityMeasurementEntity> measurements;
    private final Path storagePath;

    // Application constructor: uses the default history file.
    private QuantityMeasurementCacheRepository() {
    this(Paths.get(FILE_NAME));
}

    // Package-private constructor: allows isolated repository tests.
    QuantityMeasurementCacheRepository(Path storagePath) {
        if (storagePath == null) {
            throw new IllegalArgumentException("Storage path cannot be null.");
        }

        this.storagePath = storagePath;
        this.measurements = new ArrayList<>();

        loadFromDisk();
    }

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }

        return instance;
    }

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        measurements.add(entity);

        try {
            persistToDisk(entity);
        } catch (IllegalStateException exception) {
            measurements.remove(measurements.size() - 1);
            throw exception;
        }
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>(measurements);
    }

    @Override
    public synchronized List<QuantityMeasurementEntity>
    getMeasurementsByOperation(String operation) {

        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Operation cannot be empty.");
        }

        return measurements.stream()
                .filter(entity ->
                        operation.equalsIgnoreCase(entity.getOperation()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<QuantityMeasurementEntity>
    getMeasurementsByType(String measurementType) {

        if (measurementType == null || measurementType.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Measurement type cannot be empty.");
        }

        return measurements.stream()
                .filter(entity ->
                        measurementType.equalsIgnoreCase(
                                entity.getThisMeasurementType())
                        || measurementType.equalsIgnoreCase(
                                entity.getThatMeasurementType())
                        || measurementType.equalsIgnoreCase(
                                entity.getResultMeasurementType()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized long getTotalCount() {
        return measurements.size();
    }

    @Override
    public synchronized void deleteAll() {

        try {
            Files.deleteIfExists(storagePath);
            measurements.clear();
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to delete measurement history file.",
                    exception);
        }
    }

    private void loadFromDisk() {

        if (!Files.exists(storagePath) || getFileSize() == 0) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             Files.newInputStream(storagePath))) {

            while (true) {
                Object object = input.readObject();

                if (object instanceof QuantityMeasurementEntity) {
                    measurements.add(
                            (QuantityMeasurementEntity) object);
                }
            }

        } catch (EOFException ignored) {
            // End of serialized file.
        } catch (IOException | ClassNotFoundException exception) {
            throw new IllegalStateException(
                    "Unable to load measurement history.",
                    exception);
        }
    }

    private long getFileSize() {
        try {
            return Files.size(storagePath);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to read measurement history file size.",
                    exception);
        }
    }

    private void persistToDisk(QuantityMeasurementEntity entity) {

        try {
            Path parent = storagePath.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            boolean append =
                    Files.exists(storagePath) && getFileSize() > 0;

            try (FileOutputStream fileOutput =
                         new FileOutputStream(storagePath.toFile(), true)) {

                ObjectOutputStream output = append
                        ? new AppendableObjectOutputStream(fileOutput)
                        : new ObjectOutputStream(fileOutput);

                try (ObjectOutputStream stream = output) {
                    stream.writeObject(entity);
                    stream.flush();
                }
            }

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to persist measurement history.",
                    exception);
        }
    }
}
