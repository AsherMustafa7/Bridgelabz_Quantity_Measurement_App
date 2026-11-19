package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @Test
    void shouldCreateLengthQuantity() {
        Quantity<LengthUnit> quantity =
                new Quantity<>(5.0, LengthUnit.FEET);

        assertEquals(5.0, quantity.getValue());
        assertEquals(LengthUnit.FEET, quantity.getUnit());
    }

    @Test
    void shouldCreateWeightQuantity() {
        Quantity<WeightUnit> quantity =
                new Quantity<>(2.0, WeightUnit.KILOGRAM);

        assertEquals(2.0, quantity.getValue());
        assertEquals(WeightUnit.KILOGRAM, quantity.getUnit());
    }

    @Test
    void shouldRejectNullUnit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity<>(5.0, null)
        );
    }

    @Test
    void shouldRejectNonFiniteValue() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET)
        );
    }

    @Test
    void shouldCompareEquivalentLengthQuantities() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
    }

    @Test
    void shouldCompareEquivalentWeightQuantities() {
        Quantity<WeightUnit> kilograms =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> grams =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(kilograms, grams);
    }

    @Test
    void shouldReturnFalseForDifferentMeasurementCategories() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(length, weight);
    }

    @Test
    void shouldReturnFalseForDifferentMeasurements() {
        Quantity<LengthUnit> first =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(2.0, LengthUnit.FEET);

        assertNotEquals(first, second);
    }

    @Test
    void shouldReturnSameHashCodeForEqualQuantities() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(feet.hashCode(), inches.hashCode());
    }

    @Test
    void shouldConvertFeetToInches() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                feet.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), 0.000001);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void shouldConvertKilogramsToGrams() {
        Quantity<WeightUnit> kilograms =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result =
                kilograms.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), 0.000001);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void shouldRejectNullConversionTarget() {
        Quantity<LengthUnit> quantity =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> quantity.convertTo(null)
        );
    }

    @Test
    void shouldAddLengthQuantitiesInFirstUnits() {
        Quantity<LengthUnit> first =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = first.add(second);

        assertEquals(2.0, result.getValue(), 0.000001);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void shouldAddWeightQuantitiesInFirstUnits() {
        Quantity<WeightUnit> first =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> second =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = first.add(second);

        assertEquals(2.0, result.getValue(), 0.000001);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void shouldAddLengthQuantitiesInSpecifiedTargetUnit() {
        Quantity<LengthUnit> first =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                first.add(second, LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), 0.000001);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void shouldAddWeightQuantitiesInSpecifiedTargetUnit() {
        Quantity<WeightUnit> first =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> second =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                first.add(second, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue(), 0.000001);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void shouldRejectNullQuantityDuringAddition() {
        Quantity<LengthUnit> first =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> first.add(null)
        );
    }

    @Test
    void shouldRejectNullTargetUnitDuringAddition() {
        Quantity<LengthUnit> first =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> first.add(second, null)
        );
    }

    @Test
    void shouldKeepOriginalQuantityUnchangedAfterConversion() {
        Quantity<LengthUnit> original =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> converted =
                original.convertTo(LengthUnit.INCHES);

        assertEquals(1.0, original.getValue());
        assertEquals(LengthUnit.FEET, original.getUnit());

        assertEquals(12.0, converted.getValue(), 0.000001);
        assertEquals(LengthUnit.INCHES, converted.getUnit());
    }

    @Test
    void shouldKeepOriginalQuantitiesUnchangedAfterAddition() {
        Quantity<WeightUnit> first =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> second =
                new Quantity<>(500.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = first.add(second);

        assertEquals(1.0, first.getValue());
        assertEquals(500.0, second.getValue());
        assertEquals(1.5, result.getValue(), 0.000001);
    }

    @Test
    void shouldReturnUnitName() {
        assertEquals("FEET", LengthUnit.FEET.getUnitName());
        assertEquals("KILOGRAM", WeightUnit.KILOGRAM.getUnitName());
    }

    @Test
    void shouldUseGenericAppDemonstrationMethods() {
        Quantity<LengthUnit> first =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> second =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(
                QuantityMeasurementApp.demonstrateEquality(first, second)
        );

        Quantity<LengthUnit> converted =
                QuantityMeasurementApp.demonstrateConversion(
                        first,
                        LengthUnit.INCHES
                );

        assertEquals(12.0, converted.getValue(), 0.000001);

        Quantity<LengthUnit> added =
                QuantityMeasurementApp.demonstrateAddition(
                        first,
                        second,
                        LengthUnit.FEET
                );

        assertEquals(2.0, added.getValue(), 0.000001);
    }
}