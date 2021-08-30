package com.shipengine;

import java.util.HashMap;
import java.util.List;

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

    public HashMap validateAddresses(List address, Config config) {
        // TODO: add in merge where config is passed in and fix config param to be seperate args instead of an obj
        Config newConfig = this.config.merge(config);
        InternalClient client = new InternalClient();
        try {
            return client.post(
                    "/v1/addresses/validate",
                    address,
                    config
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap validateAddresses(List address) {
        InternalClient client = new InternalClient();
        try {
            return client.post(
                    "/v1/addresses/validate",
                    address,
                    config
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String listCarriers() {
        return config.getBaseUrl();
    }

    public String listCarriers(Config config) {
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
