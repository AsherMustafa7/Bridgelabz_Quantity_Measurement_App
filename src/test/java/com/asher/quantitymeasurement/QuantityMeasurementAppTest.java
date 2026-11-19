
package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    // ---------------- UC3: FEET TESTS ----------------

    @Test
    void testFeetEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength second =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertEquals(first, second);
    }

    @Test
    void testFeetEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength second =
                new QuantityLength(2.0, LengthUnit.FEET);

        assertNotEquals(first, second);
    }

    // ---------------- UC3: INCHES TESTS ----------------

    @Test
    void testInchesEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength second =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(first, second);
    }

    @Test
    void testInchesEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength second =
                new QuantityLength(24.0, LengthUnit.INCHES);

        assertNotEquals(first, second);
    }

    // ---------------- UC3: CROSS-UNIT TESTS ----------------

    @Test
    void testFeetAndInchesEquality_OneFootEqualsTwelveInches() {
        QuantityLength oneFoot =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength twelveInches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(oneFoot, twelveInches);
    }

    @Test
    void testFeetAndInchesEquality_TwoFeetNotEqualToTwelveInches() {
        QuantityLength twoFeet =
                new QuantityLength(2.0, LengthUnit.FEET);

        QuantityLength twelveInches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertNotEquals(twoFeet, twelveInches);
    }

    @Test
    void testFeetAndInchesEquality_ThreeFeetEqualsThirtySixInches() {
        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(threeFeet, thirtySixInches);
    }

    // ---------------- UC3: OBJECT EQUALITY RULES ----------------

    @Test
    void testEquality_NullComparison() {
        QuantityLength measurement =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertNotEquals(measurement, null);
    }

    @Test
    void testEquality_DifferentObjectType() {
        QuantityLength measurement =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertNotEquals(measurement, "1.0");
    }

    @Test
    void testEquality_SameReference() {
        QuantityLength measurement =
                new QuantityLength(1.0, LengthUnit.FEET);

        assertEquals(measurement, measurement);
    }

    @Test
    void testEquality_Symmetry() {
        QuantityLength feet =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength inches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
        assertEquals(inches, feet);
    }

    @Test
    void testEquality_HashCodeContract() {
        QuantityLength feet =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength inches =
                new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
        assertEquals(feet.hashCode(), inches.hashCode());
    }

    @Test
    void testConstructor_NullUnit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityLength(1.0, null)
        );
    }

    // ---------------- UC4: YARDS TESTS ----------------

    @Test
    void testYardsEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength second =
                new QuantityLength(1.0, LengthUnit.YARDS);

        assertEquals(first, second);
    }

    @Test
    void testYardsEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength second =
                new QuantityLength(2.0, LengthUnit.YARDS);

        assertNotEquals(first, second);
    }

    @Test
    void testYardsAndFeetEquality_OneYardEqualsThreeFeet() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        assertEquals(oneYard, threeFeet);
    }

    @Test
    void testYardsAndInchesEquality_OneYardEqualsThirtySixInches() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(oneYard, thirtySixInches);
    }

    @Test
    void testYardsAndFeetEquality_DifferentMeasurements() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength twoFeet =
                new QuantityLength(2.0, LengthUnit.FEET);

        assertNotEquals(oneYard, twoFeet);
    }

    // ---------------- UC4: CENTIMETERS TESTS ----------------

    @Test
    void testCentimetersEquality_SameValue() {
        QuantityLength first =
                new QuantityLength(100.0, LengthUnit.CENTIMETERS);

        QuantityLength second =
                new QuantityLength(100.0, LengthUnit.CENTIMETERS);

        assertEquals(first, second);
    }

    @Test
    void testCentimetersEquality_DifferentValue() {
        QuantityLength first =
                new QuantityLength(100.0, LengthUnit.CENTIMETERS);

        QuantityLength second =
                new QuantityLength(200.0, LengthUnit.CENTIMETERS);

        assertNotEquals(first, second);
    }

    @Test
    void testCentimetersAndInchesEquality_UsingSuppliedConversion() {
        QuantityLength oneCentimeter =
                new QuantityLength(1.0, LengthUnit.CENTIMETERS);

        QuantityLength equivalentInches =
                new QuantityLength(0.393701, LengthUnit.INCHES);

        assertEquals(oneCentimeter, equivalentInches);
    }

    @Test
    void testCentimetersAndFeetEquality_UsingSuppliedConversion() {
        QuantityLength thirtyPointFourEightCentimeters =
                new QuantityLength(30.48, LengthUnit.CENTIMETERS);

        QuantityLength equivalentFeet =
                new QuantityLength(
                        30.48 * (0.393701 / 12.0),
                        LengthUnit.FEET
                );

        assertEquals(
                thirtyPointFourEightCentimeters,
                equivalentFeet
        );
    }

    @Test
    void testCentimetersAndInchesEquality_DifferentMeasurements() {
        QuantityLength oneCentimeter =
                new QuantityLength(1.0, LengthUnit.CENTIMETERS);

        QuantityLength oneInch =
                new QuantityLength(1.0, LengthUnit.INCHES);

        assertNotEquals(oneCentimeter, oneInch);
    }

    // ---------------- UC4: MULTI-UNIT TESTS ----------------

    @Test
    void testMultiUnitEquality_YardsFeetAndInches() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(oneYard, threeFeet);
        assertEquals(threeFeet, thirtySixInches);
        assertEquals(oneYard, thirtySixInches);
    }

    @Test
    void testEquality_Transitivity() {
        QuantityLength oneYard =
                new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength threeFeet =
                new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength thirtySixInches =
                new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(oneYard, threeFeet);
        assertEquals(threeFeet, thirtySixInches);
        assertEquals(oneYard, thirtySixInches);
    }

private static final double EPSILON = 1e-6;

// Basic conversions

@Test
void testConversion_FeetToInches() {
    assertEquals(
            12.0,
            QuantityLength.convert(
                    1.0, LengthUnit.FEET, LengthUnit.INCHES),
            EPSILON
    );
}

@Test
void testConversion_InchesToFeet() {
    assertEquals(
            2.0,
            QuantityLength.convert(
                    24.0, LengthUnit.INCHES, LengthUnit.FEET),
            EPSILON
    );
}

@Test
void testConversion_YardsToInches() {
    assertEquals(
            36.0,
            QuantityLength.convert(
                    1.0, LengthUnit.YARDS, LengthUnit.INCHES),
            EPSILON
    );
}

@Test
void testConversion_InchesToYards() {
    assertEquals(
            2.0,
            QuantityLength.convert(
                    72.0, LengthUnit.INCHES, LengthUnit.YARDS),
            EPSILON
    );
}

@Test
void testConversion_CentimetersToInches() {
    assertEquals(
            1.0,
            QuantityLength.convert(
                    2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES),
            EPSILON
    );
}

@Test
void testConversion_FeetToYards() {
    assertEquals(
            2.0,
            QuantityLength.convert(
                    6.0, LengthUnit.FEET, LengthUnit.YARDS),
            EPSILON
    );
}

// Cross-unit and bidirectional conversions

@Test
void testConversion_YardsToCentimeters() {
    double centimeters = QuantityLength.convert(
            1.0, LengthUnit.YARDS, LengthUnit.CENTIMETERS);

    assertEquals(
            91.44,
            centimeters,
            1e-3
    );
}

@Test
void testConversion_RoundTripPreservesValue() {
    double originalValue = 15.5;

    double convertedValue = QuantityLength.convert(
            originalValue, LengthUnit.FEET, LengthUnit.INCHES);

    double returnedValue = QuantityLength.convert(
            convertedValue, LengthUnit.INCHES, LengthUnit.FEET);

    assertEquals(originalValue, returnedValue, EPSILON);
}

// Special numeric values

@Test
void testConversion_ZeroValue() {
    assertEquals(
            0.0,
            QuantityLength.convert(
                    0.0, LengthUnit.FEET, LengthUnit.INCHES),
            EPSILON
    );
}

@Test
void testConversion_NegativeValue() {
    assertEquals(
            -12.0,
            QuantityLength.convert(
                    -1.0, LengthUnit.FEET, LengthUnit.INCHES),
            EPSILON
    );
}

@Test
void testConversion_LargeValue() {
    assertEquals(
            1.2e101,
            QuantityLength.convert(
                    1.0e100, LengthUnit.FEET, LengthUnit.INCHES),
            1e95
    );
}

@Test
void testConversion_SmallValue() {
    assertEquals(
            1.2e-99,
            QuantityLength.convert(
                    1.0e-100, LengthUnit.FEET, LengthUnit.INCHES),
            1e-105
    );
}

@Test
void testConversion_SameUnitReturnsOriginalValue() {
    assertEquals(
            5.5,
            QuantityLength.convert(
                    5.5, LengthUnit.FEET, LengthUnit.FEET),
            0.0
    );
}

// Invalid input handling

@Test
void testConversion_NullSourceUnitThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.convert(
                    1.0, null, LengthUnit.FEET)
    );
}

@Test
void testConversion_NullTargetUnitThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.convert(
                    1.0, LengthUnit.FEET, null)
    );
}

@Test
void testConversion_NaNThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.convert(
                    Double.NaN, LengthUnit.FEET, LengthUnit.INCHES)
    );
}

@Test
void testConversion_PositiveInfinityThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.convert(
                    Double.POSITIVE_INFINITY,
                    LengthUnit.FEET,
                    LengthUnit.INCHES)
    );
}

@Test
void testConversion_NegativeInfinityThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.convert(
                    Double.NEGATIVE_INFINITY,
                    LengthUnit.FEET,
                    LengthUnit.INCHES)
    );
}

@Test
void testConversion_OverflowThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.convert(
                    Double.MAX_VALUE,
                    LengthUnit.YARDS,
                    LengthUnit.CENTIMETERS)
    );
}

// Instance conversion and immutability

@Test
void testInstanceConversionReturnsNewObject() {
    QuantityLength original =
            new QuantityLength(1.0, LengthUnit.YARDS);

    QuantityLength converted =
            original.convertTo(LengthUnit.INCHES);

    assertEquals(36.0, converted.getValue(), EPSILON);
    assertEquals(LengthUnit.INCHES, converted.getUnit());

    assertNotSame(original, converted);

    // Original measurement must remain unchanged.
    assertEquals(1.0, original.getValue(), EPSILON);
    assertEquals(LengthUnit.YARDS, original.getUnit());
}

@Test
void testInstanceConversion_NullTargetThrowsException() {
    QuantityLength original =
            new QuantityLength(1.0, LengthUnit.FEET);

    assertThrows(
            IllegalArgumentException.class,
            () -> original.convertTo(null)
    );
}

// Object representation

@Test
void testToStringReturnsReadableMeasurement() {
    QuantityLength length =
            new QuantityLength(3.0, LengthUnit.FEET);

    assertEquals("3.0 FEET", length.toString());
}
private static final double ADDITION_EPSILON = 1e-6;

// Same-unit addition

@Test
void testAddition_SameUnit_FeetPlusFeet() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1.0, LengthUnit.FEET),
            new QuantityLength(2.0, LengthUnit.FEET),
            LengthUnit.FEET
    );

    assertEquals(3.0, result.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

@Test
void testAddition_SameUnit_InchPlusInch() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(6.0, LengthUnit.INCHES),
            new QuantityLength(6.0, LengthUnit.INCHES),
            LengthUnit.INCHES
    );

    assertEquals(12.0, result.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.INCHES, result.getUnit());
}

// Cross-unit addition

@Test
void testAddition_CrossUnit_FeetPlusInches() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1.0, LengthUnit.FEET),
            new QuantityLength(12.0, LengthUnit.INCHES),
            LengthUnit.FEET
    );

    assertEquals(2.0, result.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

@Test
void testAddition_CrossUnit_InchPlusFeet() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(12.0, LengthUnit.INCHES),
            new QuantityLength(1.0, LengthUnit.FEET),
            LengthUnit.INCHES
    );

    assertEquals(24.0, result.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.INCHES, result.getUnit());
}

@Test
void testAddition_CrossUnit_YardPlusFeet() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1.0, LengthUnit.YARDS),
            new QuantityLength(3.0, LengthUnit.FEET),
            LengthUnit.YARDS
    );

    assertEquals(2.0, result.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.YARDS, result.getUnit());
}

@Test
void testAddition_CrossUnit_CentimeterPlusInch() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(2.54, LengthUnit.CENTIMETERS),
            new QuantityLength(1.0, LengthUnit.INCHES),
            LengthUnit.CENTIMETERS
    );

    assertEquals(5.08, result.getValue(), 1e-3);
    assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
}

// Mathematical properties

@Test
void testAddition_Commutativity() {
    QuantityLength first = new QuantityLength(
            1.0, LengthUnit.FEET
    );

    QuantityLength second = new QuantityLength(
            12.0, LengthUnit.INCHES
    );

    QuantityLength result1 =
            QuantityLength.add(first, second, LengthUnit.FEET);

    QuantityLength result2 =
            QuantityLength.add(second, first, LengthUnit.FEET);

    assertEquals(
            result1.getValue(),
            result2.getValue(),
            ADDITION_EPSILON
    );
}

@Test
void testAddition_WithZero() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(5.0, LengthUnit.FEET),
            new QuantityLength(0.0, LengthUnit.INCHES),
            LengthUnit.FEET
    );

    assertEquals(5.0, result.getValue(), ADDITION_EPSILON);
}

@Test
void testAddition_NegativeValues() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(5.0, LengthUnit.FEET),
            new QuantityLength(-2.0, LengthUnit.FEET),
            LengthUnit.FEET
    );

    assertEquals(3.0, result.getValue(), ADDITION_EPSILON);
}

// Null validation

@Test
void testAddition_NullFirstOperand() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.add(
                    null,
                    new QuantityLength(1.0, LengthUnit.FEET),
                    LengthUnit.FEET
            )
    );
}

@Test
void testAddition_NullSecondOperand() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.add(
                    new QuantityLength(1.0, LengthUnit.FEET),
                    null,
                    LengthUnit.FEET
            )
    );
}

@Test
void testAddition_NullTargetUnit() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.add(
                    new QuantityLength(1.0, LengthUnit.FEET),
                    new QuantityLength(2.0, LengthUnit.FEET),
                    null
            )
    );
}

// Numeric validation

@Test
void testAddition_NaNValue() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.add(
                    new QuantityLength(Double.NaN, LengthUnit.FEET),
                    new QuantityLength(1.0, LengthUnit.FEET),
                    LengthUnit.FEET
            )
    );
}

@Test
void testAddition_InfiniteValue() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.add(
                    new QuantityLength(Double.POSITIVE_INFINITY, LengthUnit.FEET),
                    new QuantityLength(1.0, LengthUnit.FEET),
                    LengthUnit.FEET
            )
    );
}

@Test
void testAddition_OverflowThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.add(
                    new QuantityLength(Double.MAX_VALUE, LengthUnit.FEET),
                    new QuantityLength(Double.MAX_VALUE, LengthUnit.FEET),
                    LengthUnit.FEET
            )
    );
}

// Large and small values

@Test
void testAddition_LargeValues() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1e6, LengthUnit.FEET),
            new QuantityLength(1e6, LengthUnit.FEET),
            LengthUnit.FEET
    );

    assertEquals(2e6, result.getValue(), ADDITION_EPSILON);
}

@Test
void testAddition_SmallValues() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(0.001, LengthUnit.FEET),
            new QuantityLength(0.002, LengthUnit.FEET),
            LengthUnit.FEET
    );

    assertEquals(0.003, result.getValue(), ADDITION_EPSILON);
}

// Immutability

@Test
void testAddition_OriginalMeasurementsRemainUnchanged() {
    QuantityLength first =
            new QuantityLength(1.0, LengthUnit.FEET);

    QuantityLength second =
            new QuantityLength(12.0, LengthUnit.INCHES);

    QuantityLength result =
            QuantityLength.add(first, second, LengthUnit.FEET);

    assertEquals(1.0, first.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.FEET, first.getUnit());

    assertEquals(12.0, second.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.INCHES, second.getUnit());

    assertNotSame(first, result);
    assertNotSame(second, result);
}

// Overloaded raw-value addition

@Test
void testAddition_RawValues() {
    QuantityLength result = QuantityLength.add(
            1.0,
            LengthUnit.FEET,
            12.0,
            LengthUnit.INCHES,
            LengthUnit.FEET
    );

    assertEquals(2.0, result.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

// Instance addition

@Test
void testAddition_InstanceMethod() {
    QuantityLength first =
            new QuantityLength(1.0, LengthUnit.FEET);

    QuantityLength second =
            new QuantityLength(12.0, LengthUnit.INCHES);

    QuantityLength result = first.add(second);

    assertEquals(2.0, result.getValue(), ADDITION_EPSILON);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_Feet() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1.0, LengthUnit.FEET),
            new QuantityLength(12.0, LengthUnit.INCHES),
            LengthUnit.FEET
    );

    assertEquals(2.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_Inches() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1.0, LengthUnit.FEET),
            new QuantityLength(12.0, LengthUnit.INCHES),
            LengthUnit.INCHES
    );

    assertEquals(24.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.INCHES, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_Yards() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1.0, LengthUnit.FEET),
            new QuantityLength(12.0, LengthUnit.INCHES),
            LengthUnit.YARDS
    );

    assertEquals(2.0 / 3.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.YARDS, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_Centimeters() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1.0, LengthUnit.INCHES),
            new QuantityLength(1.0, LengthUnit.INCHES),
            LengthUnit.CENTIMETERS
    );

        assertEquals(5.08, result.getValue(), 0.00001);
        assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(2.0, LengthUnit.YARDS),
            new QuantityLength(3.0, LengthUnit.FEET),
            LengthUnit.YARDS
    );

    assertEquals(3.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.YARDS, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(2.0, LengthUnit.YARDS),
            new QuantityLength(3.0, LengthUnit.FEET),
            LengthUnit.FEET
    );

    assertEquals(9.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_Commutativity() {
    QuantityLength first = new QuantityLength(1.0, LengthUnit.FEET);
    QuantityLength second = new QuantityLength(12.0, LengthUnit.INCHES);

    QuantityLength result1 =
            QuantityLength.add(first, second, LengthUnit.YARDS);

    QuantityLength result2 =
            QuantityLength.add(second, first, LengthUnit.YARDS);

    assertEquals(result1.getValue(), result2.getValue(), 1e-9);
    assertEquals(result1.getUnit(), result2.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_WithZero() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(5.0, LengthUnit.FEET),
            new QuantityLength(0.0, LengthUnit.INCHES),
            LengthUnit.YARDS
    );

    assertEquals(5.0 / 3.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.YARDS, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_NegativeValues() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(5.0, LengthUnit.FEET),
            new QuantityLength(-2.0, LengthUnit.FEET),
            LengthUnit.INCHES
    );

    assertEquals(36.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.INCHES, result.getUnit());
}

@Test
void testAddition_ExplicitTargetUnit_NullTargetUnit() {
    QuantityLength first = new QuantityLength(1.0, LengthUnit.FEET);
    QuantityLength second = new QuantityLength(12.0, LengthUnit.INCHES);

    assertThrows(
            IllegalArgumentException.class,
            () -> QuantityLength.add(first, second, null)
    );
}

@Test
void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(1000.0, LengthUnit.FEET),
            new QuantityLength(500.0, LengthUnit.FEET),
            LengthUnit.INCHES
    );

    assertEquals(18000.0, result.getValue(), 1e-9);
}

@Test
void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(12.0, LengthUnit.INCHES),
            new QuantityLength(12.0, LengthUnit.INCHES),
            LengthUnit.YARDS
    );

    assertEquals(2.0 / 3.0, result.getValue(), 1e-9);
}

@Test
void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
    LengthUnit[] units = LengthUnit.values();

    for (LengthUnit firstUnit : units) {
        for (LengthUnit secondUnit : units) {
            for (LengthUnit targetUnit : units) {
                QuantityLength first =
                        new QuantityLength(2.0, firstUnit);

                QuantityLength second =
                        new QuantityLength(3.0, secondUnit);

                QuantityLength result =
                        QuantityLength.add(first, second, targetUnit);

                double expected = QuantityLength.convert(
                        2.0 * firstUnit.getConversionFactor()
                                + 3.0 * secondUnit.getConversionFactor(),
                        LengthUnit.FEET,
                        targetUnit
                );

                assertEquals(expected, result.getValue(), 1e-9);
                assertEquals(targetUnit, result.getUnit());
            }
        }
    }
}

@Test
void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
    QuantityLength result = QuantityLength.add(
            new QuantityLength(2.54, LengthUnit.CENTIMETERS),
            new QuantityLength(1.0, LengthUnit.INCHES),
            LengthUnit.CENTIMETERS
    );
assertEquals(5.08, result.getValue(), 0.00001);
assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
}

@Test
void testLengthUnitEnum_FeetConstant() {
    assertEquals(1.0, LengthUnit.FEET.getConversionFactor());
}

@Test
void testLengthUnitEnum_InchesConstant() {
    assertEquals(1.0 / 12.0, LengthUnit.INCHES.getConversionFactor());
}

@Test
void testLengthUnitEnum_YardsConstant() {
    assertEquals(3.0, LengthUnit.YARDS.getConversionFactor());
}

@Test
void testLengthUnitEnum_CentimetersConstant() {
    assertEquals(
            0.393701 / 12.0,
            LengthUnit.CENTIMETERS.getConversionFactor()
    );
}

@Test
void testConvertToBaseUnit_FeetToFeet() {
    assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5.0));
}

@Test
void testConvertToBaseUnit_InchesToFeet() {
    assertEquals(
            1.0,
            LengthUnit.INCHES.convertToBaseUnit(12.0),
            1e-9
    );
}

@Test
void testConvertToBaseUnit_YardsToFeet() {
    assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0));
}

@Test
void testConvertToBaseUnit_CentimetersToFeet() {
    assertEquals(
            1.0,
            LengthUnit.CENTIMETERS.convertToBaseUnit(30.48),
            0.00001
    );
}

@Test
void testConvertFromBaseUnit_FeetToFeet() {
    assertEquals(2.0, LengthUnit.FEET.convertFromBaseUnit(2.0));
}

@Test
void testConvertFromBaseUnit_FeetToInches() {
    assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1.0));
}

@Test
void testConvertFromBaseUnit_FeetToYards() {
    assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0));
}

@Test
void testConvertFromBaseUnit_FeetToCentimeters() {
    assertEquals(
            30.48,
            LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0),
            0.0001
    );
}

@Test
void testLengthUnitRejectsNaN() {
    assertThrows(
            IllegalArgumentException.class,
            () -> LengthUnit.FEET.convertToBaseUnit(Double.NaN)
    );
}

@Test
void testLengthUnitRejectsInfinity() {
    assertThrows(
            IllegalArgumentException.class,
            () -> LengthUnit.INCHES.convertFromBaseUnit(Double.POSITIVE_INFINITY)
    );
}

@Test
void testQuantityLengthRefactored_Equality() {
    QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
    QuantityLength inches = new QuantityLength(12.0, LengthUnit.INCHES);

    assertEquals(feet, inches);
}

@Test
void testQuantityLengthRefactored_ConvertTo() {
    QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);

    QuantityLength result = feet.convertTo(LengthUnit.INCHES);

    assertEquals(12.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.INCHES, result.getUnit());
}

@Test
void testQuantityLengthRefactored_Add() {
    QuantityLength first = new QuantityLength(1.0, LengthUnit.FEET);
    QuantityLength second = new QuantityLength(12.0, LengthUnit.INCHES);

    QuantityLength result = QuantityLength.add(
            first,
            second,
            LengthUnit.FEET
    );

    assertEquals(2.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

@Test
void testQuantityLengthRefactored_AddWithTargetUnit() {
    QuantityLength first = new QuantityLength(1.0, LengthUnit.FEET);
    QuantityLength second = new QuantityLength(12.0, LengthUnit.INCHES);

    QuantityLength result = QuantityLength.add(
            first,
            second,
            LengthUnit.YARDS
    );

    assertEquals(2.0 / 3.0, result.getValue(), 1e-9);
    assertEquals(LengthUnit.YARDS, result.getUnit());
}

@Test
void testQuantityLengthRefactored_InvalidValue() {
    assertThrows(
            IllegalArgumentException.class,
            () -> new QuantityLength(Double.NaN, LengthUnit.FEET)
    );
}

@Test
void testRoundTripConversion_RefactoredDesign() {
    double originalValue = 12.0;

    double converted = QuantityLength.convert(
            originalValue,
            LengthUnit.INCHES,
            LengthUnit.YARDS
    );

    double roundTrip = QuantityLength.convert(
            converted,
            LengthUnit.YARDS,
            LengthUnit.INCHES
    );

    assertEquals(originalValue, roundTrip, 1e-9);
}

@Test
void testUnitImmutability() {
    LengthUnit[] units = LengthUnit.values();

    assertEquals(4, units.length);
    assertEquals(LengthUnit.FEET, LengthUnit.valueOf("FEET"));
}

}
