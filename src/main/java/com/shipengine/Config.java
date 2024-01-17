package com.shipengine;

import com.shipengine.exception.ValidationException;
import com.shipengine.util.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
    private String apiKey;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String baseUrl = Constants.BASE_URL;
    private int pageSize = 5000;
    private int retries = 1;

    /**
     * Client timeout in milliseconds.
     */
    private int timeout = 60000;

    public Config(Map<String, Object> config) {
        if (config.containsKey("apiKey")) {
            setApiKey(config.get("apiKey").toString());
        } else {
            setApiKey("");
        }

        if (config.containsKey("baseUrl")) {
            setBaseUrl(config.get("baseUrl").toString());
        }

        if (config.containsKey("timeout")) {
            setTimeout(Integer.parseInt(config.get("timeout").toString()));
        }

        if (config.containsKey("retries")) {
            setRetries(Integer.parseInt(config.get("retries").toString()));
        }

        if (config.containsKey("pageSize")) {
            setPageSize(Integer.parseInt(config.get("pageSize").toString()));
        }
    }

    public Config(String apiKey) {
        setApiKey(apiKey);
    }

    public Config(String apiKey, int timeout, int retries, int pageSize) {
        setApiKey(apiKey);
        setTimeout(timeout);
        setRetries(retries);
        setPageSize(pageSize);
    }

    public Config(String apiKey, String baseUrl, int timeout, int retries, int pageSize) {
        setApiKey(apiKey);
        setBaseUrl(baseUrl);
        setTimeout(timeout);
        setRetries(retries);
        setPageSize(pageSize);
    }

    /*
     * The URL of the ShipEngine API. You can usually leave this unset and it will
     * default to our public API.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /*
     * Your ShipEngine API key. This can be a production or sandbox key. Sandbox
     * keys start with "TEST_".
     */
    public String getApiKey() {
        return apiKey;
    }

    /*
     * Set the ShipEngine API key.
     */
    public void setApiKey(String apiKey) throws ValidationException {
        Pattern regexPattern = Pattern.compile("[\\s]");
        Matcher matcher = regexPattern.matcher(apiKey);
        if (apiKey.length() == 0) {
            throw new ValidationException(
                    "A ShipEngine API key must be specified and cannot be empty or contain whitespace.",
                    "shipengine",
                    "validation",
                    "invalid_field_value"
            );
        } else if (matcher.matches()) {
            throw new ValidationException(
                    "A ShipEngine API key must be specified and cannot be empty or contain whitespace.",
                    "shipengine",
                    "validation",
                    "invalid_field_value"
            );
        } else {
            this.apiKey = apiKey;
        }
    }

    /*
     * The maximum amount of time (in milliseconds) to wait for a response from the
     * ShipEngine server.
     *
     * Defaults to 60000 (60 seconds).
     */
    public int getTimeout() {
        return timeout;
    }

    /*
     * Set the timeout (in milliseconds).
     */
    public void setTimeout(int timeout) {
        if (timeout == 0) {
            throw new ValidationException(
                    "The timeout value must be greater than zero and in milliseconds.",
                    "shipengine",
                    "validation",
                    "invalid_field_value"
            );
        }
        this.timeout = timeout;
    }

    /*
     * If the ShipEngine client receives a rate limit error it can automatically
     * retry the request after a few seconds. This setting lets you control how many
     * times it will retry before giving up.
     *
     * Defaults to 1, which means up to 2 attempts will be made (the original
     * attempt, plus one retry).
     */
    public int getRetries() {
        return retries;
    }

    /*
     * Set the retries.
     */
    public void setRetries(int retries) {
        if (retries == 0) {
            throw new ValidationException(
                    "The retries value must be greater than zero.",
                    "shipengine",
                    "validation",
                    "invalid_field_value"
            );
        }
        this.retries = retries;
    }

    /*
     * Some ShipEngine API endpoints return paged data. This lets you control the
     * number of items returned per request. Larger numbers will use more memory but
     * will require fewer HTTP requests.
     *
     * Defaults to 50.
     */
    public int getPageSize() {
        return pageSize;
    }

    /*
     * Set the page size.
     */
    public void setPageSize(int pageSize) {
        if (pageSize == 0) {
            throw new ValidationException(
                    "The pageSize value cannot be zero.",
                    "shipengine",
                    "validation",
                    "invalid_field_value"
            );
        }
        this.pageSize = pageSize;
    }

    public Config merge() {
        return this;
    }

    public Config merge(String apiKey) {
        return new Config(apiKey);
    }

    public Config merge(Map<String, Object> newConfig) {
        Map<String, Object> config = new HashMap<>();
        List<String> configKeys = Arrays.asList("apiKey", "timeout", "retries", "pageSize", "baseUrl");

        if (newConfig.isEmpty()) {
            return this;
        } else {
            if (newConfig.containsKey(configKeys.get(0))) {
                config.put(configKeys.get(0), newConfig.get(configKeys.get(0)));
            } else {
                config.put(configKeys.get(0), getApiKey());
            }

            if (newConfig.containsKey(configKeys.get(1))) {
                config.put(configKeys.get(1), newConfig.get(configKeys.get(1)));
            } else {
                config.put(configKeys.get(1), getTimeout());
            }

            if (newConfig.containsKey(configKeys.get(2))) {
                config.put(configKeys.get(2), newConfig.get(configKeys.get(2)));
            } else {
                config.put(configKeys.get(2), getRetries());
            }

            if (newConfig.containsKey(configKeys.get(3))) {
                config.put(configKeys.get(3), newConfig.get(configKeys.get(3)));
            } else {
                config.put(configKeys.get(3), getPageSize());
            }

            if (newConfig.containsKey(configKeys.get(4))) {
                config.put(configKeys.get(4), newConfig.get(configKeys.get(4)));
            } else {
                config.put(configKeys.get(4), getBaseUrl());
            }
        }
        return new Config(
                config.get(configKeys.get(0)).toString(),
                config.get(configKeys.get(4)).toString(),
                Integer.parseInt(config.get(configKeys.get(1)).toString()),
                Integer.parseInt(config.get(configKeys.get(2)).toString()),
                Integer.parseInt(config.get(configKeys.get(3)).toString())
        );
    }
}
