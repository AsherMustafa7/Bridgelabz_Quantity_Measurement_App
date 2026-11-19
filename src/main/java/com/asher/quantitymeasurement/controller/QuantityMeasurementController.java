
package com.asher.quantitymeasurement.controller;

import com.asher.quantitymeasurement.dto.QuantityDTO;
import com.asher.quantitymeasurement.exception.QuantityMeasurementException;
import com.asher.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService quantityService;

    public QuantityMeasurementController(
            IQuantityMeasurementService quantityService) {

        if (quantityService == null) {
            throw new IllegalArgumentException(
                    "Quantity measurement service cannot be null.");
        }

        this.quantityService = quantityService;
    }

    public String convert(QuantityDTO quantity, String targetUnit) {
        try {
            QuantityDTO result =
                    quantityService.convert(quantity, targetUnit);

            return formatResult("Conversion", result);

        } catch (QuantityMeasurementException exception) {
            return formatError(exception);
        }
    }

    public String compare(QuantityDTO first, QuantityDTO second) {
        try {
            boolean result = quantityService.compare(first, second);

            return "Comparison Result: " +
                    (result ? "Equal" : "Not Equal");

        } catch (QuantityMeasurementException exception) {
            return formatError(exception);
        }
    }

    public String add(QuantityDTO first, QuantityDTO second) {
        try {
            QuantityDTO result = quantityService.add(first, second);

            return formatResult("Addition", result);

        } catch (QuantityMeasurementException exception) {
            return formatError(exception);
        }
    }

    public String add(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit) {

        try {
            QuantityDTO result =
                    quantityService.add(first, second, targetUnit);

            return formatResult("Addition", result);

        } catch (QuantityMeasurementException exception) {
            return formatError(exception);
        }
    }

    public String subtract(QuantityDTO first, QuantityDTO second) {
        try {
            QuantityDTO result =
                    quantityService.subtract(first, second);

            return formatResult("Subtraction", result);

        } catch (QuantityMeasurementException exception) {
            return formatError(exception);
        }
    }

    public String subtract(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit) {

        try {
            QuantityDTO result =
                    quantityService.subtract(first, second, targetUnit);

            return formatResult("Subtraction", result);

        } catch (QuantityMeasurementException exception) {
            return formatError(exception);
        }
    }

    public String divide(QuantityDTO first, QuantityDTO second) {
        try {
            double result = quantityService.divide(first, second);

            return "Division Result: " + result;

        } catch (QuantityMeasurementException exception) {
            return formatError(exception);
        }
    }

    private String formatResult(String operation, QuantityDTO result) {
        return operation + " Result: "
                + result.getValue() + " "
                + result.getUnit();
    }

    private String formatError(QuantityMeasurementException exception) {
        return "Error: " + exception.getMessage();
    }
}
