package com.shipengine;

import com.shipengine.Config;

public class ShipEngine
{
    Config config;

    public ShipEngine()
    {
        config = new Config();
    }

    public String validateAddresses() {
      return config.sayHi();
    }

    public String listCarriers() {
      return config.sayHi();
    }

    public String trackUsingCarrierCodeAndTrackingNumber() {
      return config.sayHi();
    }

    public String trackUsingLabelId() {
      return config.sayHi();
    }

    public String createLabelFromShipmentDetails() {
      return config.sayHi();
    }

    public String createLabelFromRate() {
      return config.sayHi();
    }

    public String voidLabelWithLabelId() {
      return config.sayHi();
    }

    public String getRatesWithShipmentDetails() {
      return config.sayHi();
    }
}
