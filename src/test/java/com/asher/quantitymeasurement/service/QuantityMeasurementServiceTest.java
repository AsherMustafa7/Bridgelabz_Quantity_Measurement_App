
package com.asher.quantitymeasurement.service;

import com.asher.quantitymeasurement.dto.QuantityDTO;
import com.asher.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.asher.quantitymeasurement.exception.QuantityMeasurementException;
import com.asher.quantitymeasurement.repository.IQuantityMeasurementRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuantityMeasurementServiceTest {

    private IQuantityMeasurementRepository repository;
    private IQuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        repository = mock(IQuantityMeasurementRepository.class);
        service = new QuantityMeasurementServiceImpl(repository);
    }

    // CONVERSION TESTS

    @Test
    void givenFeet_whenConvertingToInches_thenReturnConvertedQuantity() {

        QuantityDTO input =
                new QuantityDTO(3.0, "FEET", "LENGTH");

        QuantityDTO result = service.convert(input, "INCHES");

        assertEquals(36.0, result.getValue(), 0.0001);
        assertEquals("INCHES", result.getUnit());
        assertEquals("LENGTH", result.getMeasurementType());

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void givenInvalidUnit_whenConverting_thenThrowExceptionAndSaveError() {

        QuantityDTO input =
                new QuantityDTO(3.0, "INVALID", "LENGTH");

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.convert(input, "INCHES")
        );

        ArgumentCaptor<QuantityMeasurementEntity> captor =
                ArgumentCaptor.forClass(QuantityMeasurementEntity.class);

        verify(repository).save(captor.capture());

        assertTrue(captor.getValue().isError());
        assertEquals("CONVERT", captor.getValue().getOperation());
    }

    // COMPARISON TESTS

    @Test
    void givenEquivalentQuantities_whenComparing_thenReturnTrue() {

        QuantityDTO first =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(12.0, "INCHES", "LENGTH");

        assertTrue(service.compare(first, second));

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void givenDifferentQuantities_whenComparing_thenReturnFalse() {

        QuantityDTO first =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(10.0, "INCHES", "LENGTH");

        assertFalse(service.compare(first, second));

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    // ADDITION TESTS

    @Test
    void givenTwoQuantities_whenAdding_thenReturnResultInFirstUnit() {

        QuantityDTO first =
                new QuantityDTO(3.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(12.0, "INCHES", "LENGTH");

        QuantityDTO result = service.add(first, second);

        assertEquals(4.0, result.getValue(), 0.0001);
        assertEquals("FEET", result.getUnit());

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void givenTwoQuantities_whenAddingWithTargetUnit_thenReturnTargetUnit() {

        QuantityDTO first =
                new QuantityDTO(3.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(12.0, "INCHES", "LENGTH");

        QuantityDTO result =
                service.add(first, second, "INCHES");

        assertEquals(48.0, result.getValue(), 0.0001);
        assertEquals("INCHES", result.getUnit());

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    // SUBTRACTION TESTS

    @Test
    void givenTwoQuantities_whenSubtracting_thenReturnResultInFirstUnit() {

        QuantityDTO first =
                new QuantityDTO(3.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(12.0, "INCHES", "LENGTH");

        QuantityDTO result = service.subtract(first, second);

        assertEquals(2.0, result.getValue(), 0.0001);
        assertEquals("FEET", result.getUnit());

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void givenTwoQuantities_whenSubtractingWithTargetUnit_thenReturnTargetUnit() {

        QuantityDTO first =
                new QuantityDTO(3.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(12.0, "INCHES", "LENGTH");

        QuantityDTO result =
                service.subtract(first, second, "INCHES");

        assertEquals(24.0, result.getValue(), 0.0001);
        assertEquals("INCHES", result.getUnit());

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    // DIVISION TESTS

    @Test
    void givenTwoQuantities_whenDividing_thenReturnDimensionlessResult() {

        QuantityDTO first =
                new QuantityDTO(6.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(2.0, "FEET", "LENGTH");

        double result = service.divide(first, second);

        assertEquals(3.0, result, 0.0001);

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void givenZeroDivisor_whenDividing_thenThrowExceptionAndSaveError() {

        QuantityDTO first =
                new QuantityDTO(6.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(0.0, "FEET", "LENGTH");

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.divide(first, second)
        );

        ArgumentCaptor<QuantityMeasurementEntity> captor =
                ArgumentCaptor.forClass(QuantityMeasurementEntity.class);

        verify(repository).save(captor.capture());

        assertTrue(captor.getValue().isError());
        assertEquals("DIVIDE", captor.getValue().getOperation());
    }

    // VALIDATION AND UNSUPPORTED OPERATION TESTS

    @Test
    void givenNullQuantity_whenConverting_thenThrowException() {

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.convert(null, "INCHES")
        );

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void givenDifferentMeasurementTypes_whenAdding_thenThrowException() {

        QuantityDTO first =
                new QuantityDTO(2.0, "FEET", "LENGTH");

        QuantityDTO second =
                new QuantityDTO(3.0, "KILOGRAM", "WEIGHT");

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.add(first, second)
        );

        verify(repository).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void givenTemperatureQuantities_whenAdding_thenThrowExceptionAndSaveError() {

        QuantityDTO first =
                new QuantityDTO(25.0, "CELSIUS", "TEMPERATURE");

        QuantityDTO second =
                new QuantityDTO(10.0, "CELSIUS", "TEMPERATURE");

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.add(first, second)
        );

        ArgumentCaptor<QuantityMeasurementEntity> captor =
                ArgumentCaptor.forClass(QuantityMeasurementEntity.class);

        verify(repository).save(captor.capture());

        assertTrue(captor.getValue().isError());
        assertEquals("ADD", captor.getValue().getOperation());
    }

    // REPOSITORY FAILURE TESTS

    @Test
    void givenRepositoryFailure_whenSavingSuccessfulOperation_thenThrowException() {

        QuantityDTO input =
                new QuantityDTO(3.0, "FEET", "LENGTH");

        doThrow(new RuntimeException("Disk unavailable"))
                .when(repository)
                .save(any(QuantityMeasurementEntity.class));

        QuantityMeasurementException exception =
                assertThrows(
                        QuantityMeasurementException.class,
                        () -> service.convert(input, "INCHES")
                );

        assertTrue(exception.getMessage().contains("Unable to save"));
    }

    @Test
    void givenRepositoryFailure_whenSavingErrorHistory_thenPreserveOriginalFailure() {

        QuantityDTO first =
                new QuantityDTO(25.0, "CELSIUS", "TEMPERATURE");

        QuantityDTO second =
                new QuantityDTO(10.0, "CELSIUS", "TEMPERATURE");

        doThrow(new RuntimeException("Disk unavailable"))
                .when(repository)
                .save(any(QuantityMeasurementEntity.class));

        QuantityMeasurementException exception =
                assertThrows(
                        QuantityMeasurementException.class,
                        () -> service.add(first, second)
                );

        assertTrue(
                exception.getMessage().contains("ADD operation failed")
        );

        assertEquals(1, exception.getSuppressed().length);
    }
}
