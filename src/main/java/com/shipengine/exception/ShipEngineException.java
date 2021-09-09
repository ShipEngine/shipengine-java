package com.shipengine.exception;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

/**
 * An error thrown by the ShipEngine SDK. All other SDK errors inherit from this
 * class.
 */
public class ShipEngineException extends RuntimeException {
    private static HashSet<String> getEnums() {
        HashSet<String> values = new HashSet<>();

        for (ErrorSource k : ErrorSource.values()) {
            values.add(k.name());
        }

        for (ErrorType p : ErrorType.values()) {
            values.add(p.name());
        }

        for (ErrorCode c : ErrorCode.values()) {
            values.add(c.name());
        }

        return values;
    }

    enum ErrorSource {
        CARRIER,
        ORDER_SOURCE,
        SHIPENGINE
    }

    enum ErrorType {
        ACCOUNT_STATUS,
        AUTHORIZATION,
        BUSINESS_RULES,
        ERROR,
        SECURITY,
        SYSTEM,
        VALIDATION
    }

    enum ErrorCode {
        ADDRESS_NOT_FOUND,
        AUTO_FUND_NOT_SUPPORTED,
        BATCH_CANNOT_BE_MODIFIED,
        CARRIER_CONFLICT,
        CARRIER_NOT_CONNECTED,
        CARRIER_NOT_SUPPORTED,
        CONFIRMATION_NOT_SUPPORTED,
        FIELD_CONFLICT,
        FIELD_VALUE_REQUIRED,
        FORBIDDEN,
        IDENTIFIER_CONFLICT,
        IDENTIFIER_MUST_MATCH,
        INCOMPATIBLE_PAIRED_LABELS,
        INVALID_ADDRESS,
        INVALID_BILLING_PLAN,
        INVALID_CHARGE_EVENT,
        INVALID_FIELD_VALUE,
        INVALID_IDENTIFIER,
        INVALID_STATUS,
        INVALID_STRING_LENGTH,
        LABEL_IMAGES_NOT_SUPPORTED,
        METER_FAILURE,
        MINIMUM_POSTAL_CODE_VERIFICATION_FAILED,
        NOT_FOUND,
        PARTIALLY_VERIFIED_TO_PREMISE_LEVEL,
        RATE_LIMIT_EXCEEDED,
        REQUEST_BODY_REQUIRED,
        RETURN_LABEL_NOT_SUPPORTED,
        SUBSCRIPTION_INACTIVE,
        TERMS_NOT_ACCEPTED,
        TIMEOUT,
        TRACKING_NOT_SUPPORTED,
        TRIAL_EXPIRED,
        UNAUTHORIZED,
        UNSPECIFIED,
        VERIFICATION_CONFLICT,
        WAREHOUSE_CONFLICT,
        WEBHOOK_EVENT_TYPE_CONFLICT
    }

    /**
     * If the error came from the ShipEngine server (as opposed to a client-side
     * error) then this is the unique ID of the HTTP request that returned the
     * error. You can use this ID when contacting ShipEngine support for help.
     */
    private String requestID;

    /**
     * Indicates where the error originated. This lets you know whether you should
     * contact ShipEngine for support or if you should contact the carrier or
     * marketplace instead.
     *
     * @see <a href="https://www.shipengine.com/docs/errors/codes/#error-source">...</a>
     */
    private ErrorSource source;

    /**
     * Indicates the type of error that occurred, such as a validation error, a
     * security error, etc.
     *
     * @see <a href="https://www.shipengine.com/docs/errors/codes/#error-type">...</a>
     */
    private ErrorType type;

    /**
     * A code that indicates the specific error that occurred, such as missing a
     * required field, an invalid address, a timeout, etc.
     *
     * @see <a href="https://www.shipengine.com/docs/errors/codes/#error-code">...</a>
     */
    private ErrorCode code;

    private String getRequestID() {
        return requestID;
    }

    private ErrorSource getSource() {
        return source;
    }

    private ErrorType getType() {
        return type;
    }

    private ErrorCode getCode() {
        return code;
    }

    private URL getUrl() {
        return url;
    }

    private void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    private void setSource(ErrorSource source) {
        this.source = source;
    }

    private void setType(ErrorType type) {
        this.type = type;
    }

    private void setCode(ErrorCode code) {
        this.code = code;
    }

    private void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException err) {
            err.printStackTrace();
        }
    }

    /**
     * Some errors include a URL that you can visit to learn more about the error,
     * find out how to resolve it, or get support.
     */
    URL url;

    public ShipEngineException(
            String message,
            String requestID,
            ErrorSource source,
            ErrorType type,
            ErrorCode code,
            String url
    ) {
        super(message);
        this.setRequestID(requestID);
        this.setSource(source);
        this.setType(type);
        this.setCode(code);
        this.setUrl(url);
    }

    public ShipEngineException(
            String message,
            String requestID,
            String source,
            String type,
            String code
    ) {
        super(message);
        this.setRequestID(requestID);
        this.setSource(ErrorSource.valueOf(source.toUpperCase()));
        this.setType(ErrorType.valueOf(type.toUpperCase()));
        this.setCode(ErrorCode.valueOf(code));
    }
}
