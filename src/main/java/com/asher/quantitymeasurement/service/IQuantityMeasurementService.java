package com.asher.quantitymeasurement.service;

import com.asher.quantitymeasurement.dto.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO convert(QuantityDTO quantity, String targetUnit);

    boolean compare(QuantityDTO first, QuantityDTO second);

    QuantityDTO add(QuantityDTO first, QuantityDTO second);

    QuantityDTO add(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit
    );

    QuantityDTO subtract(QuantityDTO first, QuantityDTO second);

    QuantityDTO subtract(
            QuantityDTO first,
            QuantityDTO second,
            String targetUnit
    );

    double divide(QuantityDTO first, QuantityDTO second);
}