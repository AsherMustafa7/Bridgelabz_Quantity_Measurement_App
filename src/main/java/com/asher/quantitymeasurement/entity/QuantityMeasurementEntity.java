
package com.asher.quantitymeasurement.entity;

import java.io.Serializable;

import com.asher.quantitymeasurement.IMeasurable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String resultString;

    private boolean isError;
    private String errorMessage;

    public QuantityMeasurementEntity(
            QuantityModel<? extends IMeasurable> thisQuantity,
            QuantityModel<? extends IMeasurable> thatQuantity,
            String operation) {

        if (thisQuantity != null) {
            this.thisValue = thisQuantity.getValue();
            this.thisUnit = thisQuantity.getUnit().getUnitName();
            this.thisMeasurementType =
                    IMeasurable.getMeasurementType(this.thisUnit);
        }

        if (thatQuantity != null) {
            this.thatValue = thatQuantity.getValue();
            this.thatUnit = thatQuantity.getUnit().getUnitName();
            this.thatMeasurementType =
                    IMeasurable.getMeasurementType(this.thatUnit);
        }

        this.operation = operation;
    }

    public QuantityMeasurementEntity(
            QuantityModel<? extends IMeasurable> thisQuantity,
            QuantityModel<? extends IMeasurable> thatQuantity,
            String operation,
            String result) {

        this(thisQuantity, thatQuantity, operation);
        this.resultString = result;
    }

    public QuantityMeasurementEntity(
            QuantityModel<? extends IMeasurable> thisQuantity,
            QuantityModel<? extends IMeasurable> thatQuantity,
            String operation,
            QuantityModel<? extends IMeasurable> result) {

        this(thisQuantity, thatQuantity, operation);

        if (result != null) {
            this.resultValue = result.getValue();
            this.resultUnit = result.getUnit().getUnitName();
            this.resultMeasurementType =
                    IMeasurable.getMeasurementType(this.resultUnit);
        }
    }

    public QuantityMeasurementEntity(
            QuantityModel<? extends IMeasurable> thisQuantity,
            QuantityModel<? extends IMeasurable> thatQuantity,
            String operation,
            String errorMessage,
            boolean isError) {

        this(thisQuantity, thatQuantity, operation);
        this.errorMessage = errorMessage;
        this.isError = isError;
    }

    public double getThisValue() {
        return thisValue;
    }

    public String getThisUnit() {
        return thisUnit;
    }

    public String getThisMeasurementType() {
        return thisMeasurementType;
    }

    public double getThatValue() {
        return thatValue;
    }

    public String getThatUnit() {
        return thatUnit;
    }

    public String getThatMeasurementType() {
        return thatMeasurementType;
    }

    public String getOperation() {
        return operation;
    }

    public double getResultValue() {
        return resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public String getResultMeasurementType() {
        return resultMeasurementType;
    }

    public String getResultString() {
        return resultString;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
