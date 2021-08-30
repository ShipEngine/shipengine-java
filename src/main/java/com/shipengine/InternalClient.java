package com.shipengine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shipengine.exception.RateLimitExceededException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InternalClient {
    private enum HttpVerbs {
        GET,
        POST,
        PUT,
        DELETE
    }

    private Map requestLoop(
            String httpMethod,
            String endpoint,
            Map body,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        int retry = 0;
        Map apiResponse = Map.of();
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
                        java.util.concurrent.TimeUnit.SECONDS.sleep(((RateLimitExceededException) err).getRetryAfter());
                        retry++;
                        // continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw err;
                }
                // return apiResponse;
            }
            return apiResponse;
        }
        return apiResponse;
    }

    private List requestLoop(
            String httpMethod,
            String endpoint,
            List body,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        int retry = 0;
        List apiResponse = List.of();
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
                        java.util.concurrent.TimeUnit.SECONDS.sleep(((RateLimitExceededException) err).getRetryAfter());
                        retry++;
                        // continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw err;
                }
                // return apiResponse;
            }
            return apiResponse;
        }
        return apiResponse;
    }

    private Map requestLoop(
            String httpMethod,
            String endpoint,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        int retry = 0;
        Map apiResponse = Map.of();
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
                        java.util.concurrent.TimeUnit.SECONDS.sleep(((RateLimitExceededException) err).getRetryAfter());
                        retry++;
                        // continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw err;
                }
                // return apiResponse;
            }
            return apiResponse;
        }
        return apiResponse;
    }

    private Map sendHttpRequest(
            String httpMethod,
            String endpoint,
            Map requestBody,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        Map apiResponse = Map.of();
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

    private Map sendHttpRequest(
            String httpMethod,
            String endpoint,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        Map apiResponse = Map.of();
        if (httpMethod.equals(HttpVerbs.GET.name())) {
            apiResponse = internalGet(endpoint, config);
        } else if (httpMethod.equals(HttpVerbs.DELETE.name())) {
            apiResponse = internalDelete(endpoint, config);
        }
        return apiResponse;
    }

    private List sendHttpRequest(
            String httpMethod,
            String endpoint,
            List requestBody,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        List apiResponse = List.of();
        if (httpMethod.equals(HttpVerbs.POST.name())) {
            apiResponse = internalPost(endpoint, requestBody, config);
        }
        return apiResponse;
    }

    public List post(String endpoint, List body, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.POST.name(),
                endpoint,
                body,
                config
        );
    }

    public Map post(String endpoint, Map body, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.POST.name(),
                endpoint,
                body,
                config
        );
    }

    public Map put(String endpoint, Map body, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.PUT.name(),
                endpoint,
                body,
                config
        );
    }

    public Map get(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.GET.name(),
                endpoint,
                config
        );
    }

    public Map delete(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.DELETE.name(),
                endpoint,
                config
        );
    }

    private HttpRequest.Builder prepareRequest(String endpoint, Config config) throws URISyntaxException {
        Pattern pattern = Pattern.compile("/");
        String baseUri;
        if (endpoint.length() > 0) {
            Matcher matcher = pattern.matcher(String.valueOf(endpoint));
            String result = matcher.replaceFirst("");
            baseUri = String.format("%s%s", config.getBaseUrl(), result);

        } else {
            baseUri = config.getBaseUrl();

        }
        return HttpRequest.newBuilder()
                .uri(new URI(baseUri))
                .headers("Content-Type", "application/json")
                .headers("Accepts", "application/json")
                .timeout(Duration.of(config.getTimeout(), ChronoUnit.SECONDS));

    }

    private String sendPreparedRequest(HttpRequest preparedRequest) throws IOException, InterruptedException {
        return HttpClient
                .newBuilder()
                .build()
                .send(preparedRequest, HttpResponse.BodyHandlers.ofString())
                .body();
    }

    private Map internalDelete(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(endpoint, config)
                .DELETE()
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToMap(apiResponse);
    }

    private Map internalGet(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(endpoint, config)
                .GET()
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToMap(apiResponse);
    }

    private List internalPost(String endpoint, List requestBody, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(endpoint, config)
                .POST(HttpRequest.BodyPublishers.ofString(listToJson(requestBody)))
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToList(apiResponse);
    }

    private Map internalPost(String endpoint, Map requestBody, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(endpoint, config)
                .POST(HttpRequest.BodyPublishers.ofString(hashMapToJson(requestBody)))
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToMap(apiResponse);
    }

    private Map internalPut(String endpoint, Map requestBody, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(endpoint, config)
                .PUT(HttpRequest.BodyPublishers.ofString(hashMapToJson(requestBody)))
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToMap(apiResponse);
    }

    private String hashMapToJson(Map hash) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(hash);
    }

    private String listToJson(List list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(list);
    }

    private static Map apiResponseToMap(String apiResponse) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(apiResponse, Map.class);
    }

    private static List apiResponseToList(String apiResponse) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(apiResponse, List.class);
    }
}
