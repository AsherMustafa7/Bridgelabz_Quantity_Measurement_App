package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityWeightTest {

    // Constructor and basic validation

    @Test
    void testConstructor_Kilogram() {
        QuantityWeight weight =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertEquals(1.0, weight.getValue());
        assertEquals(WeightUnit.KILOGRAM, weight.getUnit());
    }

    @Test
    void testConstructor_RejectsNullUnit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityWeight(1.0, null)
        );
    }

    @Test
    void testConstructor_RejectsNaN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityWeight(Double.NaN, WeightUnit.KILOGRAM)
        );
    }

    @Test
    void testConstructor_RejectsInfinity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityWeight(
                        Double.POSITIVE_INFINITY,
                        WeightUnit.GRAM
                )
        );
    }

    // WeightUnit conversion factors

    @Test
    void testKilogramConversionFactor() {
        assertEquals(1.0, WeightUnit.KILOGRAM.getConversionFactor());
    }

    @Test
    void testGramConversionFactor() {
        assertEquals(0.001, WeightUnit.GRAM.getConversionFactor());
    }

    @Test
    void testPoundConversionFactor() {
        assertEquals(0.453592, WeightUnit.POUND.getConversionFactor());
    }

    // Conversion to base unit

    @Test
    void testConvertToBaseUnit_Kilogram() {
        assertEquals(
                5.0,
                WeightUnit.KILOGRAM.convertToBaseUnit(5.0)
        );
    }

    @Test
    void testConvertToBaseUnit_Gram() {
        assertEquals(
                1.0,
                WeightUnit.GRAM.convertToBaseUnit(1000.0),
                1e-9
        );
    }

    @Test
    void testConvertToBaseUnit_Pound() {
        assertEquals(
                0.453592,
                WeightUnit.POUND.convertToBaseUnit(1.0),
                1e-9
        );
    }

    // Conversion from base unit

    @Test
    void testConvertFromBaseUnit_Kilogram() {
        assertEquals(
                2.0,
                WeightUnit.KILOGRAM.convertFromBaseUnit(2.0)
        );
    }

    @Test
    void testConvertFromBaseUnit_Gram() {
        assertEquals(
                1000.0,
                WeightUnit.GRAM.convertFromBaseUnit(1.0)
        );
    }

    @Test
    void testConvertFromBaseUnit_Pound() {
        assertEquals(
                1.0 / 0.453592,
                WeightUnit.POUND.convertFromBaseUnit(1.0),
                1e-9
        );
    }

    @Test
    void testWeightUnitRejectsNonFiniteValues() {
        assertThrows(
                IllegalArgumentException.class,
                () -> WeightUnit.KILOGRAM.convertToBaseUnit(Double.NaN)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> WeightUnit.GRAM.convertFromBaseUnit(
                        Double.POSITIVE_INFINITY
                )
        );
    }

    // Equality within the same unit

    @Test
    void testEquality_KilogramSameValue() {
        assertEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
        );
    }

    @Test
    void testEquality_GramSameValue() {
        assertEquals(
                new QuantityWeight(500.0, WeightUnit.GRAM),
                new QuantityWeight(500.0, WeightUnit.GRAM)
        );
    }

    @Test
    void testEquality_PoundSameValue() {
        assertEquals(
                new QuantityWeight(2.0, WeightUnit.POUND),
                new QuantityWeight(2.0, WeightUnit.POUND)
        );
    }

    @Test
    void testEquality_DifferentValues() {
        assertNotEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(2.0, WeightUnit.KILOGRAM)
        );
    }

    // Cross-unit equality

    @Test
    void testEquality_KilogramToGram() {
        assertEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000.0, WeightUnit.GRAM)
        );
    }

    @Test
    void testEquality_GramToKilogram() {
        assertEquals(
                new QuantityWeight(1000.0, WeightUnit.GRAM),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
        );
    }

    @Test
    void testEquality_PoundToGram() {
        assertEquals(
                new QuantityWeight(1.0, WeightUnit.POUND),
                new QuantityWeight(453.592, WeightUnit.GRAM)
        );
    }

    @Test
    void testEquality_KilogramToPound() {
        double pounds = 1.0 / WeightUnit.POUND.getConversionFactor();

        assertEquals(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(pounds, WeightUnit.POUND)
        );
    }

    @Test
    void testEquality_NullComparison() {
        QuantityWeight weight =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(null, weight);
    }

    @Test
    void testEquality_SameReference() {
        QuantityWeight weight =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertEquals(weight, weight);
    }

    @Test
    void testEquality_WeightAndLengthAreIncompatible() {
        QuantityWeight weight =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        QuantityLength length =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertNotEquals(weight, length);
        assertNotEquals(length, weight);
    }

    @Test
    void testEquality_ZeroAcrossUnits() {
        assertEquals(
                new QuantityWeight(0.0, WeightUnit.KILOGRAM),
                new QuantityWeight(0.0, WeightUnit.GRAM)
        );
    }

    @Test
    void testEquality_NegativeValues() {
        assertEquals(
                new QuantityWeight(-1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(-1000.0, WeightUnit.GRAM)
        );
    }

    @Test
    void testHashCode_EquivalentMeasurements() {
        QuantityWeight kilogram =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        QuantityWeight grams =
                new QuantityWeight(1000.0, WeightUnit.GRAM);

        assertEquals(kilogram.hashCode(), grams.hashCode());
    }

    // Conversion between units

    @Test
    void testConvertTo_Gram() {
        QuantityWeight result =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), 1e-9);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void testConvertTo_KilogramFromPound() {
        QuantityWeight result =
                new QuantityWeight(2.0, WeightUnit.POUND)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(0.907184, result.getValue(), 1e-9);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testConvertTo_PoundFromGram() {
        QuantityWeight result =
                new QuantityWeight(500.0, WeightUnit.GRAM)
                        .convertTo(WeightUnit.POUND);

        assertEquals(
                0.5 / 0.453592,
                result.getValue(),
                1e-9
        );
        assertEquals(WeightUnit.POUND, result.getUnit());
    }

    @Test
    void testConvertTo_SameUnit() {
        QuantityWeight result =
                new QuantityWeight(5.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(5.0, result.getValue());
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testConvertTo_Zero() {
        QuantityWeight result =
                new QuantityWeight(0.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM);

        assertEquals(0.0, result.getValue());
    }

    @Test
    void testConvertTo_NegativeValue() {
        QuantityWeight result =
                new QuantityWeight(-1.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM);

        assertEquals(-1000.0, result.getValue());
    }

    @Test
    void testConvertTo_RoundTrip() {
        QuantityWeight original =
                new QuantityWeight(1.5, WeightUnit.KILOGRAM);

        QuantityWeight result =
                original.convertTo(WeightUnit.GRAM)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.5, result.getValue(), 1e-9);
    }

    @Test
    void testConvertTo_RejectsNullTargetUnit() {
        QuantityWeight weight =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertThrows(
                IllegalArgumentException.class,
                () -> weight.convertTo(null)
        );
    }

    // Addition with implicit target unit

    @Test
    void testAddition_KilogramPlusKilogram() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(2.0, WeightUnit.KILOGRAM)
        );

        assertEquals(3.0, result.getValue(), 1e-9);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testAddition_KilogramPlusGram() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000.0, WeightUnit.GRAM)
        );

        assertEquals(2.0, result.getValue(), 1e-9);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testAddition_GramPlusKilogram() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(500.0, WeightUnit.GRAM),
                new QuantityWeight(0.5, WeightUnit.KILOGRAM)
        );

        assertEquals(1000.0, result.getValue(), 1e-9);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void testAddition_PoundPlusKilogram() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.POUND),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
        );

        assertEquals(
                (0.453592 + 1.0) / 0.453592,
                result.getValue(),
                1e-9
        );

        assertEquals(WeightUnit.POUND, result.getUnit());
    }

    // Addition with explicit target unit

    @Test
    void testAddition_ExplicitTargetGram() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000.0, WeightUnit.GRAM),
                WeightUnit.GRAM
        );

        assertEquals(2000.0, result.getValue(), 1e-9);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetPound() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.POUND),
                new QuantityWeight(453.592, WeightUnit.GRAM),
                WeightUnit.POUND
        );

        assertEquals(2.0, result.getValue(), 1e-9);
        assertEquals(WeightUnit.POUND, result.getUnit());
    }

    @Test
    void testAddition_Commutativity() {
        QuantityWeight first = new QuantityWeight(
                1.0, WeightUnit.KILOGRAM
        );

        QuantityWeight second = new QuantityWeight(
                500.0, WeightUnit.GRAM
        );

        QuantityWeight result1 =
                QuantityWeight.add(first, second, WeightUnit.POUND);

        QuantityWeight result2 =
                QuantityWeight.add(second, first, WeightUnit.POUND);

        assertEquals(result1, result2);
    }

    @Test
    void testAddition_WithZero() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(5.0, WeightUnit.KILOGRAM),
                new QuantityWeight(0.0, WeightUnit.GRAM)
        );

        assertEquals(5.0, result.getValue(), 1e-9);
    }

    @Test
    void testAddition_NegativeValues() {
        QuantityWeight result = QuantityWeight.add(
                new QuantityWeight(5.0, WeightUnit.KILOGRAM),
                new QuantityWeight(-2000.0, WeightUnit.GRAM)
        );

        assertEquals(3.0, result.getValue(), 1e-9);
    }

    @Test
    void testAddition_NullFirstOperand() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuantityWeight.add(
                        null,
                        new QuantityWeight(1.0, WeightUnit.GRAM)
                )
        );
    }

    @Test
    void testAddition_NullSecondOperand() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuantityWeight.add(
                        new QuantityWeight(1.0, WeightUnit.GRAM),
                        null
                )
        );
    }

    @Test
    void testAddition_NullTargetUnit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuantityWeight.add(
                        new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                        new QuantityWeight(1.0, WeightUnit.GRAM),
                        null
                )
        );
    }

    // Immutability and edge cases

    @Test
    void testAddition_DoesNotModifyOriginalMeasurements() {
        QuantityWeight first =
                new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        QuantityWeight second =
                new QuantityWeight(500.0, WeightUnit.GRAM);

        QuantityWeight result = first.add(second);

        assertEquals(1.0, first.getValue());
        assertEquals(500.0, second.getValue());
        assertEquals(1.5, result.getValue(), 1e-9);
    }

    @Test
    void testEquality_LargeValues() {
        assertEquals(
                new QuantityWeight(1000.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1_000_000.0, WeightUnit.GRAM)
        );
    }

    @Test
    void testEquality_SmallValues() {
        assertEquals(
                new QuantityWeight(0.001, WeightUnit.KILOGRAM),
                new QuantityWeight(1.0, WeightUnit.GRAM)
        );
    }

    @Test
    void testAddition_RejectsOverflow() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuantityWeight.add(
                        new QuantityWeight(
                                Double.MAX_VALUE,
                                WeightUnit.KILOGRAM
                        ),
                        new QuantityWeight(
                                Double.MAX_VALUE,
                                WeightUnit.KILOGRAM
                        )
                )
        );
    }

    @Test
    void testToString() {
        QuantityWeight weight =
                new QuantityWeight(2.0, WeightUnit.POUND);

        assertEquals("2.0 POUND", weight.toString());
    }
}