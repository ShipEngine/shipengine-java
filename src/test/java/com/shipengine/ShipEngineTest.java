package com.shipengine;

import com.shipengine.util.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * Unit test for simple App.
 */
public class ShipEngineTest {
    private ClientAndServer mockServer;

    @Before
    public void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @After
    public void stopMockServer() {
        mockServer.stop();
    }

    /**
     * Testing Address Validation with a valid address.
     */
    @Test
    public void successfulAddressValidation() {
        new MockServerClient("127.0.0.1", 1080)
                .when(request()
                                .withMethod("POST")
                                .withPath("/v1/addresses/validate"),
                        Times.exactly(1))
                .respond(response()
                        .withStatusCode(200)
                        .withBody("[{\"status\": \"success!!\"}]")
                        .withDelay(TimeUnit.SECONDS, 1));

        HashMap<String, String> stubAddress = new HashMap<>();
        stubAddress.put("name", "ShipEngine");
        stubAddress.put("company", "Auctane");
        stubAddress.put("phone", "1-123-456-7891");
        stubAddress.put("address_line1", "3800 N Lamar Blvd");
        stubAddress.put("address_line2", "ste 220");
        stubAddress.put("city_locality", "Austin");
        stubAddress.put("state_province", "TX");
        stubAddress.put("postal_code", "78756");
        stubAddress.put("country_code", "US");
        stubAddress.put("address_residential_indicator", "unknown");

        HashMap<String, Object> customConfig = new HashMap<>(){{
            put("apiKey", Constants.API_KEY);
            put("baseUrl", "http://localhost:1080/");
            put("timeout", 10);
            put("retries", 2);
            put("pageSize", 55);
        }};

        List<HashMap<String, String>> unvalidatedAddress = List.of(stubAddress);
        List validatedAddress = new ShipEngine(customConfig).validateAddresses(unvalidatedAddress);
        assertEquals(HashMap.class, validatedAddress.get(0).getClass());
    }

    /**
     * Testing successful call to listCarriers which fetches all
     * carrier accounts connected o given ShipEngine Account.
     */
    @Test
    public void successfulListCarriers() {
        Map listOfCarriers = new ShipEngine(Constants.API_KEY).listCarriers();
        assertEquals(HashMap.class, listOfCarriers.getClass());
    }
}
