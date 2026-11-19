
package com.asher.quantitymeasurement.service;

import com.asher.quantitymeasurement.IMeasurable;
import com.asher.quantitymeasurement.Quantity;
import com.asher.quantitymeasurement.dto.QuantityDTO;
import com.asher.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.asher.quantitymeasurement.entity.QuantityModel;
import com.asher.quantitymeasurement.exception.QuantityMeasurementException;
import com.asher.quantitymeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(
            IQuantityMeasurementRepository repository) {

        if (repository == null) {
            throw new IllegalArgumentException(
                    "Repository cannot be null."
            );
        }

        this.repository = repository;
    }

    // CONVERSION

    @Override
    public QuantityDTO convert(QuantityDTO quantity, String targetUnit) {

        QuantityModel<? extends IMeasurable> sourceModel =
                resolveModelSafely(quantity);

        final QuantityDTO result;
        final QuantityMeasurementEntity entity;

        try {
            Quantity source = resolveQuantity(quantity);

            IMeasurable target = resolveTargetUnit(
                    quantity.getMeasurementType(),
                    targetUnit
            );

            Quantity converted = source.convertTo(target);

            result = toDTO(converted);

            entity = new QuantityMeasurementEntity(
                    sourceModel,
                    null,
                    "CONVERT",
                    toModel(converted)
            );

        } catch (RuntimeException exception) {
            throw handleFailure(
                    "CONVERT",
                    sourceModel,
                    null,
                    exception
            );
        }

        saveSuccessfulOperation(entity, "CONVERT");

        return result;
    }

    // COMPARISON

    @Override
    public boolean compare(QuantityDTO first, QuantityDTO second) {

        QuantityModel<? extends IMeasurable> firstModel =
                resolveModelSafely(first);

        QuantityModel<? extends IMeasurable> secondModel =
                resolveModelSafely(second);

        final boolean result;

        try {
            Quantity firstQuantity = resolveQuantity(first);
            Quantity secondQuantity = resolveQuantity(second);

            result = firstQuantity.equals(secondQuantity);

        } catch (RuntimeException exception) {
            throw handleFailure(
                    "COMPARE",
                    firstModel,
                    secondModel,
                    exception
            );
        }

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "COMPARE",
                        Boolean.toString(result)
                );

        saveSuccessfulOperation(entity, "COMPARE");

        return result;
    }

    // ADDITION

    @Override
    public QuantityDTO add(
            QuantityDTO first,
            QuantityDTO second) {

        return add(
                first,
                second,
                first == null ? null : first.getUnit()
        );
    }

    @Override
    public QuantityDTO add(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit) {

        return performQuantityOperation(
                first,
                second,
                targetUnit,
                "ADD"
        );
    }

    // SUBTRACTION

    @Override
    public QuantityDTO subtract(
            QuantityDTO first,
            QuantityDTO second) {

        return subtract(
                first,
                second,
                first == null ? null : first.getUnit()
        );
    }

    @Override
    public QuantityDTO subtract(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit) {

        return performQuantityOperation(
                first,
                second,
                targetUnit,
                "SUBTRACT"
        );
    }

    // DIVISION

    @Override
    public double divide(
            QuantityDTO first,
            QuantityDTO second) {

        QuantityModel<? extends IMeasurable> firstModel =
                resolveModelSafely(first);

        QuantityModel<? extends IMeasurable> secondModel =
                resolveModelSafely(second);

        final double result;

        try {
            Quantity firstQuantity = resolveQuantity(first);
            Quantity secondQuantity = resolveQuantity(second);

            result = firstQuantity.divide(secondQuantity);

        } catch (RuntimeException exception) {
            throw handleFailure(
                    "DIVIDE",
                    firstModel,
                    secondModel,
                    exception
            );
        }

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "DIVIDE",
                        Double.toString(result)
                );

        saveSuccessfulOperation(entity, "DIVIDE");

        return result;
    }

    // COMMON ADDITION AND SUBTRACTION LOGIC

    private QuantityDTO performQuantityOperation(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit,
            String operation) {

        QuantityModel<? extends IMeasurable> firstModel =
                resolveModelSafely(first);

        QuantityModel<? extends IMeasurable> secondModel =
                resolveModelSafely(second);

        final Quantity result;

        try {
            Quantity firstQuantity = resolveQuantity(first);
            Quantity secondQuantity = resolveQuantity(second);

            IMeasurable target = resolveTargetUnit(
                    first.getMeasurementType(),
                    targetUnit
            );

            if ("ADD".equals(operation)) {
                result = firstQuantity.add(secondQuantity, target);
            } else {
                result = firstQuantity.subtract(secondQuantity, target);
            }

        } catch (RuntimeException exception) {
            throw handleFailure(
                    operation,
                    firstModel,
                    secondModel,
                    exception
            );
        }

        QuantityDTO resultDTO = toDTO(result);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        operation,
                        toModel(result)
                );

        saveSuccessfulOperation(entity, operation);

        return resultDTO;
    }

    // DTO TO QUANTITY CONVERSION

    private Quantity resolveQuantity(QuantityDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Quantity cannot be null."
            );
        }

        if (dto.getMeasurementType() == null
                || dto.getMeasurementType().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Measurement type cannot be empty."
            );
        }

        if (dto.getUnit() == null
                || dto.getUnit().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Unit cannot be empty."
            );
        }

        IMeasurable unit = IMeasurable.getUnit(
                dto.getMeasurementType(),
                dto.getUnit()
        );

        String actualType =
                IMeasurable.getMeasurementType(dto.getUnit());

        if (!actualType.equalsIgnoreCase(
                dto.getMeasurementType().trim())) {

            throw new IllegalArgumentException(
                    "Unit does not match the supplied measurement type."
            );
        }

        return new Quantity(dto.getValue(), unit);
    }

    // TARGET UNIT RESOLUTION

    private IMeasurable resolveTargetUnit(
            String measurementType,
            String targetUnit) {

        return IMeasurable.getUnit(
                measurementType,
                targetUnit
        );
    }

    // QUANTITY TO DTO CONVERSION

    private QuantityDTO toDTO(Quantity quantity) {

        return new QuantityDTO(
                quantity.getValue(),
                quantity.getUnit().getUnitName(),
                IMeasurable.getMeasurementType(
                        quantity.getUnit().getUnitName()
                )
        );
    }

    // QUANTITY TO MODEL CONVERSION

    private QuantityModel<? extends IMeasurable> toModel(
            Quantity quantity) {

        return new QuantityModel<>(
                quantity.getValue(),
                (IMeasurable) quantity.getUnit()
        );
    }

    // SAFELY RESOLVE INPUT FOR HISTORY RECORDING

    private QuantityModel<? extends IMeasurable> resolveModelSafely(
            QuantityDTO dto) {

        try {
            return toModel(resolveQuantity(dto));

        } catch (RuntimeException ignored) {
            return null;
        }
    }

    // SAVE SUCCESSFUL OPERATIONS

    private void saveSuccessfulOperation(
            QuantityMeasurementEntity entity,
            String operation) {

        try {
            repository.save(entity);

        } catch (RuntimeException exception) {
            throw new QuantityMeasurementException(
                    "Unable to save successful "
                            + operation + " operation.",
                    exception
            );
        }
    }

    // HANDLE FAILED OPERATIONS

    private QuantityMeasurementException handleFailure(
            String operation,
            QuantityModel<? extends IMeasurable> first,
            QuantityModel<? extends IMeasurable> second,
            RuntimeException originalException) {

        QuantityMeasurementException exception =
                new QuantityMeasurementException(
                        operation + " operation failed: "
                                + originalException.getMessage(),
                        originalException
                );

        try {
            repository.save(
                    new QuantityMeasurementEntity(
                            first,
                            second,
                            operation,
                            exception.getMessage(),
                            true
                    )
            );

        } catch (RuntimeException persistenceException) {
            exception.addSuppressed(persistenceException);
        }

        return exception;
    }
}
