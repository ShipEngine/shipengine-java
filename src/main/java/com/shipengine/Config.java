package com.shipengine;

public class Config {
    /*
     * Your ShipEngine API key. This can be a production or sandbox key. Sandbox
     * keys start with "TEST_".
     */
    String apiKey;

    /*
     * The URL of the ShipEngine API. You can usually leave this unset and it will
     * default to our public API.
     */
    String baseUrl = "https://api.shipengine.com/";

    /*
     * Some ShipEngine API endpoints return paged data. This lets you control the
     * number of items returned per request. Larger numbers will use more memory but
     * will require fewer HTTP requests.
     *
     * Defaults to 50.
     */
    int pageSize;

    /*
     * If the ShipEngine client receives a rate limit error it can automatically
     * retry the request after a few seconds. This setting lets you control how many
     * times it will retry before giving up.
     *
     * Defaults to 1, which means up to 2 attempts will be made (the original
     * attempt, plus one retry).
     */
    int retries;

    /*
     * The maximum amount of time (in milliseconds) to wait for a response from the
     * ShipEngine server.
     *
     * Defaults to 5000 (5 seconds).
     */
    int timeout;

    public Config(String apiKey) {
        this.apiKey = apiKey;
        this.timeout = 5000;
        this.retries = 1;
        this.pageSize = 50;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getRetries() {
        return retries;
    }

    public int getPageSize() {
        return pageSize;
    }
}
