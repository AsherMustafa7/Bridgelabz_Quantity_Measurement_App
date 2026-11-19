
package com.asher.quantitymeasurement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
// import IMeasurable


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
     * Defines the arithmetic operation performed on base-unit values.
     */
    private enum ArithmeticOperation {

        ADD {
            @Override
            double compute(double first, double second) {
                return first + second;
            }
        },

        SUBTRACT {
            @Override
            double compute(double first, double second) {
                return first - second;
            }
        },

        DIVIDE {
            @Override
            double compute(double first, double second) {
                if (second == 0.0) {
                    throw new ArithmeticException("Cannot divide by zero quantity.");
                }
                return first / second;
            }
        };

        abstract double compute(double first, double second);
    }

    /**
     * Converts this quantity into the specified target unit.
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
     */
    public Quantity<U> add(Quantity<U> other) {
        return add(other, unit);
    }

    /**
     * Adds another quantity and returns the result in the specified unit.
     */
    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        unit.validateOperationSupport("addition");

        double resultInBaseUnit = performBaseArithmetic(
                other,
                targetUnit,
                true,
                ArithmeticOperation.ADD
        );

        double result = targetUnit.convertFromBaseUnit(resultInBaseUnit);

        return new Quantity<>(result, targetUnit);
    }

    /**
     * Subtracts another quantity and returns the result in this quantity's unit.
     */
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, unit);
    }

    /**
     * Subtracts another quantity and returns the result in the specified unit.
     * The result is rounded to two decimal places using HALF_UP.
     */
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        unit.validateOperationSupport("subtraction");

        double resultInBaseUnit = performBaseArithmetic(
                other,
                targetUnit,
                true,
                ArithmeticOperation.SUBTRACT
        );

        double result = targetUnit.convertFromBaseUnit(resultInBaseUnit);

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(
                    "Subtraction result exceeds the supported numeric range."
            );
        }

        double roundedResult = roundToTwoDecimals(result);

        return new Quantity<>(roundedResult, targetUnit);
    }

    /**
     * Divides this quantity by another quantity of the same category.
     * Returns a dimensionless scalar.
     */
    public double divide(Quantity<U> other) {
        unit.validateOperationSupport("division");

        return performBaseArithmetic(
                other,
                null,
                false,
                ArithmeticOperation.DIVIDE
        );
    }

    /**
     * Centralized arithmetic engine.
     *
     * Validates operands, converts both values into base units,
     * performs the requested operation, and returns the base-unit result.
     */
    private double performBaseArithmetic(
            Quantity<U> other,
            U targetUnit,
            boolean targetUnitRequired,
            ArithmeticOperation operation) {

        validateArithmeticOperands(other, targetUnit, targetUnitRequired);

        double firstInBaseUnit = unit.convertToBaseUnit(value);
        double secondInBaseUnit =
                other.unit.convertToBaseUnit(other.value);

        if (!Double.isFinite(firstInBaseUnit)
                || !Double.isFinite(secondInBaseUnit)) {

            if (operation == ArithmeticOperation.DIVIDE) {
                throw new ArithmeticException(
                        "Converted quantity exceeds the supported numeric range."
                );
            }

            throw new IllegalArgumentException(
                    "Arithmetic operand exceeds the supported numeric range."
            );
        }

        double result = operation.compute(
                firstInBaseUnit,
                secondInBaseUnit
        );

        if (!Double.isFinite(result)) {
            if (operation == ArithmeticOperation.DIVIDE) {
                throw new ArithmeticException(
                        "Division result exceeds the supported numeric range."
                );
            }

            String operationName =
                    operation == ArithmeticOperation.ADD
                            ? "Addition"
                            : "Subtraction";

            throw new IllegalArgumentException(
                    operationName + " result exceeds the supported numeric range."
            );
        }

        return result;
    }

    /**
     * Centralized validation for all arithmetic operations.
     */
    private void validateArithmeticOperands(
            Quantity<U> other,
            U targetUnit,
            boolean targetUnitRequired) {

        if (other == null) {
            throw new IllegalArgumentException(
                    "Second measurement cannot be null."
            );
        }

        if (!Double.isFinite(value) || !Double.isFinite(other.value)) {
            throw new IllegalArgumentException(
                    "Measurement values must be finite."
            );
        }

        validateSameCategory(other.unit);

        if (targetUnitRequired) {
            validateTargetUnit(targetUnit);
        }
    }

    /**
     * Validates that the target unit is present and belongs to this category.
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
     * Rounds a value to two decimal places using HALF_UP.
     */
    private double roundToTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
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
