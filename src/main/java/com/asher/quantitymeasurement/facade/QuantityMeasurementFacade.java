package com.asher.quantitymeasurement.facade;

import com.asher.quantitymeasurement.controller.QuantityMeasurementController;
import com.asher.quantitymeasurement.dto.QuantityDTO;

public class QuantityMeasurementFacade {

    private final QuantityMeasurementController controller;

    public QuantityMeasurementFacade(
            QuantityMeasurementController controller) {

        if (controller == null) {
            throw new IllegalArgumentException(
                    "Quantity measurement controller cannot be null.");
        }

        this.controller = controller;
    }

    public String convert(QuantityDTO quantity, String targetUnit) {
        return controller.convert(quantity, targetUnit);
    }

    public String compare(QuantityDTO first, QuantityDTO second) {
        return controller.compare(first, second);
    }

    public String add(QuantityDTO first, QuantityDTO second) {
        return controller.add(first, second);
    }

    public String add(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit) {

        return controller.add(first, second, targetUnit);
    }

    public String subtract(QuantityDTO first, QuantityDTO second) {
        return controller.subtract(first, second);
    }

    public String subtract(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit) {

        return controller.subtract(first, second, targetUnit);
    }

    public String divide(QuantityDTO first, QuantityDTO second) {
        return controller.divide(first, second);
    }
}