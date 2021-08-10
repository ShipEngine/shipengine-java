package com.shipengine.exception;

/**
 * This error occurs when a request to ShipEngine API is blocked due to the rate
 * limit being exceeded.
 */
public class RateLimitExceededException extends ShipEngineException {
    /**
     * The amount of time (in milliseconds) to wait before retrying the request.
     */
    int retryAfter;

    public RateLimitExceededException(String requestID, ErrorSource source, int retryAfter) {
        this.retryAfter = retryAfter;
    }
}
