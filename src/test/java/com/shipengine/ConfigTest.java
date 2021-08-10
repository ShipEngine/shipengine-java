package com.shipengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for Config.
 */
public class ConfigTest {
  /**
   * Should allow the config to just be the API key
   */
  @Test
  public void shouldAllowApiKeyOnly() {
    ShipEngine client = new ShipEngine("test");

    assertEquals(client.getConfig().getApiKey(), "test");
  }
}
