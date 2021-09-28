package com.shipengine.exception;

import org.junit.Test;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for exceptions used in the ShipEngine SDK.
 */
public class ExceptionTest {
    private final List<String> stubErrorValues = List.of(
            "Testing that a ShipEngine Exception can be raised.",
            "req_00as89d7yfa0s8dt7f",
            "shipengine",
            "5000",
            "validation",
            "invalid_field_value",
            "error",
            "address_not_found"
    );

    private void throwClientTimeoutException() {
        throw new ClientTimeoutException(
                stubErrorValues.get(1),
                stubErrorValues.get(2),
                parseInt(stubErrorValues.get(3))
        );
    }

    private void throwFieldValueRequiredException() {
        throw new FieldValueRequiredException("apiKey");
    }

    private void throwRateLimitExceededException() {
        throw new RateLimitExceededException(
                stubErrorValues.get(1),
                stubErrorValues.get(2),
                parseInt(stubErrorValues.get(3))
        );
    }

    private void throwShipEngineException() {
        throw new ShipEngineException(
                stubErrorValues.get(0),
                stubErrorValues.get(1),
                stubErrorValues.get(2),
                stubErrorValues.get(6),
                stubErrorValues.get(7)
        );
    }

    private void throwValidationException() {
        throw new ValidationException(
                stubErrorValues.get(0),
                stubErrorValues.get(2),
                stubErrorValues.get(4),
                stubErrorValues.get(5)
        );
    }

    @Test
    public void testClientTimeoutException() {
        try {
            throwClientTimeoutException();
        } catch (ClientTimeoutException err) {
            assertEquals(ClientTimeoutException.class, err.getClass());
        }
    }

    @Test
    public void testFieldValueRequiredException() {
        try {
            throwFieldValueRequiredException();
        } catch (FieldValueRequiredException err) {
            assertEquals(FieldValueRequiredException.class, err.getClass());
        }
    }

    @Test
    public void testRateLimitExceededException() {
        try {
            throwRateLimitExceededException();
        } catch (RateLimitExceededException err) {
            assertEquals(RateLimitExceededException.class, err.getClass());
        }
    }

    @Test
    public void testShipEngineException() {
        try {
            throwShipEngineException();
        } catch (ShipEngineException err) {
            assertEquals(ShipEngineException.class, err.getClass());
        }
    }

    @Test
    public void testValidationException() {
        try {
            throwValidationException();
        } catch (ValidationException err) {
            assertEquals(ValidationException.class, err.getClass());
        }
    }
}
