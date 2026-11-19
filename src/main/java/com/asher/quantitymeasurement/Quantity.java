package com.asher.quantitymeasurement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents an immutable quantity of any measurable unit.
 *
 * @param <U> the unit type implementing IMeasurable
 */
public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Measurement unit cannot be null.");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Measurement value must be finite.");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    /**
     * Converts this quantity into the specified target unit.
     *
     * @param targetUnit target measurement unit
     * @return a new quantity expressed in the target unit
     * @throws IllegalArgumentException if targetUnit is null
     */
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        validateSameCategory(targetUnit);

        if (unit == targetUnit) {
            return new Quantity<>(value, targetUnit);
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(convertedValue, targetUnit);
    }

    /**
     * Adds another quantity and returns the result in this quantity's unit.
     *
     * @param other quantity to add
     * @return a new quantity in this quantity's unit
     */
    public Quantity<U> add(Quantity<U> other) {
        return add(other, unit);
    }

    /**
     * Adds another quantity and returns the result in the specified unit.
     *
     * @param other quantity to add
     * @param targetUnit unit for the result
     * @return a new quantity expressed in targetUnit
     */
    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateOther(other);
        validateTargetUnit(targetUnit);
        validateSameCategory(other.unit);

        double firstInBaseUnit = unit.convertToBaseUnit(value);
        double secondInBaseUnit =
                other.unit.convertToBaseUnit(other.value);

        double sumInBaseUnit = firstInBaseUnit + secondInBaseUnit;

        if (!Double.isFinite(sumInBaseUnit)) {
            throw new IllegalArgumentException(
                    "Addition result exceeds the supported numeric range."
            );
        }

        double result = targetUnit.convertFromBaseUnit(sumInBaseUnit);

        return new Quantity<>(result, targetUnit);
    }

    /**
     * Subtracts another quantity and returns the result in this quantity's unit.
     *
     * @param other quantity to subtract
     * @return a new quantity expressed in this quantity's unit
     * @throws IllegalArgumentException if other is null or belongs to another category
     */
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, unit);
    }

    /**
     * Subtracts another quantity and returns the result in the specified unit.
     * The converted result is rounded to two decimal places using HALF_UP.
     *
     * @param other quantity to subtract
     * @param targetUnit unit for the result
     * @return a new quantity expressed in targetUnit
     * @throws IllegalArgumentException if an argument is null, categories differ,
     *                                  or the result is outside the supported range
     */
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOther(other);
        validateTargetUnit(targetUnit);
        validateSameCategory(other.unit);

        double firstInBaseUnit = unit.convertToBaseUnit(value);
        double secondInBaseUnit =
                other.unit.convertToBaseUnit(other.value);

        double differenceInBaseUnit =
                firstInBaseUnit - secondInBaseUnit;

        if (!Double.isFinite(differenceInBaseUnit)) {
            throw new IllegalArgumentException(
                    "Subtraction result exceeds the supported numeric range."
            );
        }

        double result = targetUnit.convertFromBaseUnit(differenceInBaseUnit);

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(
                    "Subtraction result exceeds the supported numeric range."
            );
        }

        double roundedResult = BigDecimal.valueOf(result)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new Quantity<>(roundedResult, targetUnit);
    }

    /**
     * Divides this quantity by another quantity of the same measurement category.
     * Both quantities are converted to their common base unit before division.
     *
     * @param other divisor quantity
     * @return dimensionless ratio as a double
     * @throws IllegalArgumentException if other is null or belongs to another category
     * @throws ArithmeticException if the divisor is zero or the ratio is not finite
     */
    public double divide(Quantity<U> other) {
        validateOther(other);
        validateSameCategory(other.unit);

        double firstInBaseUnit = unit.convertToBaseUnit(value);
        double secondInBaseUnit =
                other.unit.convertToBaseUnit(other.value);

        if (!Double.isFinite(firstInBaseUnit)
                || !Double.isFinite(secondInBaseUnit)) {
            throw new ArithmeticException(
                    "Converted quantity exceeds the supported numeric range."
            );
        }

        if (secondInBaseUnit == 0.0) {
            throw new ArithmeticException("Cannot divide by zero quantity.");
        }

        double result = firstInBaseUnit / secondInBaseUnit;

        if (!Double.isFinite(result)) {
            throw new ArithmeticException(
                    "Division result exceeds the supported numeric range."
            );
        }

        return result;
    }

    /**
     * Validates that another quantity is present.
     */
    private void validateOther(Quantity<?> other) {
        if (other == null) {
            throw new IllegalArgumentException(
                    "Second measurement cannot be null."
            );
        }
    }

    /**
     * Validates that a target unit is present and belongs to this category.
     */
    private void validateTargetUnit(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        validateSameCategory(targetUnit);
    }

    /**
     * Validates that two units belong to the same measurement category.
     */
    private void validateSameCategory(IMeasurable otherUnit) {
        if (!unit.getClass().equals(otherUnit.getClass())) {
            throw new IllegalArgumentException(
                    "Operation between different measurement categories is not allowed."
            );
        }
    }

    /**
     * Converts this quantity into its category's base unit.
     */
    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Quantity<?> that = (Quantity<?>) object;

        if (!unit.getClass().equals(that.unit.getClass())) {
            return false;
        }

        return Double.compare(
                convertToBaseUnit(),
                that.convertToBaseUnit()
        ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                unit.getClass(),
                Double.hashCode(convertToBaseUnit())
        );
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}