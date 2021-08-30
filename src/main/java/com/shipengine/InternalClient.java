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
import java.util.HashMap;
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

    private HashMap requestLoop(
            String httpMethod,
            String endpoint,
            Optional body,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        int retry = 0;
        HashMap apiResponse;
        while (retry <= config.getRetries()) {
            try {
                apiResponse = sendHttpRequest();
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
        // return apiResponse;
    }

    private HashMap sendHttpRequest(
            String httpMethod,
            String endpoint,
            HashMap requestBody,
            int retry,
            Config config
    ) throws URISyntaxException, IOException, InterruptedException {
        if (httpMethod.equals(HttpVerbs.POST.name())) {
            return internalPost(endpoint, requestBody, config);
        } else if (httpMethod.equals(HttpVerbs.GET.name())) {
            return internalGet(endpoint, config);
        } else if (httpMethod.equals(HttpVerbs.PUT.name())) {
            return internalPut(endpoint, requestBody, config);
        } else if (httpMethod.equals(HttpVerbs.DELETE.name())) {
            return internalDelete(endpoint, config);
        }
    }

    public HashMap post(String endpoint, List body, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.POST.name(),
                endpoint,
                Optional.of(body),
                config
        );
    }

    public HashMap post(String endpoint, HashMap body, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.POST.name(),
                endpoint,
                Optional.of(body),
                config
        );
    }

    public HashMap put(String endpoint, HashMap body, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.PUT.name(),
                endpoint,
                Optional.of(body),
                config
        );
    }

    public HashMap get(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.GET.name(),
                endpoint,
                Optional.empty(),
                config
        );
    }

    public HashMap delete(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        return requestLoop(
                HttpVerbs.DELETE.name(),
                endpoint,
                Optional.empty(),
                config
        );
    }

    private HttpRequest.Builder prepareRequest(Optional<String> endpoint, Config config) throws URISyntaxException {
        Pattern pattern = Pattern.compile("/");
        String baseUri;
        if (endpoint.isPresent()) {
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
                .timeout(Duration.of(config.getTimeout(), SECONDS));

    }

    private String sendPreparedRequest(HttpRequest preparedRequest) throws IOException, InterruptedException {
        return HttpClient
                .newBuilder()
                .build()
                .send(preparedRequest, HttpResponse.BodyHandlers.ofString())
                .body();
    }

    private HashMap internalDelete(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(Optional.of(endpoint), config)
                .DELETE()
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToHashMap(apiResponse);
    }

    private HashMap internalGet(String endpoint, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(Optional.of(endpoint), config)
                .GET()
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToHashMap(apiResponse);
    }

    private HashMap internalPost(String endpoint, HashMap requestBody, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(Optional.of(endpoint), config)
                .POST(HttpRequest.BodyPublishers.ofString(hashMapToJson(requestBody)))
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToHashMap(apiResponse);
    }

    private HashMap internalPut(String endpoint, HashMap requestBody, Config config) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = prepareRequest(Optional.of(endpoint), config)
                .PUT(HttpRequest.BodyPublishers.ofString(hashMapToJson(requestBody)))
                .build();

        String apiResponse = sendPreparedRequest(request);
        return apiResponseToHashMap(apiResponse);
    }

    private String hashMapToJson(HashMap hash) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(hash);
    }

    private static HashMap apiResponseToHashMap(String apiResponse) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(apiResponse, HashMap.class);
    }
}
