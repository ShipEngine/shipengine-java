package com.shipengine;

import java.util.HashMap;

public class Config {
    private String apiKey;
    private String baseUrl = "https://api.shipengine.com/";
    private int pageSize = 5000;
    private int retries = 1;
    private int timeout = 50;

    public Config(String apiKey) {
        setApiKey(apiKey);
    }

    public Config(String apiKey, int timeout, int retries, int pageSize) {
        setApiKey(apiKey);
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
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /*
     * The maximum amount of time (in milliseconds) to wait for a response from the
     * ShipEngine server.
     *
     * Defaults to 5000 (5 seconds).
     */
    public int getTimeout() {
        return timeout;
    }

    /*
     * Set the timeout (in milliseconds).
     */
    public void setTimeout(int timeout) {
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
        this.pageSize = pageSize;
    }

    public Config merge() {
        return this;
    }

    public Config merge(String apiKey) {
        return new Config(apiKey);
    }

    public Config merge(String apiKey, int timeout, int retries, int pageSize) {
        return new Config(
                apiKey,
                timeout,
                retries,
                pageSize
        );
    }
}
