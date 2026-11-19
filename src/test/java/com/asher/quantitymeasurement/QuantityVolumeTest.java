package com.asher.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityVolumeTest {

    private static final double DELTA = 0.000001;

    // -------------------- Creation --------------------

    @Test
    void shouldCreateLitreQuantity() {
        Quantity<VolumeUnit> quantity =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        assertEquals(5.0, quantity.getValue());
        assertEquals(VolumeUnit.LITRE, quantity.getUnit());
    }

    @Test
    void shouldCreateMillilitreQuantity() {
        Quantity<VolumeUnit> quantity =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        assertEquals(500.0, quantity.getValue());
        assertEquals(VolumeUnit.MILLILITRE, quantity.getUnit());
    }

    @Test
    void shouldCreateGallonQuantity() {
        Quantity<VolumeUnit> quantity =
                new Quantity<>(2.0, VolumeUnit.GALLON);

        assertEquals(2.0, quantity.getValue());
        assertEquals(VolumeUnit.GALLON, quantity.getUnit());
    }

    // -------------------- Equality --------------------

    @Test
    void shouldCompareEqualLitreQuantities() {
        assertEquals(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(1.0, VolumeUnit.LITRE)
        );
    }

    @Test
    void shouldCompareEqualMillilitreQuantities() {
        assertEquals(
                new Quantity<>(500.0, VolumeUnit.MILLILITRE),
                new Quantity<>(500.0, VolumeUnit.MILLILITRE)
        );
    }

    @Test
    void shouldCompareEqualGallonQuantities() {
        assertEquals(
                new Quantity<>(2.0, VolumeUnit.GALLON),
                new Quantity<>(2.0, VolumeUnit.GALLON)
        );
    }

    @Test
    void shouldCompareLitreAndMillilitreAsEqual() {
        assertEquals(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
        );
    }

    @Test
    void shouldCompareMillilitreAndLitreAsEqual() {
        assertEquals(
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                new Quantity<>(1.0, VolumeUnit.LITRE)
        );
    }

    @Test
    void shouldCompareLitreAndGallonUsingExactEquivalentValue() {
        assertEquals(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(1.0 / 3.78541, VolumeUnit.GALLON)
        );
    }

    @Test
    void shouldCompareGallonAndLitreUsingExactEquivalentValue() {
        assertEquals(
                new Quantity<>(1.0, VolumeUnit.GALLON),
                new Quantity<>(3.78541, VolumeUnit.LITRE)
        );
    }

    @Test
    void shouldReturnFalseForDifferentVolumeValues() {
        assertNotEquals(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(2.0, VolumeUnit.LITRE)
        );
    }

    @Test
    void shouldReturnFalseWhenComparingVolumeWithLength() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertNotEquals(volume, length);
    }

    @Test
    void shouldReturnFalseWhenComparingVolumeWithWeight() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(volume, weight);
    }

    @Test
    void shouldReturnFalseWhenComparedWithNull() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertNotEquals(null, volume);
    }

    @Test
    void shouldReturnTrueForSameReference() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertEquals(volume, volume);
    }

    @Test
    void shouldMaintainEqualityForZeroAcrossUnits() {
        assertEquals(
                new Quantity<>(0.0, VolumeUnit.LITRE),
                new Quantity<>(0.0, VolumeUnit.MILLILITRE)
        );
    }

    @Test
    void shouldMaintainEqualityForNegativeValues() {
        assertEquals(
                new Quantity<>(-1.0, VolumeUnit.LITRE),
                new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)
        );
    }

    @Test
    void shouldMaintainEqualityForLargeValues() {
        assertEquals(
                new Quantity<>(1000.0, VolumeUnit.LITRE),
                new Quantity<>(1000000.0, VolumeUnit.MILLILITRE)
        );
    }

    @Test
    void shouldMaintainHashCodeForEqualVolumeQuantities() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(litre.hashCode(), millilitre.hashCode());
    }

    // -------------------- Conversion --------------------

    @Test
    void shouldConvertLitreToMillilitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void shouldConvertMillilitreToLitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(1.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void shouldConvertGallonToLitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(3.78541, result.getValue(), DELTA);
    }

    @Test
    void shouldConvertLitreToGallon() {
        Quantity<VolumeUnit> result =
                new Quantity<>(3.78541, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.GALLON);

        assertEquals(1.0, result.getValue(), DELTA);
    }

    @Test
    void shouldConvertMillilitreToGallon() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.GALLON);

        assertEquals(1000.0 / 3785.41, result.getValue(), DELTA);
    }

    @Test
    void shouldConvertGallonToMillilitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(3785.41, result.getValue(), DELTA);
    }

    @Test
    void shouldConvertSameUnitWithoutChangingValue() {
        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(5.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void shouldConvertZeroValue() {
        Quantity<VolumeUnit> result =
                new Quantity<>(0.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(0.0, result.getValue(), DELTA);
    }

    @Test
    void shouldConvertNegativeValue() {
        Quantity<VolumeUnit> result =
                new Quantity<>(-1.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(-1000.0, result.getValue(), DELTA);
    }

    @Test
    void shouldPreserveValueAfterRoundTripConversion() {
        Quantity<VolumeUnit> original =
                new Quantity<>(1.5, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                original.convertTo(VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(1.5, result.getValue(), DELTA);
    }

    @Test
    void shouldKeepOriginalQuantityUnchangedAfterConversion() {
        Quantity<VolumeUnit> original =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        original.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1.0, original.getValue());
        assertEquals(VolumeUnit.LITRE, original.getUnit());
    }

    @Test
    void shouldRejectNullConversionTarget() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertThrows(
                IllegalArgumentException.class,
                () -> volume.convertTo(null)
        );
    }

    // -------------------- Addition --------------------

    @Test
    void shouldAddLitreQuantities() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(2.0, VolumeUnit.LITRE));

        assertEquals(3.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void shouldAddMillilitreQuantities() {
        Quantity<VolumeUnit> result =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE)
                        .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(1000.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void shouldAddLitreAndMillilitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        assertEquals(2.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void shouldAddMillilitreAndLitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .add(new Quantity<>(1.0, VolumeUnit.LITRE));

        assertEquals(2000.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void shouldAddGallonAndLitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .add(new Quantity<>(3.78541, VolumeUnit.LITRE));

        assertEquals(2.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.GALLON, result.getUnit());
    }

    @Test
    void shouldAddUsingExplicitLitreTarget() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(
                                new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                                VolumeUnit.LITRE
                        );

        assertEquals(2.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void shouldAddUsingExplicitMillilitreTarget() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(
                                new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                                VolumeUnit.MILLILITRE
                        );

        assertEquals(2000.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void shouldAddUsingExplicitGallonTarget() {
        Quantity<VolumeUnit> result =
                new Quantity<>(3.78541, VolumeUnit.LITRE)
                        .add(
                                new Quantity<>(3.78541, VolumeUnit.LITRE),
                                VolumeUnit.GALLON
                        );

        assertEquals(2.0, result.getValue(), DELTA);
        assertEquals(VolumeUnit.GALLON, result.getUnit());
    }

    @Test
    void shouldAddZeroWithoutChangingValue() {
        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));

        assertEquals(5.0, result.getValue(), DELTA);
    }

    @Test
    void shouldAddNegativeValues() {
        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));

        assertEquals(3.0, result.getValue(), DELTA);
    }

    @Test
    void shouldAddLargeValues() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1e6, VolumeUnit.LITRE)
                        .add(new Quantity<>(1e6, VolumeUnit.LITRE));

        assertEquals(2e6, result.getValue(), DELTA);
    }

    @Test
    void shouldAddSmallValues() {
        Quantity<VolumeUnit> result =
                new Quantity<>(0.001, VolumeUnit.LITRE)
                        .add(new Quantity<>(0.002, VolumeUnit.LITRE));

        assertEquals(0.003, result.getValue(), DELTA);
    }

    @Test
    void shouldRejectNullQuantityDuringAddition() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertThrows(
                IllegalArgumentException.class,
                () -> volume.add(null)
        );
    }

    @Test
    void shouldRejectNullTargetUnitDuringAddition() {
        Quantity<VolumeUnit> first =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> second =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertThrows(
                IllegalArgumentException.class,
                () -> first.add(second, null)
        );
    }

    @Test
    void shouldKeepOriginalQuantitiesUnchangedAfterAddition() {
        Quantity<VolumeUnit> first =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> second =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = first.add(second);

        assertEquals(1.0, first.getValue());
        assertEquals(1000.0, second.getValue());
        assertEquals(2.0, result.getValue(), DELTA);
    }

    // -------------------- VolumeUnit Methods --------------------

    @Test
    void shouldHaveCorrectConversionFactors() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor());
        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor());
        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor());
    }

    @Test
    void shouldConvertLitreToBaseUnit() {
        assertEquals(5.0, VolumeUnit.LITRE.convertToBaseUnit(5.0), DELTA);
    }

    @Test
    void shouldConvertMillilitreToBaseUnit() {
        assertEquals(1.0, VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0), DELTA);
    }

    @Test
    void shouldConvertGallonToBaseUnit() {
        assertEquals(3.78541, VolumeUnit.GALLON.convertToBaseUnit(1.0), DELTA);
    }

    @Test
    void shouldConvertBaseUnitToLitre() {
        assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2.0), DELTA);
    }

    @Test
    void shouldConvertBaseUnitToMillilitre() {
        assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0), DELTA);
    }

    @Test
    void shouldConvertBaseUnitToGallon() {
        assertEquals(
                1.0,
                VolumeUnit.GALLON.convertFromBaseUnit(3.78541),
                DELTA
        );
    }

    @Test
    void shouldReturnCorrectUnitNames() {
        assertEquals("LITRE", VolumeUnit.LITRE.getUnitName());
        assertEquals("MILLILITRE", VolumeUnit.MILLILITRE.getUnitName());
        assertEquals("GALLON", VolumeUnit.GALLON.getUnitName());
    }

    // -------------------- Generic Integration --------------------

    @Test
    void shouldWorkWithGenericEqualityDemonstration() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(
                QuantityMeasurementApp.demonstrateEquality(litre, millilitre)
        );
    }

    @Test
    void shouldWorkWithGenericConversionDemonstration() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                QuantityMeasurementApp.demonstrateConversion(
                        litre,
                        VolumeUnit.MILLILITRE
                );

        assertEquals(1000.0, result.getValue(), DELTA);
    }

    @Test
    void shouldWorkWithGenericAdditionDemonstration() {
        Quantity<VolumeUnit> first =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> second =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                QuantityMeasurementApp.demonstrateAddition(
                        first,
                        second,
                        VolumeUnit.GALLON
                );

        assertEquals(2.0 / 3.78541, result.getValue(), DELTA);
        assertEquals(VolumeUnit.GALLON, result.getUnit());
    }
}