package com.shipengine.exception;

public class ClientTimeoutException extends ShipEngineException {

    private int retryAfter;

    private ErrorSource source;

    private String requestID;

    public int getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(int retryAfter) {
        this.retryAfter = retryAfter;
    }

    public ErrorSource getSource() {
        return source;
    }

    public void setSource(ErrorSource source) {
        this.source = source;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public ClientTimeoutException(
            String requestID,
            String source,
            int retryAfter
    ) {
        super(
                String.format("The request took longer than the %s milliseconds allowed.", retryAfter),
                requestID,
                ErrorSource.valueOf(source.toUpperCase()),
                ErrorType.SYSTEM,
                ErrorCode.TIMEOUT,
                "https://www.shipengine.com/docs/rate-limits"
        );
    }
}
