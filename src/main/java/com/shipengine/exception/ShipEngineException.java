package com.shipengine.exception;

import java.net.URL;

/**
 * An error thrown by the ShipEngine SDK. All other SDK errors inherit from this
 * class.
 */
public class ShipEngineException extends RuntimeException {
    enum ErrorSource {
        LOW, MEDIUM, HIGH
    }

    enum ErrorType {
        LOW, MEDIUM, HIGH
    }

    enum ErrorCode {
        LOW, MEDIUM, HIGH
    }

    /**
     * If the error came from the ShipEngine server (as opposed to a client-side
     * error) then this is the unique ID of the HTTP request that returned the
     * error. You can use this ID when contacting ShipEngine support for help.
     */
    String requestID;

    /**
     * Indicates where the error originated. This lets you know whether you should
     * contact ShipEngine for support or if you should contact the carrier or
     * marketplace instead.
     *
     * @see https://www.shipengine.com/docs/errors/codes/#error-source
     */
    ErrorSource source;

    /**
     * Indicates the type of error that occurred, such as a validation error, a
     * security error, etc.
     *
     * @see https://www.shipengine.com/docs/errors/codes/#error-type
     */
    ErrorType type;

    /**
     * A code that indicates the specific error that occurred, such as missing a
     * required field, an invalid address, a timeout, etc.
     *
     * @see https://www.shipengine.com/docs/errors/codes/#error-code
     */
    ErrorCode code;

    /**
     * Some errors include a URL that you can visit to learn more about the error,
     * find out how to resolve it, or get support.
     */
    URL url;

    public ShipEngineException() {

    }
}
