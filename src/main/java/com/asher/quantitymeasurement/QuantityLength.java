
package com.asher.quantitymeasurement;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {

        if (unit == null) {
            throw new IllegalArgumentException(
                    "Length unit cannot be null"
            );
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }

    @Override
    public boolean equals(Object obj) {

        // Same reference
        if (this == obj) {
            return true;
        }

        // Null or different class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        QuantityLength other = (QuantityLength) obj;

        // Convert both measurements to feet
        return Double.compare(
                this.convertToBaseUnit(),
                other.convertToBaseUnit()
        ) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }
}
