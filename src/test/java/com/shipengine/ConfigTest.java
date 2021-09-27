package com.shipengine;

import com.shipengine.exception.ShipEngineException;
import com.shipengine.exception.ValidationException;
import com.shipengine.util.Constants;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Config.
 */
public class ConfigTest {
    /**
     * Should allow the config to just be the API key
     */
    @Test
    public void shouldAllowApiKeyOnlyConstructor() {
        ShipEngine client = new ShipEngine(Constants.API_KEY);

        assertEquals(Constants.API_KEY, client.getConfig().getApiKey());
    }

    @Test
    public void shouldNotAllowEmptyStringApiKey() {
        try {
            new ShipEngine("");
        } catch (ValidationException err) {
            assertEquals(ValidationException.class, err.getClass());
            assertEquals(
                    "A ShipEngine API key must be specified and cannot be empty or contain whitespace.",
                    err.getMessage()
            );
            assertEquals(ShipEngineException.ErrorSource.SHIPENGINE, err.getSource());
            assertEquals(ShipEngineException.ErrorType.VALIDATION, err.getType());
            assertEquals(ShipEngineException.ErrorCode.INVALID_FIELD_VALUE, err.getCode());
        }
    }

    @Test
    public void shouldNotAllowInvalidRetries() {
        try {
            new ShipEngine(Map.of(
                    "apiKey", Constants.API_KEY,
                    "retries", 0
                    ));
        } catch (ValidationException err) {
            assertEquals(ValidationException.class, err.getClass());
            assertEquals(
                    "The retries value must be greater than zero.",
                    err.getMessage()
            );
            assertEquals(ShipEngineException.ErrorSource.SHIPENGINE, err.getSource());
            assertEquals(ShipEngineException.ErrorType.VALIDATION, err.getType());
            assertEquals(ShipEngineException.ErrorCode.INVALID_FIELD_VALUE, err.getCode());
        }
    }

    @Test
    public void shouldNotAllowInvalidTimeout() {
        try {
            new ShipEngine(Map.of(
                    "apiKey", Constants.API_KEY,
                    "timeout", 0
            ));
        } catch (ValidationException err) {
            assertEquals(ValidationException.class, err.getClass());
            assertEquals(
                    "The timeout value must be greater than zero and in milliseconds.",
                    err.getMessage()
            );
            assertEquals(ShipEngineException.ErrorSource.SHIPENGINE, err.getSource());
            assertEquals(ShipEngineException.ErrorType.VALIDATION, err.getType());
            assertEquals(ShipEngineException.ErrorCode.INVALID_FIELD_VALUE, err.getCode());
        }
    }

    /**
     * Should return the Global Config object when calling the merge() method
     * with no arguments.
     */
    @Test
    public void shouldReturnGlobalConfigFromEmptyMergeCall() {
        ShipEngine client = new ShipEngine(Constants.API_KEY);

        assertEquals(Config.class, client.getConfig().merge().getClass());
    }

    /**
     * Should allow method level configuration.
     */
    @Test
    public void shouldAllowMethodLevelConfig() {
        ShipEngine client = new ShipEngine(Constants.API_KEY);

        Map<String, Object> newConfig = new HashMap<>();
        newConfig.put("retries", 3);
        Map result = client.listCarriers(newConfig);
        assertEquals(result.getClass(), HashMap.class);
    }
}
