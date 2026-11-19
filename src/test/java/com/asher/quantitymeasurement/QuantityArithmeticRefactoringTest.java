
package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class QuantityArithmeticRefactoringTest {

    // -----------------------------------------
    // ADDITION THROUGH CENTRALIZED HELPER
    // -----------------------------------------

    @Test
    void shouldAddLengthQuantities() {
        Quantity<LengthUnit> result =
                new Quantity<>(1.0, LengthUnit.FEET)
                        .add(new Quantity<>(12.0, LengthUnit.INCHES));

        assertEquals(2.0, result.getValue(), 0.000001);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void shouldAddWeightQuantitiesUsingExplicitTarget() {
        Quantity<WeightUnit> result =
                new Quantity<>(10.0, WeightUnit.KILOGRAM)
                        .add(
                                new Quantity<>(5000.0, WeightUnit.GRAM),
                                WeightUnit.GRAM
                        );

        assertEquals(15000.0, result.getValue(), 0.001);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void shouldAddVolumeQuantities() {
        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(5.5, result.getValue(), 0.001);
    }

    // -----------------------------------------
    // SUBTRACTION THROUGH CENTRALIZED HELPER
    // -----------------------------------------

    @Test
    void shouldSubtractLengthQuantities() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCHES));

        assertEquals(9.5, result.getValue(), 0.001);
    }

    @Test
    void shouldSubtractWeightQuantities() {
        Quantity<WeightUnit> result =
                new Quantity<>(10.0, WeightUnit.KILOGRAM)
                        .subtract(new Quantity<>(5000.0, WeightUnit.GRAM));

        assertEquals(5.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSubtractVolumeUsingExplicitTarget() {
        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .subtract(
                                new Quantity<>(2.0, VolumeUnit.LITRE),
                                VolumeUnit.MILLILITRE
                        );

        assertEquals(3000.0, result.getValue(), 0.001);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void shouldRoundSubtractionResultToTwoDecimalPlaces() {
        Quantity<LengthUnit> result =
                new Quantity<>(1.0, LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(0.123, LengthUnit.FEET)
                        );

        assertEquals(0.88, result.getValue(), 0.001);
    }

    @Test
    void shouldPreserveNegativeSubtractionResults() {
        Quantity<LengthUnit> result =
                new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(10.0, LengthUnit.FEET));

        assertEquals(-5.0, result.getValue(), 0.001);
    }

    // -----------------------------------------
    // DIVISION THROUGH CENTRALIZED HELPER
    // -----------------------------------------

    @Test
    void shouldDivideLengthQuantities() {
        double result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(5.0, result, 0.000001);
    }

    @Test
    void shouldDivideDifferentLengthUnits() {
        double result =
                new Quantity<>(24.0, LengthUnit.INCHES)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(1.0, result, 0.000001);
    }

    @Test
    void shouldDivideWeightQuantities() {
        double result =
                new Quantity<>(2000.0, WeightUnit.GRAM)
                        .divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));

        assertEquals(2.0, result, 0.000001);
    }

    @Test
    void shouldDivideVolumeQuantities() {
        double result =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .divide(new Quantity<>(1.0, VolumeUnit.LITRE));

        assertEquals(1.0, result, 0.000001);
    }

    @Test
    void shouldPreserveDivisionPrecision() {
        double result =
                new Quantity<>(1.0, LengthUnit.FEET)
                        .divide(new Quantity<>(3.0, LengthUnit.FEET));

        assertEquals(1.0 / 3.0, result, 0.0000000001);
    }

    // -----------------------------------------
    // VALIDATION CONSISTENCY
    // -----------------------------------------

    @Test
    void shouldRejectNullOperandAcrossAllOperations() {
        Quantity<LengthUnit> quantity =
                new Quantity<>(10.0, LengthUnit.FEET);

        IllegalArgumentException addException =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> quantity.add(null)
                );

        IllegalArgumentException subtractException =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> quantity.subtract(null)
                );

        IllegalArgumentException divideException =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> quantity.divide(null)
                );

        assertEquals(addException.getMessage(), subtractException.getMessage());
        assertEquals(addException.getMessage(), divideException.getMessage());
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void shouldRejectCrossCategoryOperandsAcrossAllOperations() {
        Quantity length =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity weight =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertThrows(
                IllegalArgumentException.class,
                () -> length.add(weight)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> length.subtract(weight)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> length.divide(weight)
        );
    }

    @Test
    void shouldRejectNullTargetUnitForAdditionAndSubtraction() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(5.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> first.add(second, null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> first.subtract(second, null)
        );
    }

    @Test
    void shouldRejectDivisionByZero() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> zero =
                new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(
                ArithmeticException.class,
                () -> first.divide(zero)
        );
    }

    // -----------------------------------------
    // IMMUTABILITY
    // -----------------------------------------

    @Test
    void shouldPreserveOriginalQuantitiesAfterAllOperations() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(5.0, LengthUnit.FEET);

        first.add(second);
        first.subtract(second);
        first.divide(second);

        assertEquals(10.0, first.getValue(), 0.001);
        assertEquals(5.0, second.getValue(), 0.001);
    }

    // -----------------------------------------
    // BACKWARD COMPATIBILITY
    // -----------------------------------------

    @Test
    void shouldSupportChainedArithmeticOperations() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .add(new Quantity<>(5.0, LengthUnit.FEET))
                        .subtract(new Quantity<>(3.0, LengthUnit.FEET));

        double ratio = result.divide(
                new Quantity<>(4.0, LengthUnit.FEET)
        );

        assertEquals(3.0, ratio, 0.001);
    }

    @Test
    void shouldPreserveAdditionWithoutRounding() {
        Quantity<LengthUnit> result =
                new Quantity<>(1.234, LengthUnit.FEET)
                        .add(new Quantity<>(0.001, LengthUnit.FEET));

        assertEquals(1.235, result.getValue(), 0.000001);
    }

    // -----------------------------------------
    // PRIVATE HELPER STRUCTURE
    // -----------------------------------------

    @Test
    void shouldKeepPerformBaseArithmeticPrivate() throws Exception {
        Method method = Quantity.class.getDeclaredMethod(
                "performBaseArithmetic",
                Quantity.class,
                IMeasurable.class,
                boolean.class,
                getArithmeticOperationClass()
        );

        assertTrue(Modifier.isPrivate(method.getModifiers()));
    }

    @Test
    void shouldKeepValidationHelperPrivate() throws Exception {
        Method method = Quantity.class.getDeclaredMethod(
                "validateArithmeticOperands",
                Quantity.class,
                IMeasurable.class,
                boolean.class
        );

        assertTrue(Modifier.isPrivate(method.getModifiers()));
    }

    @Test
    void shouldContainArithmeticOperationEnum() {
        Class<?>[] nestedClasses = Quantity.class.getDeclaredClasses();

        boolean enumFound = false;

        for (Class<?> nestedClass : nestedClasses) {
            if (nestedClass.getSimpleName().equals("ArithmeticOperation")
                    && nestedClass.isEnum()) {
                enumFound = true;
                break;
            }
        }

        assertTrue(enumFound);
    }

    private Class<?> getArithmeticOperationClass() {
        for (Class<?> nestedClass : Quantity.class.getDeclaredClasses()) {
            if (nestedClass.getSimpleName().equals("ArithmeticOperation")) {
                return nestedClass;
            }
        }

        throw new IllegalStateException("ArithmeticOperation enum not found.");
    }
}
