package com.shipengine.exception;

public class ValidationException extends ShipEngineException {

    public ValidationException(
            String message,
            String requestID,
            ErrorSource source,
            ErrorType type,
            ErrorCode code,
            String url
    ) {
        super(message, requestID, source, type, code, url);
    }
}
