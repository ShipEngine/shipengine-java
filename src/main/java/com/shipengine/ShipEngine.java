package com.shipengine;

import com.shipengine.Config;

public class ShipEngine {
  Config config;

  public ShipEngine(String apiKey) {
    config = new Config(apiKey);
  }

  public String validateAddresses() {
    return config.baseURL();
  }

  public String listCarriers() {
    return config.baseURL();
  }

  public String trackUsingCarrierCodeAndTrackingNumber() {
    return config.baseURL();
  }

  public String trackUsingLabelId() {
    return config.baseURL();
  }

  public String createLabelFromShipmentDetails() {
    return config.baseURL();
  }

  public String createLabelFromRate() {
    return config.baseURL();
  }

  public String voidLabelWithLabelId() {
    return config.baseURL();
  }

  public String getRatesWithShipmentDetails() {
    return config.baseURL();
  }
}
