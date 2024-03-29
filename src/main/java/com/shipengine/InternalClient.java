package com.shipengine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shipengine.exception.ClientTimeoutException;
import com.shipengine.exception.RateLimitExceededException;
import com.shipengine.exception.ShipEngineException;
import com.shipengine.ErrorResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InternalClient {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Enumeration of frequently used HTTP verbs to be used when making requests with the client.
     */
    private enum HttpVerbs {
        GET,
        POST,
        PUT,
        DELETE
    }

    /**
     * This is the request loop that manages the clients retry logic when interacting with ShipEngine API.
     * This method takes in the request body as a Map or HashMap.
     *
     * @param httpMethod The HTTP Verb to set as the HTTP Method in the request.
     * @param endpoint   A string representation of the target API endpoint for the request.
     * @param body       A Map or HashMap that contains the request body contents.
     * @param config     The global Config object for the ShipEngine SDK.
     * @return Map The response from ShipEngine API serialized into a Map/HashMap.
     */
    private Map<String, String> requestLoop(
            String httpMethod,
            String endpoint,
            Map body,
            Config config
    ) throws InterruptedException {
        int retry = 0;
        Map<String, String> apiResponse = Map.of();
        while (retry <= config.getRetries()) {
            try {
                apiResponse = sendHttpRequest(
                        httpMethod,
                        endpoint,
                        body,
                        config
                );
            } catch (Exception err) {
                if ((retry < config.getRetries()) &&
                        (err instanceof RateLimitExceededException) &&
                        (config.getTimeout() > ((RateLimitExceededException) err).getRetryAfter())) {
                    try {
                        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(((RateLimitExceededException) err).getRetryAfter());
                        retry++;
                        continue;
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw err;
                }
            }
            return apiResponse;
        }
        return apiResponse;
    }

    /**
     * This is the request loop that manages the clients retry logic when interacting with ShipEngine API.
     * This method override takes in the request body as a List or Array.
     *
     * @param httpMethod The HTTP Verb to set as the HTTP Method in the request.
     * @param endpoint   A string representation of the target API endpoint for the request.
     * @param body       A Map or HashMap that contains the request body contents.
     * @param config     The global Config object for the ShipEngine SDK.
     * @return List The response from ShipEngine API serialized into a List/Array.
     */
    private List<Map<String, String>> requestLoop(
            String httpMethod,
            String endpoint,
            List<Map<String, String>> body,
            Config config
    ) throws InterruptedException {
        int retry = 0;
        List<Map<String, String>> apiResponse = List.of();
        while (retry <= config.getRetries()) {
            try {
                apiResponse = sendHttpRequest(
                        httpMethod,
                        endpoint,
                        body,
                        config
                );
            } catch (Exception err) {
                if ((retry < config.getRetries()) &&
                        (err instanceof RateLimitExceededException) &&
                        (config.getTimeout() > ((RateLimitExceededException) err).getRetryAfter())) {
                    try {
                        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(((RateLimitExceededException) err).getRetryAfter());
                        retry++;
                        continue;
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw err;
                }
            }
            return apiResponse;
        }
        return apiResponse;
    }

    /**
     * This is the request loop that manages the clients retry logic when interacting with ShipEngine API.
     * This method override does not take in a *body* argument (e.g. Servicing a GET request).
     *
     * @param httpMethod The HTTP Verb to set as the HTTP Method in the request.
     * @param endpoint   A string representation of the target API endpoint for the request.
     * @param config     The global Config object for the ShipEngine SDK.
     * @return Map The response from ShipEngine API serialized into a List/Array.
     */
    private Map<String, String> requestLoop(
            String httpMethod,
            String endpoint,
            Config config
    ) throws InterruptedException {
        int retry = 0;
        Map<String, String> apiResponse = Map.of();
        while (retry <= config.getRetries()) {
            try {
                apiResponse = sendHttpRequest(
                        httpMethod,
                        endpoint,
                        config
                );
            } catch (Exception err) {
                if ((retry < config.getRetries()) &&
                        (err instanceof RateLimitExceededException) &&
                        (config.getTimeout() > ((RateLimitExceededException) err).getRetryAfter())) {
                    try {
                        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(((RateLimitExceededException) err).getRetryAfter());
                        retry++;
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw err;
                }
            }
            return apiResponse;
        }
        return apiResponse;
    }

    private Map<String, String> sendHttpRequest(
            String httpMethod,
            String endpoint,
            Map requestBody,
            Config config
    ) {
        Map<String, String> apiResponse = Map.of();
        if (httpMethod.equals(HttpVerbs.POST.name())) {
            apiResponse = internalPost(endpoint, requestBody, config);
        } else if (httpMethod.equals(HttpVerbs.GET.name())) {
            apiResponse = internalGet(endpoint, config);
        } else if (httpMethod.equals(HttpVerbs.PUT.name())) {
            apiResponse = internalPut(endpoint, requestBody, config);
        } else if (httpMethod.equals(HttpVerbs.DELETE.name())) {
            apiResponse = internalDelete(endpoint, config);
        }
        return apiResponse;
    }

    private Map<String, String> sendHttpRequest(
            String httpMethod,
            String endpoint,
            Config config
    ) {
        Map<String, String> apiResponse = Map.of();
        if (httpMethod.equals(HttpVerbs.GET.name())) {
            apiResponse = internalGet(endpoint, config);
        } else if (httpMethod.equals(HttpVerbs.DELETE.name())) {
            apiResponse = internalDelete(endpoint, config);
        }
        return apiResponse;
    }

    private List<Map<String, String>> sendHttpRequest(
            String httpMethod,
            String endpoint,
            List<Map<String, String>> requestBody,
            Config config
    ) {
        List<Map<String, String>> apiResponse = List.of();
        if (httpMethod.equals(HttpVerbs.POST.name())) {
            apiResponse = internalPost(endpoint, requestBody, config);
        }
        return apiResponse;
    }

    public List<Map<String, String>> post(
            String endpoint,
            List<Map<String, String>> body,
            Config config
    ) throws InterruptedException {
        return requestLoop(
                HttpVerbs.POST.name(),
                endpoint,
                body,
                config
        );
    }

    public Map<String, String> post(
            String endpoint,
            Map<String, Object> body,
            Config config
    ) throws InterruptedException {
        return requestLoop(
                HttpVerbs.POST.name(),
                endpoint,
                body,
                config
        );
    }

    public Map<String, String> put(
            String endpoint,
            Config config
    ) throws InterruptedException {
        return requestLoop(
                HttpVerbs.PUT.name(),
                endpoint,
                config
        );
    }

    public Map<String, String> get(
            String endpoint,
            Config config
    ) throws InterruptedException {
        return requestLoop(
                HttpVerbs.GET.name(),
                endpoint,
                config
        );
    }

    public Map<String, String> delete(
            String endpoint,
            Config config
    ) throws InterruptedException {
        return requestLoop(
                HttpVerbs.DELETE.name(),
                endpoint,
                config
        );
    }

    private HttpRequest.Builder prepareRequest(
            String endpoint,
            Config config
    ) {
        Pattern pattern = Pattern.compile("/");
        String baseUri;
        if (endpoint.length() > 0) {
            Matcher matcher = pattern.matcher(endpoint);
            String result = matcher.replaceFirst("");
            baseUri = String.format("%s%s", config.getBaseUrl(), result);

        } else {
            baseUri = config.getBaseUrl();

        }

        URI clientUri = null;
        try {
            clientUri = new URI(baseUri);
        } catch (URISyntaxException err) {
            err.printStackTrace();
        }

        return HttpRequest.newBuilder()
                .uri(clientUri)
                .headers("Content-Type", "application/json")
                .headers("Accepts", "application/json")
                .headers("Api-Key", config.getApiKey())
                .headers("User-Agent", deriveUserAgent())
                .timeout(Duration.of(config.getTimeout(), ChronoUnit.SECONDS));
    }

    private String sendPreparedRequest(
            HttpRequest preparedRequest,
            Config config
    ) {
        String responseBody = null;

        try {
            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(preparedRequest, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();

            checkResponseForErrors(
                    response.statusCode(),
                    responseBody,
                    response.headers(),
                    config
            );

        } catch (IOException | InterruptedException err) {
            err.printStackTrace();
        }

        return responseBody;
    }

    private Map<String, String> internalDelete(
            String endpoint,
            Config config
    ) {
        HttpRequest request = prepareRequest(endpoint, config)
                .DELETE()
                .build();

        String apiResponse = sendPreparedRequest(request, config);
        return apiResponseToMap(apiResponse);
    }

    private Map<String, String> internalGet(
            String endpoint,
            Config config
    ) {
        HttpRequest request = prepareRequest(endpoint, config)
                .GET()
                .build();

        String apiResponse = sendPreparedRequest(request, config);
        return apiResponseToMap(apiResponse);
    }

    private List<Map<String, String>> internalPost(
            String endpoint,
            List<Map<String, String>> requestBody,
            Config config
    ) {
        String preppedRequest = gson.toJson(requestBody);
        HttpRequest request = prepareRequest(endpoint, config)
                .POST(HttpRequest.BodyPublishers.ofString(preppedRequest))
                .build();

        String apiResponse = sendPreparedRequest(request, config);
        return apiResponseToList(apiResponse);
    }

    private Map<String, String> internalPost(
            String endpoint,
            Map<String, String> requestBody,
            Config config
    ) {
        HttpRequest request = prepareRequest(endpoint, config)
                .POST(HttpRequest.BodyPublishers.ofString(hashMapToJson(requestBody)))
                .build();

        String apiResponse = sendPreparedRequest(request, config);
        return apiResponseToMap(apiResponse);
    }

    private Map<String, String> internalPut(
            String endpoint,
            Map requestBody,
            Config config
    ) {
        HttpRequest request = prepareRequest(endpoint, config)
                .PUT(HttpRequest.BodyPublishers.ofString(hashMapToJson(requestBody)))
                .build();

        String apiResponse = sendPreparedRequest(request, config);
        return apiResponseToMap(apiResponse);
    }

    private String hashMapToJson(Map hash) {
        return gson.toJson(hash);
    }

    private String objectToJson(Object obj) {
        return gson.toJson(obj);
    }

    private String listToJson(List list) {
        return gson.toJson(list);
    }

    private static Map apiResponseToMap(String apiResponse) {
        return gson.fromJson(apiResponse, HashMap.class);
    }
    
    private static ErrorResponse apiResponseToErrorResponse(String apiResponse) {
        TypeToken<ErrorResponse> mapType = new TypeToken<ErrorResponse>(){};
        return gson.fromJson(apiResponse, mapType.getType());
    }
    
    private static List<Map<String, String>> apiResponseToList(String apiResponse) {
        List<Map<String, String>> newList = new ArrayList<>();
        List apiResponseAsList = gson.fromJson(apiResponse, List.class);
        for (Object k : apiResponseAsList) {
            String temp = gson.toJson(k);
            newList.add(gson.fromJson(temp, HashMap.class));
        }
        return newList;
    }

    private void checkResponseForErrors(
            int statusCode,
            String httpResponseBody,
            HttpHeaders responseHeaders,
            Config config
    ) {
        switch (statusCode) {
            case 400:
            case 500:
                ErrorResponse responseBody400And500 = apiResponseToErrorResponse(httpResponseBody);
                Map<String, String> error400And500 = responseBody400And500.getErrors().get(0);
                throw new ShipEngineException(
                        error400And500.get("message"),
                        responseBody400And500.getRequestId(),
                        error400And500.get("error_source"),
                        error400And500.get("error_type"),
                        error400And500.get("error_code")
                );
            case 404:
                ErrorResponse responseBody404 = httpResponseBody.equals("") ?
                        new ErrorResponse() :
                        apiResponseToErrorResponse(httpResponseBody);
                Map<String, String> error404 = responseBody404.getErrors() != null ?
                        responseBody404.getErrors().get(0) :
                        Map.of();
                throw new ShipEngineException(
                        mapSizeIsNotZero(error404) ? error404.get("message") : "404 Error Occurred..",
                        responseBody404.getRequestId() != null ? responseBody404.getRequestId() : "",
                        "shipengine",
                        mapSizeIsNotZero(error404) ? error404.get("error_type") : "error",
                        mapSizeIsNotZero(error404) ? error404.get("error_code") : "not_found"
                );
            case 429:
                Optional<String> retryAfterHeader = responseHeaders.firstValue("retry-after");
                ErrorResponse responseBody429 = apiResponseToErrorResponse(httpResponseBody);
                if (retryAfterHeader.isPresent()) {
                    int retry = Integer.parseInt(retryAfterHeader.get()) * 1000;

                    if (retry > config.getTimeout()) {
                        throw new ClientTimeoutException(
                                responseBody429.getRequestId(),
                                "shipengine",
                                config.getTimeout()
                        );
                    } else {
                        throw new RateLimitExceededException(
                                responseBody429.getRequestId(),
                                "shipengine",
                                retry
                        );
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean mapSizeIsNotZero(Map obj) {
        return obj.size() != 0;
    }

    private String deriveUserAgent() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream("project.properties")) {
            properties.load(resourceStream);
        } catch (IOException err) {
            err.printStackTrace();
        }

        String platformOs = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String runtimeName = System.getProperty("java.runtime.name");
        String runtimeVersion = System.getProperty("java.version");

        String currentVersion = properties.getProperty("version");
        String sdkVersion = String.format("shipengine-java/%s", currentVersion);

        return String.format("%s %s/%s %s/%s", sdkVersion, platformOs, osVersion, runtimeName, runtimeVersion);
    }
}
