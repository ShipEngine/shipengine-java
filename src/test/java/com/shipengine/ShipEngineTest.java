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
                        .withBody("[\n" +
                                "  {\n" +
                                "    \"status\": \"verified\",\n" +
                                "    \"original_address\": {\n" +
                                "      \"name\": \"ShipEngine\",\n" +
                                "      \"phone\": \"1-123-456-7891\",\n" +
                                "      \"company_name\": null,\n" +
                                "      \"address_line1\": \"3800 N Lamar Blvd\",\n" +
                                "      \"address_line2\": \"ste 220\",\n" +
                                "      \"address_line3\": null,\n" +
                                "      \"city_locality\": \"Austin\",\n" +
                                "      \"state_province\": \"TX\",\n" +
                                "      \"postal_code\": \"78756\",\n" +
                                "      \"country_code\": \"US\",\n" +
                                "      \"address_residential_indicator\": \"unknown\"\n" +
                                "    },\n" +
                                "    \"matched_address\": {\n" +
                                "      \"name\": \"SHIPENGINE\",\n" +
                                "      \"phone\": \"1-123-456-7891\",\n" +
                                "      \"company_name\": null,\n" +
                                "      \"address_line1\": \"3800 N LAMAR BLVD STE 220\",\n" +
                                "      \"address_line2\": \"\",\n" +
                                "      \"address_line3\": null,\n" +
                                "      \"city_locality\": \"AUSTIN\",\n" +
                                "      \"state_province\": \"TX\",\n" +
                                "      \"postal_code\": \"78756-0003\",\n" +
                                "      \"country_code\": \"US\",\n" +
                                "      \"address_residential_indicator\": \"no\"\n" +
                                "    },\n" +
                                "    \"messages\": []\n" +
                                "  }\n" +
                                "]")
                        .withDelay(TimeUnit.SECONDS, 1));

        HashMap<String, String> stubAddress = new HashMap<>() {{
            put("name", "ShipEngine");
            put("company", "Auctane");
            put("phone", "1-123-456-7891");
            put("address_line1", "3800 N Lamar Blvd");
            put("address_line2", "ste 220");
            put("city_locality", "Austin");
            put("state_province", "TX");
            put("postal_code", "78756");
            put("country_code", "US");
            put("address_residential_indicator", "unknown");
        }};

        HashMap<String, Object> customConfig = new HashMap<>() {{
            put("apiKey", Constants.API_KEY);
            put("baseUrl", Constants.TEST_URL);
        }};

        List<HashMap<String, String>> unvalidatedAddress = List.of(stubAddress);
        List<HashMap<String, String>> validatedAddress = new ShipEngine(customConfig).validateAddresses(unvalidatedAddress);
        System.out.println("validatedAddress = " + validatedAddress);
        assertEquals("verified", validatedAddress.get(0).get("status"));
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
