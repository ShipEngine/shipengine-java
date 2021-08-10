package com.shipengine;

public class ShipEngine {
    private Config config;

    public ShipEngine(String apiKey) {
        this.config = new Config(apiKey);
    }

    public ShipEngine(String apiKey, int timeout, int retries, int pageSize) {
        this.config = new Config(apiKey, timeout, retries, pageSize);
    }

    public Config getConfig() {
        return config;
    }

    public String validateAddresses() {
        return config.getBaseUrl();
    }

    public String listCarriers() {
        return config.getBaseUrl();
    }

    public String trackUsingCarrierCodeAndTrackingNumber() {
        return config.getBaseUrl();
    }

    public String trackUsingLabelId() {
        return config.getBaseUrl();
    }

    public String createLabelFromShipmentDetails() {
        return config.getBaseUrl();
    }

    public String createLabelFromRate() {
        return config.getBaseUrl();
    }

    public String voidLabelWithLabelId() {
        return config.getBaseUrl();
    }

    public String getRatesWithShipmentDetails() {
        return config.getBaseUrl();
    }
}
