package com.shipengine;

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
