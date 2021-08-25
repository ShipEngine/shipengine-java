package com.shipengine.exception;

import java.net.URL;

public class ValidationException extends ShipEngineException {

    public ValidationException(String message, String requestID, ErrorSource source, ErrorType type, ErrorCode code, URL url) {
        super(message, requestID, source, type, code, url);
    }
}
