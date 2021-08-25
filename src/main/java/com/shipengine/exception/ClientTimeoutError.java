package com.shipengine.exception;

import java.net.MalformedURLException;
import java.net.URL;

public class ClientTimeoutError extends ShipEngineException {

    int retryAfter;

    ErrorSource source;

    String requestID;

    public ClientTimeoutError(String requestID, ErrorSource source, int retryAfter) throws MalformedURLException {
        super(
                String.format("The request took longer than the %s seconds allowed.", retryAfter),
                requestID,
                source,
                ErrorType.SYSTEM,
                ErrorCode.TIMEOUT,
                new URL("https://www.shipengine.com/docs/rate-limits")
        );
    }
}
