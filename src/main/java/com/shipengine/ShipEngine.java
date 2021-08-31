package com.shipengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List validateAddresses(List<HashMap<String , String>> address) {
        Config mergedConfig = this.config.merge();
        List<HashMap<String , String>> apiResponse = new ArrayList<>();
        InternalClient client = new InternalClient();
        try {
            apiResponse = client.post(
                    "/v1/addresses/validate",
                    address,
                    mergedConfig
            );
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    public List validateAddresses(List<HashMap<String , String>> address, Map<String, Object> config) {
        Config mergedConfig = this.config.merge(config);
        List<HashMap<String , String>> apiResponse = new ArrayList<>();
        InternalClient client = new InternalClient();
        try {
            apiResponse = client.post(
                    "/v1/addresses/validate",
                    address,
                    mergedConfig
            );
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    public Map listCarriers() {
        Config mergedConfig = this.config.merge();
        Map apiResponse = new HashMap<>();
        InternalClient client = new InternalClient();
        try {
            apiResponse = client.get(
                    "/v1/carriers",
                    mergedConfig
            );
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    public Map listCarriers(Map<String, Object> config) {
        Config mergedConfig = this.config.merge(config);
        Map apiResponse = new HashMap<>();
        InternalClient client = new InternalClient();
        try {
            apiResponse = client.get(
                    "/v1/carriers",
                    mergedConfig
            );
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
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
