package com.shipengine.exception;

/**
 * This error occurs when a required field has not been set. This includes fields
 * that are conditionally required.
 */
public class FieldValueRequiredException extends RuntimeException {
    /**
     * The name of the invalid field.
     */
    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldValueRequiredException(
            String fieldName
    ) {
        super(String.format("%s is a required field.", fieldName));
    }
}
