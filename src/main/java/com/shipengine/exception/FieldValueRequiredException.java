package com.shipengine.exception;

/**
 * This error occurs when a rquired field has not been set. This includes fields
 * that are conditionally required.
 */
public class FieldValueRequiredException extends RuntimeException {
  /**
   * The name of the invalid field.
   */
  String fieldName;

  /**
   * The value of the invalid field.
   */
  String fieldValue;
}
