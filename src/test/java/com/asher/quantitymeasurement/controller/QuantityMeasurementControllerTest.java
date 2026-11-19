
package com.asher.quantitymeasurement.controller;

import com.asher.quantitymeasurement.dto.QuantityDTO;
import com.asher.quantitymeasurement.exception.QuantityMeasurementException;
import com.asher.quantitymeasurement.service.IQuantityMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuantityMeasurementControllerTest {

    private IQuantityMeasurementService service;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        service = mock(IQuantityMeasurementService.class);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    void shouldRejectNullService() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityMeasurementController(null)
        );
    }

    @Test
    void shouldConvertQuantity() {
        QuantityDTO input = new QuantityDTO(10, "FEET", "LENGTH");
        QuantityDTO output = new QuantityDTO(120, "INCHES", "LENGTH");

        when(service.convert(input, "INCHES")).thenReturn(output);

        assertEquals(
                "Conversion Result: 120.0 INCHES",
                controller.convert(input, "INCHES")
        );

        verify(service).convert(input, "INCHES");
    }

    @Test
    void shouldCompareQuantities() {
        QuantityDTO first = new QuantityDTO(1, "FEET", "LENGTH");
        QuantityDTO second = new QuantityDTO(12, "INCHES", "LENGTH");

        when(service.compare(first, second)).thenReturn(true);

        assertEquals(
                "Comparison Result: Equal",
                controller.compare(first, second)
        );
    }

    @Test
    void shouldAddQuantities() {
        QuantityDTO first = new QuantityDTO(2, "FEET", "LENGTH");
        QuantityDTO second = new QuantityDTO(3, "FEET", "LENGTH");
        QuantityDTO result = new QuantityDTO(5, "FEET", "LENGTH");

        when(service.add(first, second)).thenReturn(result);

        assertEquals(
                "Addition Result: 5.0 FEET",
                controller.add(first, second)
        );
    }

    @Test
    void shouldAddQuantitiesWithTargetUnit() {
        QuantityDTO first = new QuantityDTO(2, "FEET", "LENGTH");
        QuantityDTO second = new QuantityDTO(12, "INCHES", "LENGTH");
        QuantityDTO result = new QuantityDTO(36, "INCHES", "LENGTH");

        when(service.add(first, second, "INCHES")).thenReturn(result);

        assertEquals(
                "Addition Result: 36.0 INCHES",
                controller.add(first, second, "INCHES")
        );
    }

    @Test
    void shouldSubtractQuantities() {
        QuantityDTO first = new QuantityDTO(5, "FEET", "LENGTH");
        QuantityDTO second = new QuantityDTO(2, "FEET", "LENGTH");
        QuantityDTO result = new QuantityDTO(3, "FEET", "LENGTH");

        when(service.subtract(first, second)).thenReturn(result);

        assertEquals(
                "Subtraction Result: 3.0 FEET",
                controller.subtract(first, second)
        );
    }

    @Test
    void shouldDivideQuantities() {
        QuantityDTO first = new QuantityDTO(10, "FEET", "LENGTH");
        QuantityDTO second = new QuantityDTO(2, "FEET", "LENGTH");

        when(service.divide(first, second)).thenReturn(5.0);

        assertEquals(
                "Division Result: 5.0",
                controller.divide(first, second)
        );
    }

    @Test
    void shouldHandleServiceException() {
        QuantityDTO input = new QuantityDTO(10, "FEET", "LENGTH");

        when(service.convert(input, "UNKNOWN"))
                .thenThrow(new QuantityMeasurementException(
                        "Invalid target unit."));

        assertEquals(
                "Error: Invalid target unit.",
                controller.convert(input, "UNKNOWN")
        );
    }
}
