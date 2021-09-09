package com.shipengine.exception;

/**
 * This error occurs when a request to ShipEngine API is blocked due to the rate
 * limit being exceeded.
 */
public class RateLimitExceededException extends ShipEngineException {
    /**
     * The amount of time (in milliseconds) to wait before retrying the request.
     */
    public int retryAfter;

    public int getRetryAfter() {
        return retryAfter;
    }

    private void setRetryAfter(int retryAfter) {
        this.retryAfter = retryAfter;
    }

    public RateLimitExceededException(
            String requestID, String source, int retryAfter
    ) {
        super(
                "You have exceeded the rate limit.",
                requestID,
                ErrorSource.valueOf(source.toUpperCase()),
                ErrorType.SYSTEM,
                ErrorCode.RATE_LIMIT_EXCEEDED,
                "https://www.shipengine.com/docs/rate-limits"
        );
        this.setRetryAfter(retryAfter);
    }
}
