package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityArithmeticTest {

    // -----------------------------------------
    // SUBTRACTION - SAME UNIT
    // -----------------------------------------

    @Test
    void shouldSubtractSameUnitFeet() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(5.0, LengthUnit.FEET));

        assertEquals(5.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void shouldSubtractSameUnitLitres() {
        Quantity<VolumeUnit> result =
                new Quantity<>(10.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(3.0, VolumeUnit.LITRE));

        assertEquals(7.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSubtractSameUnitKilograms() {
        Quantity<WeightUnit> result =
                new Quantity<>(10.0, WeightUnit.KILOGRAM)
                        .subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM));

        assertEquals(5.0, result.getValue(), 0.001);
    }

    // -----------------------------------------
    // SUBTRACTION - DIFFERENT UNITS
    // -----------------------------------------

    @Test
    void shouldSubtractFeetAndInches() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCHES));

        assertEquals(9.5, result.getValue(), 0.001);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void shouldSubtractInchesAndFeet() {
        Quantity<LengthUnit> result =
                new Quantity<>(120.0, LengthUnit.INCHES)
                        .subtract(new Quantity<>(5.0, LengthUnit.FEET));

        assertEquals(60.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void shouldSubtractKilogramsAndGrams() {
        Quantity<WeightUnit> result =
                new Quantity<>(10.0, WeightUnit.KILOGRAM)
                        .subtract(new Quantity<>(5000.0, WeightUnit.GRAM));

        assertEquals(5.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSubtractLitresAndMillilitres() {
        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(4.5, result.getValue(), 0.001);
    }

    // -----------------------------------------
    // SUBTRACTION - EXPLICIT TARGET UNIT
    // -----------------------------------------

    @Test
    void shouldSubtractUsingExplicitFeetTarget() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(6.0, LengthUnit.INCHES),
                                LengthUnit.FEET
                        );

        assertEquals(9.5, result.getValue(), 0.001);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void shouldSubtractUsingExplicitInchesTarget() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(6.0, LengthUnit.INCHES),
                                LengthUnit.INCHES
                        );

        assertEquals(114.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void shouldSubtractUsingExplicitGramsTarget() {
        Quantity<WeightUnit> result =
                new Quantity<>(10.0, WeightUnit.KILOGRAM)
                        .subtract(
                                new Quantity<>(5.0, WeightUnit.KILOGRAM),
                                WeightUnit.GRAM
                        );

        assertEquals(5000.0, result.getValue(), 0.001);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void shouldSubtractUsingExplicitMillilitresTarget() {
        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .subtract(
                                new Quantity<>(2.0, VolumeUnit.LITRE),
                                VolumeUnit.MILLILITRE
                        );

        assertEquals(3000.0, result.getValue(), 0.001);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    // -----------------------------------------
    // SUBTRACTION - NEGATIVE, ZERO AND EDGE CASES
    // -----------------------------------------

    @Test
    void shouldReturnNegativeSubtractionResult() {
        Quantity<LengthUnit> result =
                new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(10.0, LengthUnit.FEET));

        assertEquals(-5.0, result.getValue(), 0.001);
    }

    @Test
    void shouldReturnZeroWhenSubtractingEquivalentQuantities() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(120.0, LengthUnit.INCHES));

        assertEquals(0.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSubtractZeroQuantity() {
        Quantity<LengthUnit> result =
                new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(0.0, LengthUnit.INCHES));

        assertEquals(5.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSubtractNegativeQuantity() {
        Quantity<LengthUnit> result =
                new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(-2.0, LengthUnit.FEET));

        assertEquals(7.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSupportLargeSubtractionValues() {
        Quantity<WeightUnit> result =
                new Quantity<>(1_000_000.0, WeightUnit.KILOGRAM)
                        .subtract(
                                new Quantity<>(500_000.0, WeightUnit.KILOGRAM)
                        );

        assertEquals(500_000.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSupportSmallSubtractionValues() {
        Quantity<LengthUnit> result =
                new Quantity<>(0.001, LengthUnit.FEET)
                        .subtract(new Quantity<>(0.0005, LengthUnit.FEET));

        assertEquals(0.00, result.getValue(), 0.001);
    }

    @Test
    void shouldRoundSubtractionResultToTwoDecimalPlaces() {
        Quantity<LengthUnit> result =
                new Quantity<>(1.0, LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(0.123, LengthUnit.FEET),
                                LengthUnit.FEET
                        );

        assertEquals(0.88, result.getValue(), 0.001);
    }

    @Test
    void shouldNotChangeOriginalQuantitiesAfterSubtraction() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(5.0, LengthUnit.FEET);

        first.subtract(second);

        assertEquals(10.0, first.getValue(), 0.001);
        assertEquals(5.0, second.getValue(), 0.001);
    }

    @Test
    void shouldSupportChainedSubtraction() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(2.0, LengthUnit.FEET))
                        .subtract(new Quantity<>(1.0, LengthUnit.FEET));

        assertEquals(7.0, result.getValue(), 0.001);
    }

    @Test
    void subtractionShouldBeNonCommutative() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(5.0, LengthUnit.FEET);

        double forward = first.subtract(second).getValue();
        double reverse = second.subtract(first).getValue();

        assertEquals(5.0, forward, 0.001);
        assertEquals(-5.0, reverse, 0.001);
        assertNotEquals(forward, reverse);
    }

    // -----------------------------------------
    // SUBTRACTION - VALIDATION
    // -----------------------------------------

    @Test
    void shouldRejectNullSubtractionOperand() {
        Quantity<LengthUnit> quantity =
                new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> quantity.subtract(null)
        );
    }

    @Test
    void shouldRejectNullSubtractionTargetUnit() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(5.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> first.subtract(second, null)
        );
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void shouldRejectCrossCategorySubtraction() {
        Quantity length =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity weight =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertThrows(
                IllegalArgumentException.class,
                () -> length.subtract(weight)
        );
    }

    // -----------------------------------------
    // DIVISION - SAME UNIT
    // -----------------------------------------

    @Test
    void shouldDivideSameUnitFeet() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals(5.0, first.divide(second), 0.000001);
    }

    @Test
    void shouldDivideSameUnitLitres() {
        Quantity<VolumeUnit> first =
                new Quantity<>(10.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> second =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        assertEquals(2.0, first.divide(second), 0.000001);
    }

    @Test
    void shouldDivideSameUnitKilograms() {
        Quantity<WeightUnit> first =
                new Quantity<>(10.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> second =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertEquals(2.0, first.divide(second), 0.000001);
    }

    // -----------------------------------------
    // DIVISION - DIFFERENT UNITS
    // -----------------------------------------

    @Test
    void shouldDivideInchesByFeet() {
        Quantity<LengthUnit> first =
                new Quantity<>(24.0, LengthUnit.INCHES);

        Quantity<LengthUnit> second =
                new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals(1.0, first.divide(second), 0.000001);
    }

    @Test
    void shouldDivideKilogramsByGrams() {
        Quantity<WeightUnit> first =
                new Quantity<>(2.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> second =
                new Quantity<>(2000.0, WeightUnit.GRAM);

        assertEquals(1.0, first.divide(second), 0.000001);
    }

    @Test
    void shouldDivideMillilitresByLitres() {
        Quantity<VolumeUnit> first =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> second =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertEquals(1.0, first.divide(second), 0.000001);
    }

    // -----------------------------------------
    // DIVISION - RATIOS
    // -----------------------------------------

    @Test
    void shouldReturnRatioGreaterThanOne() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals(5.0, first.divide(second), 0.000001);
    }

    @Test
    void shouldReturnRatioLessThanOne() {
        Quantity<LengthUnit> first =
                new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(10.0, LengthUnit.FEET);

        assertEquals(0.5, first.divide(second), 0.000001);
    }

    @Test
    void shouldReturnRatioEqualToOne() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(10.0, LengthUnit.FEET);

        assertEquals(1.0, first.divide(second), 0.000001);
    }

    @Test
    void divisionShouldBeNonCommutative() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(5.0, LengthUnit.FEET);

        assertEquals(2.0, first.divide(second), 0.000001);
        assertEquals(0.5, second.divide(first), 0.000001);
    }

    @Test
    void shouldSupportLargeDivisionRatio() {
        Quantity<WeightUnit> first =
                new Quantity<>(1_000_000.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> second =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertEquals(1_000_000.0, first.divide(second), 0.001);
    }

    @Test
    void shouldSupportSmallDivisionRatio() {
        Quantity<WeightUnit> first =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> second =
                new Quantity<>(1_000_000.0, WeightUnit.KILOGRAM);

        assertEquals(0.000001, first.divide(second), 0.000000001);
    }

    @Test
    void shouldPreserveDivisionPrecision() {
        Quantity<LengthUnit> first =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(3.0, LengthUnit.FEET);

        assertEquals(1.0 / 3.0, first.divide(second), 0.0000000001);
    }

    // -----------------------------------------
    // DIVISION - VALIDATION
    // -----------------------------------------

    @Test
    void shouldRejectNullDivisionOperand() {
        Quantity<LengthUnit> quantity =
                new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> quantity.divide(null)
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

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void shouldRejectCrossCategoryDivision() {
        Quantity length =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity weight =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertThrows(
                IllegalArgumentException.class,
                () -> length.divide(weight)
        );
    }

    // -----------------------------------------
    // DIVISION - IMMUTABILITY AND MATHEMATICAL PROPERTIES
    // -----------------------------------------

    @Test
    void shouldNotChangeOriginalQuantitiesAfterDivision() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(2.0, LengthUnit.FEET);

        first.divide(second);

        assertEquals(10.0, first.getValue(), 0.001);
        assertEquals(2.0, second.getValue(), 0.001);
    }

    @Test
    void divisionShouldBeNonAssociative() {
        Quantity<LengthUnit> a =
                new Quantity<>(24.0, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(4.0, LengthUnit.FEET);

        Quantity<LengthUnit> c =
                new Quantity<>(2.0, LengthUnit.FEET);

        double left = a.divide(b) / c.getValue();
        double right = a.divide(
                new Quantity<>(b.getValue() / c.getValue(), LengthUnit.FEET)
        );

        assertNotEquals(left, right);
    }

    // -----------------------------------------
    // INTEGRATION WITH ADDITION
    // -----------------------------------------

    @Test
    void shouldSupportAdditionFollowedBySubtraction() {
        Quantity<LengthUnit> first =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                first.add(second).subtract(second);

        assertEquals(10.0, result.getValue(), 0.001);
    }

    @Test
    void shouldSupportSubtractionFollowedByDivision() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(4.0, LengthUnit.FEET));

        double ratio = result.divide(
                new Quantity<>(2.0, LengthUnit.FEET)
        );

        assertEquals(3.0, ratio, 0.001);
    }

    // -----------------------------------------
    // APPLICATION DEMONSTRATION METHODS
    // -----------------------------------------

    @Test
    void shouldDemonstrateSubtraction() {
        Quantity<LengthUnit> result =
                QuantityMeasurementApp.demonstrateSubtraction(
                        new Quantity<>(10.0, LengthUnit.FEET),
                        new Quantity<>(6.0, LengthUnit.INCHES),
                        LengthUnit.FEET
                );

        assertEquals(9.5, result.getValue(), 0.001);
    }

    @Test
    void shouldDemonstrateDivision() {
        double result =
                QuantityMeasurementApp.demonstrateDivision(
                        new Quantity<>(10.0, LengthUnit.FEET),
                        new Quantity<>(2.0, LengthUnit.FEET)
                );

        assertEquals(5.0, result, 0.001);
    }

    @Test
    void shouldRejectNullFirstOperandInSubtractionDemonstration() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuantityMeasurementApp.demonstrateSubtraction(
                        null,
                        new Quantity<>(5.0, LengthUnit.FEET),
                        LengthUnit.FEET
                )
        );
    }

    @Test
    void shouldRejectNullFirstOperandInDivisionDemonstration() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuantityMeasurementApp.demonstrateDivision(
                        null,
                        new Quantity<>(5.0, LengthUnit.FEET)
                )
        );
    }
}