package com.shipengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipEngine {
    private InternalClient client = new InternalClient();
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

    /**
     * Address validation ensures accurate addresses and can lead to reduced shipping costs by preventing address
     * correction surcharges. ShipEngine cross-references multiple databases to validate addresses and identify
     * potential deliverability issues.
     * See: https://shipengine.github.io/shipengine-openapi/#operation/validate_address
     *
     * @param address A list of HashMaps where each HashMap contains the address data to be validated.
     * @return The response from ShipEngine API including the validated and normalized address.
     */
    public List validateAddresses(List<HashMap<String, String>> address) {
        Config mergedConfig = this.config.merge();
        List<HashMap<String, String>> apiResponse = new ArrayList<>();
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

    /**
     * Address validation ensures accurate addresses and can lead to reduced shipping costs by preventing address
     * correction surcharges. ShipEngine cross-references multiple databases to validate addresses and identify
     * potential deliverability issues.
     * See: https://shipengine.github.io/shipengine-openapi/#operation/validate_address
     *
     * @param address A list of HashMaps where each HashMap contains the address data to be validated.
     * @param config  Method level configuration to set new values for properties of the
     *                global ShipEngineConfig object that will only affect the current request, not all requests.
     * @return The response from ShipEngine API including the validated and normalized address.
     */
    public List validateAddresses(List<HashMap<String, String>> address, Map<String, Object> config) {
        Config mergedConfig = this.config.merge(config);
        List<HashMap<String, String>> apiResponse = new ArrayList<>();
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

    /**
     * Void label with a Label ID.
     * See: https://shipengine.github.io/shipengine-openapi/#operation/void_label
     *
     * @param labelId The label_id of the label you wish to void.
     * @return The response from ShipEngine API confirming the label was successfully voided or
     * unable to be voided.
     */
    public Map voidLabelWithLabelId(String labelId) {
        Config mergedConfig = this.config.merge();
        Map apiResponse = new HashMap<>();
        try {
            apiResponse = client.get(
                    String.format("/v1/labels/%s/void", labelId),
                    mergedConfig
            );
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    /**
     * Void label with a Label ID.
     * See: https://shipengine.github.io/shipengine-openapi/#operation/void_label
     *
     * @param labelId The label_id of the label you wish to void.
     * @param config  Method level configuration to set new values for properties of the
     *                global ShipEngineConfig object that will only affect the current request, not all requests.
     * @return The response from ShipEngine API confirming the label was successfully voided or unable to be voided.
     */
    public Map voidLabelWithLabelId(String labelId, Map<String, Object> config) {
        Config mergedConfig = this.config.merge(config);
        Map apiResponse = new HashMap<>();
        try {
            apiResponse = client.get(
                    String.format("/v1/labels/%s/void", labelId),
                    mergedConfig
            );
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    public String getRatesWithShipmentDetails() {
        return config.getBaseUrl();
    }
}
