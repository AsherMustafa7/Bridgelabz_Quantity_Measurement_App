
package com.asher.quantitymeasurement.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementExceptionTest {

    @Test
    void givenMessage_whenCreatingException_thenPreserveMessage() {
        QuantityMeasurementException exception =
                new QuantityMeasurementException("Measurement failed");

        assertEquals("Measurement failed", exception.getMessage());
    }

    @Test
    void givenMessageAndCause_whenCreatingException_thenPreserveBoth() {
        RuntimeException cause =
                new RuntimeException("Original error");

        QuantityMeasurementException exception =
                new QuantityMeasurementException(
                        "Measurement failed", cause);

        assertEquals("Measurement failed", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
