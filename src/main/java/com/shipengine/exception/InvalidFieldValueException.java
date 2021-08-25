package com.shipengine.exception;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This error occurs when a field has been set to an invalid value.
 */
public class InvalidFieldValueException extends ShipEngineException {
    /**
     * The name of the invalid field.
     */
    String fieldName;

    /**
     * The value of the invalid field.
     */
    String fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public InvalidFieldValueException(
            String fieldName,
            String fieldValue
    ) throws MalformedURLException {
        super(
                String.format("%s - %s was provided.", fieldName, fieldValue),
                "",
                ErrorSource.SHIPENGINE,
                ErrorType.VALIDATION,
                ErrorCode.INVALID_FIELD_VALUE,
                new URL("https://www.shipengine.com/docs/")
        );
        this.setFieldName(fieldName);
        this.setFieldValue(fieldValue);
    }
}
