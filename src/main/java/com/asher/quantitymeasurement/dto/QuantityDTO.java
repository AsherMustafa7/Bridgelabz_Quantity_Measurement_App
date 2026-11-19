
package com.asher.quantitymeasurement.dto;

public class QuantityDTO {

    private double value;
    private String unit;
    private String measurementType;

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public interface IMeasurableUnit {
        String getUnitName();

        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS, METERS;

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "LENGTH";
        }
    }

    public enum WeightUnit implements IMeasurableUnit {
        GRAM, KILOGRAM, POUND, OUNCES;

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "WEIGHT";
        }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "VOLUME";
        }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "TEMPERATURE";
        }
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
