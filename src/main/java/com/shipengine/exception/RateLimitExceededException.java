package com.shipengine.exception;

import java.net.URL;

/**
 * This error occurs when a request to ShipEngine API is blocked due to the rate
 * limit being exceeded.
 */
public class RateLimitExceededException extends ShipEngineException {
    /**
     * The amount of time (in milliseconds) to wait before retrying the request.
     */
    private int retryAfter;

    public int getRetryAfter() {
        return retryAfter;
    }

    private void setRetryAfter(int retryAfter) {
        this.retryAfter = retryAfter;
    }

    public RateLimitExceededException(
            String requestID, ErrorSource source, int retryAfter
    ) {
        super(
                "You have exceeded the rate limit.",
                requestID,
                source,
                ErrorType.SYSTEM,
                ErrorCode.RATE_LIMIT_EXCEEDED,
                new URL("https://www.shipengine.com/docs/rate-limits")
        );
        this.setRetryAfter(retryAfter);
    }
}
