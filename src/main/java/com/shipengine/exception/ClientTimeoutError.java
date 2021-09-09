package com.shipengine.exception;

public class ClientTimeoutError extends ShipEngineException {

    public int retryAfter;

    public ErrorSource source;

    public String requestID;

    public ClientTimeoutError(
            String requestID,
            String source,
            int retryAfter
    ) {
        super(
                String.format("The request took longer than the %s seconds allowed.", retryAfter),
                requestID,
                ErrorSource.valueOf(source.toUpperCase()),
                ErrorType.SYSTEM,
                ErrorCode.TIMEOUT,
                "https://www.shipengine.com/docs/rate-limits"
        );
    }
}
