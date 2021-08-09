package com.shipengine;

import com.shipengine.Config;

public class ShipEngine {
    Config config;

    public ShipEngine(String apiKey) {
        config = new Config(apiKey);
    }

    public String validateAddresses() {
        return config.baseUrl();
    }

    public String listCarriers() {
        return config.baseUrl();
    }

    public String trackUsingCarrierCodeAndTrackingNumber() {
        return config.baseUrl();
    }

    public String trackUsingLabelId() {
        return config.baseUrl();
    }

    public String createLabelFromShipmentDetails() {
        return config.baseUrl();
    }

    public String createLabelFromRate() {
        return config.baseUrl();
    }

    public String voidLabelWithLabelId() {
        return config.baseUrl();
    }

    public String getRatesWithShipmentDetails() {
        return config.baseUrl();
    }
}
