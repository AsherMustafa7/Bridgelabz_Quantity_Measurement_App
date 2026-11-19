package com.asher.quantitymeasurement;

/**
 * Defines the common conversion contract for measurable units.
 */
public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();
}