package com.shipengine.exception;

/**
 * This error occurs when a field has been set to an invalid value.
 */
public class InvalidFieldValueException extends RuntimeException {
    /**
     * The name of the invalid field.
     */
    String fieldName;

    /**
     * The value of the invalid field.
     */
    String fieldValue;
}
