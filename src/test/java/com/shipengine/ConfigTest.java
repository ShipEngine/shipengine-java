package com.shipengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for Config.
 */
public class ConfigTest {
  /**
   * Should allow the config to just be the API key
   */
  @Test
  public void shouldAllowApiKeyOnlyConstructor() {
    ShipEngine client = new ShipEngine("test");

    assertEquals(client.getConfig().getApiKey(), "test");
  }

  /**
   * Should return the Global Config object when calling the merge() method
   * with no arguments.
   */
  @Test
  public void shouldReturnGlobalConfigFromEmptyMergeCall() {
    ShipEngine client = new ShipEngine("");

    assertEquals(client.getConfig().merge().getClass(),  Config.class);
  }

  /**
   * Should allow method level configuration.
   */
  @Test
  public void shouldAllowMethodLevelConfig() {
    ShipEngine client = new ShipEngine("");

    Map<String, Object> newConfig = new HashMap<String, Object>() {{
      put("retries", 3);
    }};
    Map result = client.listCarriers(newConfig);
    assertEquals(result.getClass(), HashMap.class);
  }

  /**
   * Does not allow an empty string
   */
  @Test
  public void shouldNotAllowAnEmptyApiKeyString() {
    new ShipEngine("");
  }
}
