package com.shipengine;

import com.shipengine.util.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Parameter;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private final String stubApiKey = "STUB_API_KEY";

    private Map<String, Object> methodLevelConfig = Map.of(
            "retries", 2
    );

    private final Map<String, Object> customConfig = Map.of(
            "apiKey", Constants.API_KEY,
            "baseUrl", Constants.TEST_URL,
            "retries", 8,
            "timeout", 8000
    );

    @Before
    public void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @After
    public void stopMockServer() {
        mockServer.stop();
    }

    public static String fetchTestData(String file) throws Exception {
        return readFileAsString(file);
    }

    public static String readFileAsString(String file) throws Exception {
        String fileData = "";
        try {
            fileData += new String(Files.readAllBytes(Paths.get(file)));
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        return fileData;
    }

    @Test
    public void successfulInstantiationWithStringAPIKey() {
        ShipEngine shipengine = new ShipEngine(stubApiKey);
        assertEquals(stubApiKey, shipengine.getConfig().getApiKey());
    }

    @Test
    public void successfulInstantiationWithIndividualArgs() {
        ShipEngine shipengine = new ShipEngine(stubApiKey, 8000, 3, 75);
        assertEquals(stubApiKey, shipengine.getConfig().getApiKey());
        assertEquals(3, shipengine.getConfig().getRetries());
        assertEquals(8000, shipengine.getConfig().getTimeout());
        assertEquals(75, shipengine.getConfig().getPageSize());
    }

    /**
     * Testing Address Validation with a valid address.
     */
    @Test
    public void successfulAddressValidation() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/addresses/validate"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/validate-addresses.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Map<String, String>> unvalidatedAddress = List.of(
                Map.of(
                        "name", "ShipEngine",
                        "company", "Auctane",
                        "phone", "1-123-456-7891",
                        "address_line1", "3800 N Lamar Blvd",
                        "address_line2", "ste 220",
                        "city_locality", "Austin",
                        "state_province", "TX",
                        "postal_code", "78756",
                        "country_code", "US",
                        "address_residential_indicator", "unknown"
                )
        );

        List<Map<String, String>> validatedAddress = new ShipEngine(customConfig).validateAddresses(unvalidatedAddress);
        assertEquals("verified", validatedAddress.get(0).get("status"));
    }

    @Test
    public void addressValidationWithMethodLevelConfig() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/addresses/validate"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/validate-addresses.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Map<String, String>> unvalidatedAddress = List.of(
                Map.of(
                        "name", "ShipEngine",
                        "company", "Auctane",
                        "phone", "1-123-456-7891",
                        "address_line1", "3800 N Lamar Blvd",
                        "address_line2", "ste 220",
                        "city_locality", "Austin",
                        "state_province", "TX",
                        "postal_code", "78756",
                        "country_code", "US",
                        "address_residential_indicator", "unknown"
                )
        );

        List<Map<String, String>> validatedAddress = new ShipEngine(customConfig).validateAddresses(
                unvalidatedAddress,
                methodLevelConfig
        );
        assertEquals("verified", validatedAddress.get(0).get("status"));
    }

    /**
     * Testing successful call to listCarriers which fetches all
     * carrier accounts connected o given ShipEngine Account.
     */
    @Test
    public void successfulListCarriers() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/carriers"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/list-carriers.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> listOfCarriers = new ShipEngine(customConfig).listCarriers();
        assertEquals(HashMap.class, listOfCarriers.getClass());
    }

    @Test
    public void listCarriersWithMethodLevelConfig() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/carriers"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/list-carriers.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> listOfCarriers = new ShipEngine(customConfig).listCarriers(methodLevelConfig);
        assertEquals(HashMap.class, listOfCarriers.getClass());
    }

    @Test
    public void successfulCreateLabelUsingShipmentDetails() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/labels"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/create-label.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> shipmentDetails = Map.ofEntries(
                Map.entry("shipment", Map.of(
                                "carrier_id", "se-1234",
                                "service_code", "usps_first_class_mail",
                                "external_order_id", "string",
                                "item", List.of(),
                                "tax_identifiers", List.of(
                                        Map.of(
                                                "taxable_entity_type", "shipper",
                                                "identifier_type", "vat",
                                                "issuing_authority", "string",
                                                "value", "string"
                                        )
                                ),
                                "external_shipment_id", "string",
                                "ship_date", "2018-09-23T00:00:00.000Z",
                                "ship_to", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                ),
                                "ship_from", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                )
                        )
                ));

        Map<String, String> labelData = new ShipEngine(customConfig).createLabelFromShipmentDetails(shipmentDetails);
        assertEquals("stamps_com", labelData.get("carrier_code"));
    }

    @Test
    public void createLabelUsingShipmentDetailsWithMethodLevelConfig() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/labels"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/create-label.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> shipmentDetails = Map.ofEntries(
                Map.entry("shipment", Map.of(
                                "carrier_id", "se-1234",
                                "service_code", "usps_first_class_mail",
                                "external_order_id", "string",
                                "item", List.of(),
                                "tax_identifiers", List.of(
                                        Map.of(
                                                "taxable_entity_type", "shipper",
                                                "identifier_type", "vat",
                                                "issuing_authority", "string",
                                                "value", "string"
                                        )
                                ),
                                "external_shipment_id", "string",
                                "ship_date", "2018-09-23T00:00:00.000Z",
                                "ship_to", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                ),
                                "ship_from", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                )
                        )
                ));

        Map<String, String> labelData = new ShipEngine(customConfig).createLabelFromShipmentDetails(
                shipmentDetails,
                methodLevelConfig
        );
        assertEquals("stamps_com", labelData.get("carrier_code"));
    }

    @Test
    public void successfulCreateLabelUsingRateId() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/labels/rates/se-1234"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/create-label.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String labelId = "se-1234";
        Map<String, Object> labelParams = Map.of(
                "label_layout", "4x6",
                "label_format", "pdf",
                "label_download_type", "url"
        );

        Map<String, String> labelData = new ShipEngine(customConfig).createLabelFromRateId(labelId, labelParams);
        assertEquals("se-799373193", labelData.get("label_id"));
    }

    @Test
    public void createLabelUsingRateIdWithMethodLevelConfig() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/labels/rates/se-1234"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/create-label.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String labelId = "se-1234";
        Map<String, Object> labelParams = Map.of(
                "label_layout", "4x6",
                "label_format", "pdf",
                "label_download_type", "url"
        );

        Map<String, String> labelData = new ShipEngine(customConfig).createLabelFromRateId(
                labelId,
                labelParams,
                methodLevelConfig
        );
        assertEquals("se-799373193", labelData.get("label_id"));
    }

    @Test
    public void successfulVoidLabelWithLabelId() {
        String labelId = "se-799373193";
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/labels/se-799373193/void"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/void-label.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> voidLabelResult = new ShipEngine(customConfig).voidLabelWithLabelId(labelId);
        assertEquals("This label has been voided.", voidLabelResult.get("message"));
    }

    @Test
    public void voidLabelWithLabelIdUsingMethodLabelConfig() {
        String labelId = "se-799373193";
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/labels/se-799373193/void"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/void-label.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> voidLabelResult = new ShipEngine(customConfig).voidLabelWithLabelId(
                labelId,
                methodLevelConfig
        );
        assertEquals("This label has been voided.", voidLabelResult.get("message"));
    }

    @Test
    public void successfulTrackUsingLabelId() {
        String labelId = "se-1234";
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/labels/se-1234/track"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/track-package.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> trackingResult = new ShipEngine(customConfig).trackUsingLabelId(labelId);
        assertEquals("1Z932R800392060079", trackingResult.get("tracking_number"));
    }

    @Test
    public void trackUsingLabelIdWithMethodLevelConfig() {
        String labelId = "se-1234";
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/labels/se-1234/track"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/track-package.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> trackingResult = new ShipEngine(customConfig).trackUsingLabelId(labelId, methodLevelConfig);
        assertEquals("1Z932R800392060079", trackingResult.get("tracking_number"));
    }

    @Test
    public void successfulTrackUsingCarrierCodeAndTrackingNumber() {
        Map<String, Object> trackingData = Map.of(
                "carrierCode", "se-1234",
                "trackingNumber", "abc123"
        );

        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/tracking")
                                    .withQueryStringParameters(List.of(
                                            new Parameter("carrier_code", (String) trackingData.get("carrierCode")),
                                            new Parameter("tracking_number", (String) trackingData.get("trackingNumber"))
                                    )),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/track-package.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> trackingResult = new ShipEngine(customConfig).trackUsingCarrierCodeAndTrackingNumber(trackingData);
        assertEquals("1Z932R800392060079", trackingResult.get("tracking_number"));
    }

    @Test
    public void trackUsingCarrierCodeAndTrackingNumberWithMethodLevelConfig() {
        Map<String, Object> trackingData = Map.of(
                "carrierCode", "se-1234",
                "trackingNumber", "abc123"
        );

        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("GET")
                                    .withPath("/v1/tracking")
                                    .withQueryStringParameters(List.of(
                                            new Parameter("carrier_code", (String) trackingData.get("carrierCode")),
                                            new Parameter("tracking_number", (String) trackingData.get("trackingNumber"))
                                    )),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData("src/test/java/com/shipengine/resources/track-package.json"))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> trackingResult = new ShipEngine(customConfig).trackUsingCarrierCodeAndTrackingNumber(
                trackingData,
                methodLevelConfig
        );
        assertEquals("1Z932R800392060079", trackingResult.get("tracking_number"));
    }

    @Test
    public void successfulGetRateFromShipmentDetails() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/rates"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData(
                                    "src/test/java/com/shipengine/resources/get-rate-from-shipment-details.json"
                            ))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> shipmentDetails = Map.ofEntries(
                Map.entry("shipment", Map.of(
                                "carrier_id", "se-1234",
                                "service_code", "usps_first_class_mail",
                                "external_order_id", "string",
                                "item", List.of(),
                                "tax_identifiers", List.of(
                                        Map.of(
                                                "taxable_entity_type", "shipper",
                                                "identifier_type", "vat",
                                                "issuing_authority", "string",
                                                "value", "string"
                                        )
                                ),
                                "external_shipment_id", "string",
                                "ship_date", "2018-09-23T00:00:00.000Z",
                                "ship_to", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                ),
                                "ship_from", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                )
                        )
                ));

        Map<String, String> rateData = new ShipEngine(customConfig).getRatesWithShipmentDetails(shipmentDetails);
        assertEquals("se-141694059", rateData.get("shipmentId"));
    }

    @Test
    public void getRateFromShipmentDetailsWithMethodLevelConfig() {
        try {
            new MockServerClient("127.0.0.1", 1080)
                    .when(request()
                                    .withMethod("POST")
                                    .withPath("/v1/rates"),
                            Times.exactly(1))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(fetchTestData(
                                    "src/test/java/com/shipengine/resources/get-rate-from-shipment-details.json"
                            ))
                            .withDelay(TimeUnit.SECONDS, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> shipmentDetails = Map.ofEntries(
                Map.entry("shipment", Map.of(
                                "carrier_id", "se-1234",
                                "service_code", "usps_first_class_mail",
                                "external_order_id", "string",
                                "item", List.of(),
                                "tax_identifiers", List.of(
                                        Map.of(
                                                "taxable_entity_type", "shipper",
                                                "identifier_type", "vat",
                                                "issuing_authority", "string",
                                                "value", "string"
                                        )
                                ),
                                "external_shipment_id", "string",
                                "ship_date", "2018-09-23T00:00:00.000Z",
                                "ship_to", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                ),
                                "ship_from", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                )
                        )
                ));

        Map<String, String> rateData = new ShipEngine(customConfig).getRatesWithShipmentDetails(
                shipmentDetails,
                methodLevelConfig
        );
        assertEquals("se-141694059", rateData.get("shipmentId"));
    }
}