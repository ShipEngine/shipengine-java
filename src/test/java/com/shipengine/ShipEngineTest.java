package com.shipengine;

import com.shipengine.util.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;

import java.util.ArrayList;
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

    private final HashMap<String, Object> customConfig = new HashMap<>() {{
        put("apiKey", Constants.API_KEY);
        put("baseUrl", Constants.TEST_URL);
        put("retries", 3);
    }};

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

        List<HashMap<String, String>> unvalidatedAddress = List.of(stubAddress);
        List<HashMap<String, String>> validatedAddress = new ShipEngine(customConfig).validateAddresses(unvalidatedAddress);
        assertEquals("verified", validatedAddress.get(0).get("status"));
    }

    /**
     * Testing successful call to listCarriers which fetches all
     * carrier accounts connected o given ShipEngine Account.
     */
    @Test
    public void successfulListCarriers() {
        new MockServerClient("127.0.0.1", 1080)
                .when(request()
                                .withMethod("GET")
                                .withPath("/v1/carriers"),
                        Times.exactly(1))
                .respond(response()
                        .withStatusCode(200)
                        .withBody("{\n" +
                                "  \"carriers\": [\n" +
                                "    {\n" +
                                "      \"carrier_id\": \"se-656171\",\n" +
                                "      \"carrier_code\": \"stamps_com\",\n" +
                                "      \"account_number\": \"test_account_656171\",\n" +
                                "      \"requires_funded_amount\": true,\n" +
                                "      \"balance\": 8948.3400,\n" +
                                "      \"nickname\": \"ShipEngine Test Account - Stamps.com\",\n" +
                                "      \"friendly_name\": \"Stamps.com\",\n" +
                                "      \"primary\": false,\n" +
                                "      \"has_multi_package_supporting_services\": false,\n" +
                                "      \"supports_label_messages\": true,\n" +
                                "      \"services\": [\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_first_class_mail\",\n" +
                                "          \"name\": \"USPS First Class Mail\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_media_mail\",\n" +
                                "          \"name\": \"USPS Media Mail\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_parcel_select\",\n" +
                                "          \"name\": \"USPS Parcel Select Ground\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_priority_mail\",\n" +
                                "          \"name\": \"USPS Priority Mail\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_priority_mail_express\",\n" +
                                "          \"name\": \"USPS Priority Mail Express\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_first_class_mail_international\",\n" +
                                "          \"name\": \"USPS First Class Mail Intl\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_priority_mail_international\",\n" +
                                "          \"name\": \"USPS Priority Mail Intl\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656171\",\n" +
                                "          \"carrier_code\": \"stamps_com\",\n" +
                                "          \"service_code\": \"usps_priority_mail_express_international\",\n" +
                                "          \"name\": \"USPS Priority Mail Express Intl\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"packages\": [\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"cubic\",\n" +
                                "          \"name\": \"Cubic\",\n" +
                                "          \"description\": \"Cubic\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"flat_rate_envelope\",\n" +
                                "          \"name\": \"Flat Rate Envelope\",\n" +
                                "          \"description\": \"USPS flat rate envelope. A special cardboard envelope provided by the USPS that clearly indicates \\\"Flat Rate\\\".\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"flat_rate_legal_envelope\",\n" +
                                "          \"name\": \"Flat Rate Legal Envelope\",\n" +
                                "          \"description\": \"Flat Rate Legal Envelope\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"flat_rate_padded_envelope\",\n" +
                                "          \"name\": \"Flat Rate Padded Envelope\",\n" +
                                "          \"description\": \"Flat Rate Padded Envelope\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"large_envelope_or_flat\",\n" +
                                "          \"name\": \"Large Envelope or Flat\",\n" +
                                "          \"description\": \"Large envelope or flat. Has one dimension that is between 11 1/2\\\" and 15\\\" long, 6 1/18\\\" and 12\\\" high, or 1/4\\\" and 3/4\\\" thick.\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"large_flat_rate_box\",\n" +
                                "          \"name\": \"Large Flat Rate Box\",\n" +
                                "          \"description\": \"Large Flat Rate Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"large_package\",\n" +
                                "          \"name\": \"Large Package (any side \\u003e 12\\\")\",\n" +
                                "          \"description\": \"Large package. Longest side plus the distance around the thickest part is over 84\\\" and less than or equal to 108\\\".\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"letter\",\n" +
                                "          \"name\": \"Letter\",\n" +
                                "          \"description\": \"Letter\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"medium_flat_rate_box\",\n" +
                                "          \"name\": \"Medium Flat Rate Box\",\n" +
                                "          \"description\": \"USPS flat rate box. A special 11\\\" x 8 1/2\\\" x 5 1/2\\\" or 14\\\" x 3.5\\\" x 12\\\" USPS box that clearly indicates \\\"Flat Rate Box\\\"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"non_rectangular\",\n" +
                                "          \"name\": \"Non Rectangular Package\",\n" +
                                "          \"description\": \"Non-Rectangular package type that is cylindrical in shape.\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"package\",\n" +
                                "          \"name\": \"Package\",\n" +
                                "          \"description\": \"Package. Longest side plus the distance around the thickest part is less than or equal to 84\\\"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"regional_rate_box_a\",\n" +
                                "          \"name\": \"Regional Rate Box A\",\n" +
                                "          \"description\": \"Regional Rate Box A\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"regional_rate_box_b\",\n" +
                                "          \"name\": \"Regional Rate Box B\",\n" +
                                "          \"description\": \"Regional Rate Box B\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"small_flat_rate_box\",\n" +
                                "          \"name\": \"Small Flat Rate Box\",\n" +
                                "          \"description\": \"Small Flat Rate Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"thick_envelope\",\n" +
                                "          \"name\": \"Thick Envelope\",\n" +
                                "          \"description\": \"Thick envelope. Envelopes or flats greater than 3/4\\\" at the thickest point.\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"options\": [\n" +
                                "        {\n" +
                                "          \"name\": \"non_machinable\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_account\",\n" +
                                "          \"default_value\": null,\n" +
                                "          \"description\": \"Bill To Account\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_party\",\n" +
                                "          \"default_value\": null,\n" +
                                "          \"description\": \"Bill To Party\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_postal_code\",\n" +
                                "          \"default_value\": null,\n" +
                                "          \"description\": \"Bill To Postal Code\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_country_code\",\n" +
                                "          \"default_value\": null,\n" +
                                "          \"description\": \"Bill To Country Code\"\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"carrier_id\": \"se-656172\",\n" +
                                "      \"carrier_code\": \"ups\",\n" +
                                "      \"account_number\": \"test_account_656172\",\n" +
                                "      \"requires_funded_amount\": false,\n" +
                                "      \"balance\": 0.0,\n" +
                                "      \"nickname\": \"ShipEngine Test Account - UPS\",\n" +
                                "      \"friendly_name\": \"UPS\",\n" +
                                "      \"primary\": false,\n" +
                                "      \"has_multi_package_supporting_services\": true,\n" +
                                "      \"supports_label_messages\": true,\n" +
                                "      \"services\": [\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_standard_international\",\n" +
                                "          \"name\": \"UPS Standard®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_next_day_air_early_am\",\n" +
                                "          \"name\": \"UPS Next Day Air® Early\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_worldwide_express\",\n" +
                                "          \"name\": \"UPS Worldwide Express®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_next_day_air\",\n" +
                                "          \"name\": \"UPS Next Day Air®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_ground_international\",\n" +
                                "          \"name\": \"UPS Ground® (International)\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_worldwide_express_plus\",\n" +
                                "          \"name\": \"UPS Worldwide Express Plus®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_next_day_air_saver\",\n" +
                                "          \"name\": \"UPS Next Day Air Saver®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_worldwide_expedited\",\n" +
                                "          \"name\": \"UPS Worldwide Expedited®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_2nd_day_air_am\",\n" +
                                "          \"name\": \"UPS 2nd Day Air AM®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_2nd_day_air\",\n" +
                                "          \"name\": \"UPS 2nd Day Air®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_worldwide_saver\",\n" +
                                "          \"name\": \"UPS Worldwide Saver®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_2nd_day_air_international\",\n" +
                                "          \"name\": \"UPS 2nd Day Air® (International)\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_3_day_select\",\n" +
                                "          \"name\": \"UPS 3 Day Select®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_ground\",\n" +
                                "          \"name\": \"UPS® Ground\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656172\",\n" +
                                "          \"carrier_code\": \"ups\",\n" +
                                "          \"service_code\": \"ups_next_day_air_international\",\n" +
                                "          \"name\": \"UPS Next Day Air® (International)\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"packages\": [\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"package\",\n" +
                                "          \"name\": \"Package\",\n" +
                                "          \"description\": \"Package. Longest side plus the distance around the thickest part is less than or equal to 84\\\"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups__express_box_large\",\n" +
                                "          \"name\": \"UPS  Express® Box - Large\",\n" +
                                "          \"description\": \"Express Box - Large\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_10_kg_box\",\n" +
                                "          \"name\": \"UPS 10 KG Box®\",\n" +
                                "          \"description\": \"10 KG Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_25_kg_box\",\n" +
                                "          \"name\": \"UPS 25 KG Box®\",\n" +
                                "          \"description\": \"25 KG Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_express_box\",\n" +
                                "          \"name\": \"UPS Express® Box\",\n" +
                                "          \"description\": \"Express Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_express_box_medium\",\n" +
                                "          \"name\": \"UPS Express® Box - Medium\",\n" +
                                "          \"description\": \"Express Box - Medium\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_express_box_small\",\n" +
                                "          \"name\": \"UPS Express® Box - Small\",\n" +
                                "          \"description\": \"Express Box - Small\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_express_pak\",\n" +
                                "          \"name\": \"UPS Express® Pak\",\n" +
                                "          \"description\": \"Pak\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_letter\",\n" +
                                "          \"name\": \"UPS Letter\",\n" +
                                "          \"description\": \"Letter\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"ups_tube\",\n" +
                                "          \"name\": \"UPS Tube\",\n" +
                                "          \"description\": \"Tube\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"options\": [\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_account\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_country_code\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_party\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_postal_code\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"collect_on_delivery\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"contains_alcohol\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"delivered_duty_paid\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"dry_ice\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"dry_ice_weight\",\n" +
                                "          \"default_value\": \"0\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"freight_class\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"non_machinable\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"saturday_delivery\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"shipper_release\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"Driver may release package without signature\"\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"carrier_id\": \"se-656173\",\n" +
                                "      \"carrier_code\": \"fedex\",\n" +
                                "      \"account_number\": \"test_account_656173\",\n" +
                                "      \"requires_funded_amount\": false,\n" +
                                "      \"balance\": 0.0,\n" +
                                "      \"nickname\": \"ShipEngine Test Account - FedEx\",\n" +
                                "      \"friendly_name\": \"FedEx\",\n" +
                                "      \"primary\": false,\n" +
                                "      \"has_multi_package_supporting_services\": true,\n" +
                                "      \"supports_label_messages\": true,\n" +
                                "      \"services\": [\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_ground\",\n" +
                                "          \"name\": \"FedEx Ground®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_home_delivery\",\n" +
                                "          \"name\": \"FedEx Home Delivery®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_2day\",\n" +
                                "          \"name\": \"FedEx 2Day®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_2day_am\",\n" +
                                "          \"name\": \"FedEx 2Day® A.M.\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_express_saver\",\n" +
                                "          \"name\": \"FedEx Express Saver®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_standard_overnight\",\n" +
                                "          \"name\": \"FedEx Standard Overnight®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_priority_overnight\",\n" +
                                "          \"name\": \"FedEx Priority Overnight®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_first_overnight\",\n" +
                                "          \"name\": \"FedEx First Overnight®\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_1_day_freight\",\n" +
                                "          \"name\": \"FedEx 1Day® Freight\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_2_day_freight\",\n" +
                                "          \"name\": \"FedEx 2Day® Freight\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_3_day_freight\",\n" +
                                "          \"name\": \"FedEx 3Day® Freight\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_first_overnight_freight\",\n" +
                                "          \"name\": \"FedEx First Overnight® Freight\",\n" +
                                "          \"domestic\": true,\n" +
                                "          \"international\": false,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_ground_international\",\n" +
                                "          \"name\": \"FedEx International Ground®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_international_economy\",\n" +
                                "          \"name\": \"FedEx International Economy®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_international_priority\",\n" +
                                "          \"name\": \"FedEx International Priority®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_international_first\",\n" +
                                "          \"name\": \"FedEx International First®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_international_economy_freight\",\n" +
                                "          \"name\": \"FedEx International Economy® Freight\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_international_priority_freight\",\n" +
                                "          \"name\": \"FedEx International Priority® Freight\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": true\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"carrier_id\": \"se-656173\",\n" +
                                "          \"carrier_code\": \"fedex\",\n" +
                                "          \"service_code\": \"fedex_international_connect_plus\",\n" +
                                "          \"name\": \"FedEx International Connect Plus®\",\n" +
                                "          \"domestic\": false,\n" +
                                "          \"international\": true,\n" +
                                "          \"is_multi_package_supported\": false\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"packages\": [\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_envelope_onerate\",\n" +
                                "          \"name\": \"FedEx One Rate® Envelope\",\n" +
                                "          \"description\": \"FedEx® Envelope\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_extra_large_box_onerate\",\n" +
                                "          \"name\": \"FedEx One Rate® Extra Large Box\",\n" +
                                "          \"description\": \"FedEx® Extra Large Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_large_box_onerate\",\n" +
                                "          \"name\": \"FedEx One Rate® Large Box\",\n" +
                                "          \"description\": \"FedEx® Large Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_medium_box_onerate\",\n" +
                                "          \"name\": \"FedEx One Rate® Medium Box\",\n" +
                                "          \"description\": \"FedEx® Medium Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_pak_onerate\",\n" +
                                "          \"name\": \"FedEx One Rate® Pak\",\n" +
                                "          \"description\": \"FedEx® Pak\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_small_box_onerate\",\n" +
                                "          \"name\": \"FedEx One Rate® Small Box\",\n" +
                                "          \"description\": \"FedEx® Small Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_tube_onerate\",\n" +
                                "          \"name\": \"FedEx One Rate® Tube\",\n" +
                                "          \"description\": \"FedEx® Tube\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_10kg_box\",\n" +
                                "          \"name\": \"FedEx® 10kg Box\",\n" +
                                "          \"description\": \"FedEx® 10kg Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_25kg_box\",\n" +
                                "          \"name\": \"FedEx® 25kg Box\",\n" +
                                "          \"description\": \"FedEx® 25kg Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_box\",\n" +
                                "          \"name\": \"FedEx® Box\",\n" +
                                "          \"description\": \"FedEx® Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_envelope\",\n" +
                                "          \"name\": \"FedEx® Envelope\",\n" +
                                "          \"description\": \"FedEx® Envelope\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_extra_large_box\",\n" +
                                "          \"name\": \"FedEx® Extra Large Box\",\n" +
                                "          \"description\": \"FedEx® Extra Large Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_large_box\",\n" +
                                "          \"name\": \"FedEx® Large Box\",\n" +
                                "          \"description\": \"FedEx® Large Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_medium_box\",\n" +
                                "          \"name\": \"FedEx® Medium Box\",\n" +
                                "          \"description\": \"FedEx® Medium Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_pak\",\n" +
                                "          \"name\": \"FedEx® Pak\",\n" +
                                "          \"description\": \"FedEx® Pak\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_small_box\",\n" +
                                "          \"name\": \"FedEx® Small Box\",\n" +
                                "          \"description\": \"FedEx® Small Box\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"fedex_tube\",\n" +
                                "          \"name\": \"FedEx® Tube\",\n" +
                                "          \"description\": \"FedEx® Tube\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"package_id\": null,\n" +
                                "          \"package_code\": \"package\",\n" +
                                "          \"name\": \"Package\",\n" +
                                "          \"description\": \"Package. Longest side plus the distance around the thickest part is less than or equal to 84\\\"\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"options\": [\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_account\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_country_code\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_party\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"bill_to_postal_code\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"collect_on_delivery\",\n" +
                                "          \"default_value\": \"\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"contains_alcohol\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"delivered_duty_paid\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"dry_ice\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"dry_ice_weight\",\n" +
                                "          \"default_value\": \"0\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"non_machinable\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"name\": \"saturday_delivery\",\n" +
                                "          \"default_value\": \"false\",\n" +
                                "          \"description\": \"\"\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"request_id\": \"81a0e0f0-4fed-4b6b-8965-56e1b82baad2\",\n" +
                                "  \"errors\": []\n" +
                                "}")
                        .withDelay(TimeUnit.SECONDS, 1));

        Map<String, String> listOfCarriers = new ShipEngine(customConfig).listCarriers();
//        assertEquals(List.class, listOfCarriers.getClass());
        assertEquals(HashMap.class, listOfCarriers.getClass());
    }

    @Test
    public void successfulCreateLabelUsingShipmentDetails() {
        new MockServerClient("127.0.0.1", 1080)
                .when(request()
                                .withMethod("POST")
                                .withPath("/v1/labels"),
                        Times.exactly(1))
                .respond(response()
                        .withStatusCode(200)
                        .withBody("{\n" +
                                "  \"batch_id\": \"\",\n" +
                                "  \"carrier_code\": \"stamps_com\",\n" +
                                "  \"carrier_id\": \"se-656171\",\n" +
                                "  \"charge_event\": \"carrier_default\",\n" +
                                "  \"created_at\": \"2021-08-05T16:47:47.8768838Z\",\n" +
                                "  \"display_scheme\": \"label\",\n" +
                                "  \"form_download\": null,\n" +
                                "  \"insurance_claim\": null,\n" +
                                "  \"insurance_cost\": {\n" +
                                "    \"amount\": 0.0,\n" +
                                "    \"currency\": \"usd\"\n" +
                                "  },\n" +
                                "  \"is_international\": false,\n" +
                                "  \"is_return_label\": false,\n" +
                                "  \"label_download\": {\n" +
                                "    \"href\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf\",\n" +
                                "    \"pdf\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf\",\n" +
                                "    \"png\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.png\",\n" +
                                "    \"zpl\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.zpl\"\n" +
                                "  },\n" +
                                "  \"label_format\": \"pdf\",\n" +
                                "  \"label_id\": \"se-799373193\",\n" +
                                "  \"label_image_id\": null,\n" +
                                "  \"label_layout\": \"4x6\",\n" +
                                "  \"package_code\": \"package\",\n" +
                                "  \"packages\": [\n" +
                                "    {\n" +
                                "      \"dimensions\": {\n" +
                                "        \"height\": 0.0,\n" +
                                "        \"length\": 0.0,\n" +
                                "        \"unit\": \"inch\",\n" +
                                "        \"width\": 0.0\n" +
                                "      },\n" +
                                "      \"external_package_id\": null,\n" +
                                "      \"insured_value\": {\n" +
                                "        \"amount\": 0.0,\n" +
                                "        \"currency\": \"usd\"\n" +
                                "      },\n" +
                                "      \"label_messages\": {\n" +
                                "        \"reference1\": null,\n" +
                                "        \"reference2\": null,\n" +
                                "        \"reference3\": null\n" +
                                "      },\n" +
                                "      \"package_code\": \"package\",\n" +
                                "      \"package_id\": 80328023,\n" +
                                "      \"sequence\": 1,\n" +
                                "      \"tracking_number\": \"9400111899560334651289\",\n" +
                                "      \"weight\": {\n" +
                                "        \"unit\": \"ounce\",\n" +
                                "        \"value\": 1.0\n" +
                                "      }\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"rma_number\": null,\n" +
                                "  \"service_code\": \"usps_first_class_mail\",\n" +
                                "  \"ship_date\": \"2021-08-05T00:00:00Z\",\n" +
                                "  \"shipment_cost\": {\n" +
                                "    \"amount\": 3.35,\n" +
                                "    \"currency\": \"usd\"\n" +
                                "  },\n" +
                                "  \"shipment_id\": \"se-144794216\",\n" +
                                "  \"status\": \"completed\",\n" +
                                "  \"trackable\": true,\n" +
                                "  \"tracking_number\": \"9400111899560334651289\",\n" +
                                "  \"tracking_status\": \"in_transit\",\n" +
                                "  \"voided\": false,\n" +
                                "  \"voided_at\": null\n" +
                                "}")
                        .withDelay(TimeUnit.SECONDS, 1));

        Map<String, Object> shipmentDetails = new HashMap<>() {{
            put("shipment", new HashMap<>() {{
                put("carrier_id", "se-1234");
                put("service_code", "usps_first_class_mail");
                put("external_order_id", "string");
                put("items", new ArrayList<>());
                put("tax_identifiers", new ArrayList<>() {{
                    add(new HashMap<>() {{
                        put("taxable_entity_type", "shipper");
                        put("identifier_type", "vat");
                        put("issuing_authority", "string");
                        put("value", "string");
                    }});
                }});
                put("external_shipment_id", "string");
                put("ship_date", "2018-09-23T00:00:00.000Z");
                put("ship_to", new HashMap<>() {{
                    put("name", "John Doe");
                    put("phone", "1-123-456-7894");
                    put("company_name", "The Home Depot");
                    put("address_line1", "1999 Bishop Grandin Blvd.");
                    put("address_line2", "Unit 408");
                    put("address_line3", "Building #7");
                    put("city_locality", "Winnipeg");
                    put("state_province", "Manitoba");
                    put("postal_code", "78756");
                    put("country_code", "CA");
                    put("address_residential_indicator", "no");
                }});
                put("ship_from", new HashMap<>() {{
                    put("name", "John Doe");
                    put("phone", "1-123-456-7894");
                    put("company_name", "The Home Depot");
                    put("address_line1", "1999 Bishop Grandin Blvd.");
                    put("address_line2", "Unit 408");
                    put("address_line3", "Building #7");
                    put("city_locality", "Winnipeg");
                    put("state_province", "Manitoba");
                    put("postal_code", "78756");
                    put("country_code", "CA");
                    put("address_residential_indicator", "no");
                }});
            }});
        }};

        Map<String, String> labelData = new ShipEngine(customConfig).createLabelFromShipmentDetails(shipmentDetails);
        assertEquals("stamps_com", labelData.get("carrier_code"));
    }

    @Test
    public void successfulCreateLabelUsingLabelId() {
        new MockServerClient("127.0.0.1", 1080)
                .when(request()
                                .withMethod("POST")
                                .withPath("/v1/labels/rates/se-1234"),
                        Times.exactly(1))
                .respond(response()
                        .withStatusCode(200)
                        .withBody("{\n" +
                                "  \"batch_id\": \"\",\n" +
                                "  \"carrier_code\": \"stamps_com\",\n" +
                                "  \"carrier_id\": \"se-656171\",\n" +
                                "  \"charge_event\": \"carrier_default\",\n" +
                                "  \"created_at\": \"2021-08-05T16:47:47.8768838Z\",\n" +
                                "  \"display_scheme\": \"label\",\n" +
                                "  \"form_download\": null,\n" +
                                "  \"insurance_claim\": null,\n" +
                                "  \"insurance_cost\": {\n" +
                                "    \"amount\": 0.0,\n" +
                                "    \"currency\": \"usd\"\n" +
                                "  },\n" +
                                "  \"is_international\": false,\n" +
                                "  \"is_return_label\": false,\n" +
                                "  \"label_download\": {\n" +
                                "    \"href\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf\",\n" +
                                "    \"pdf\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf\",\n" +
                                "    \"png\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.png\",\n" +
                                "    \"zpl\": \"https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.zpl\"\n" +
                                "  },\n" +
                                "  \"label_format\": \"pdf\",\n" +
                                "  \"label_id\": \"se-799373193\",\n" +
                                "  \"label_image_id\": null,\n" +
                                "  \"label_layout\": \"4x6\",\n" +
                                "  \"package_code\": \"package\",\n" +
                                "  \"packages\": [\n" +
                                "    {\n" +
                                "      \"dimensions\": {\n" +
                                "        \"height\": 0.0,\n" +
                                "        \"length\": 0.0,\n" +
                                "        \"unit\": \"inch\",\n" +
                                "        \"width\": 0.0\n" +
                                "      },\n" +
                                "      \"external_package_id\": null,\n" +
                                "      \"insured_value\": {\n" +
                                "        \"amount\": 0.0,\n" +
                                "        \"currency\": \"usd\"\n" +
                                "      },\n" +
                                "      \"label_messages\": {\n" +
                                "        \"reference1\": null,\n" +
                                "        \"reference2\": null,\n" +
                                "        \"reference3\": null\n" +
                                "      },\n" +
                                "      \"package_code\": \"package\",\n" +
                                "      \"package_id\": 80328023,\n" +
                                "      \"sequence\": 1,\n" +
                                "      \"tracking_number\": \"9400111899560334651289\",\n" +
                                "      \"weight\": {\n" +
                                "        \"unit\": \"ounce\",\n" +
                                "        \"value\": 1.0\n" +
                                "      }\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"rma_number\": null,\n" +
                                "  \"service_code\": \"usps_first_class_mail\",\n" +
                                "  \"ship_date\": \"2021-08-05T00:00:00Z\",\n" +
                                "  \"shipment_cost\": {\n" +
                                "    \"amount\": 3.35,\n" +
                                "    \"currency\": \"usd\"\n" +
                                "  },\n" +
                                "  \"shipment_id\": \"se-144794216\",\n" +
                                "  \"status\": \"completed\",\n" +
                                "  \"trackable\": true,\n" +
                                "  \"tracking_number\": \"9400111899560334651289\",\n" +
                                "  \"tracking_status\": \"in_transit\",\n" +
                                "  \"voided\": false,\n" +
                                "  \"voided_at\": null\n" +
                                "}")
                        .withDelay(TimeUnit.SECONDS, 1));

        String labelId = "se-1234";
        Map<String, Object> labelParams = new HashMap<>() {{
            put("label_layout", "4x6");
            put("label_format", "pdf");
            put("label_download_type", "url");
        }};

        Map<String, String> labelData = new ShipEngine(customConfig).createLabelFromRateId(labelId, labelParams);
        assertEquals("se-799373193", labelData.get("label_id"));
    }

    @Test
    public void successfulVoidLabelWithLabelId() {
        String labelId = "se-799373193";
        new MockServerClient("127.0.0.1", 1080)
                .when(request()
                                .withMethod("GET")
                                .withPath("/v1/labels/se-799373193/void"),
                        Times.exactly(1))
                .respond(response()
                        .withStatusCode(200)
                        .withBody("{\n" +
                                "  \"approved\": true,\n" +
                                "  \"message\": \"This label has been voided.\"\n" +
                                "}")
                        .withDelay(TimeUnit.SECONDS, 1));

        Map<String, String> voidLabelResult = new ShipEngine(customConfig).voidLabelWithLabelId(labelId);
        assertEquals("This label has been voided.", voidLabelResult.get("message"));
    }

    @Test
    public void successfulGetRateFromShipmentDetails() {
        new MockServerClient("127.0.0.1", 1080)
                .when(request()
                                .withMethod("POST")
                                .withPath("/v1/rates"),
                        Times.exactly(1))
                .respond(response()
                        .withStatusCode(200)
                        .withBody("{\n" +
                                "  \"shipmentId\": \"se-141694059\",\n" +
                                "  \"carrierId\": \"se-161650\",\n" +
                                "  \"serviceCode\": \"usps_first_class_mail\",\n" +
                                "  \"externalOrderId\": null,\n" +
                                "  \"items\": [],\n" +
                                "  \"taxIdentifiers\": null,\n" +
                                "  \"externalShipmentId\": null,\n" +
                                "  \"shipDate\": \"2021-07-28T00:00:00Z\",\n" +
                                "  \"createdAt\": \"2021-07-28T16:56:40.257Z\",\n" +
                                "  \"modifiedAt\": \"2021-07-28T16:56:40.223Z\",\n" +
                                "  \"shipmentStatus\": \"pending\",\n" +
                                "  \"shipTo\": {\n" +
                                "    \"name\": \"James Atkinson\",\n" +
                                "    \"phone\": null,\n" +
                                "    \"companyName\": null,\n" +
                                "    \"addressLine1\": \"28793 Fox Fire Lane\",\n" +
                                "    \"addressLine2\": null,\n" +
                                "    \"addressLine3\": null,\n" +
                                "    \"cityLocality\": \"Shell Knob\",\n" +
                                "    \"stateProvince\": \"MO\",\n" +
                                "    \"postalCode\": \"65747\",\n" +
                                "    \"countryCode\": \"US\",\n" +
                                "    \"addressResidentialIndicator\": \"yes\"\n" +
                                "  },\n" +
                                "  \"shipFrom\": {\n" +
                                "    \"name\": \"Medals of America\",\n" +
                                "    \"phone\": \"800-308-0849\",\n" +
                                "    \"companyName\": null,\n" +
                                "    \"addressLine1\": \"114 Southchase Blvd\",\n" +
                                "    \"addressLine2\": null,\n" +
                                "    \"addressLine3\": null,\n" +
                                "    \"cityLocality\": \"Fountain Inn\",\n" +
                                "    \"stateProvince\": \"SC\",\n" +
                                "    \"postalCode\": \"29644\",\n" +
                                "    \"countryCode\": \"US\",\n" +
                                "    \"addressResidentialIndicator\": \"unknown\"\n" +
                                "  },\n" +
                                "  \"warehouseId\": null,\n" +
                                "  \"returnTo\": {\n" +
                                "    \"name\": \"Medals of America\",\n" +
                                "    \"phone\": \"800-308-0849\",\n" +
                                "    \"companyName\": null,\n" +
                                "    \"addressLine1\": \"114 Southchase Blvd\",\n" +
                                "    \"addressLine2\": null,\n" +
                                "    \"addressLine3\": null,\n" +
                                "    \"cityLocality\": \"Fountain Inn\",\n" +
                                "    \"stateProvince\": \"SC\",\n" +
                                "    \"postalCode\": \"29644\",\n" +
                                "    \"countryCode\": \"US\",\n" +
                                "    \"addressResidentialIndicator\": \"unknown\"\n" +
                                "  },\n" +
                                "  \"confirmation\": \"none\",\n" +
                                "  \"customs\": {\n" +
                                "    \"contents\": \"merchandise\",\n" +
                                "    \"nonDelivery\": \"return_to_sender\",\n" +
                                "    \"customsItems\": []\n" +
                                "  },\n" +
                                "  \"advancedOptions\": {\n" +
                                "    \"billToAccount\": null,\n" +
                                "    \"billToCountryCode\": null,\n" +
                                "    \"billToParty\": null,\n" +
                                "    \"billToPostalCode\": null,\n" +
                                "    \"containsAlcohol\": null,\n" +
                                "    \"deliveryDutyPaid\": null,\n" +
                                "    \"dryIce\": null,\n" +
                                "    \"dryIceWeight\": null,\n" +
                                "    \"nonMachinable\": null,\n" +
                                "    \"saturdayDelivery\": null,\n" +
                                "    \"useUPSGroundFreightPricing\": null,\n" +
                                "    \"freightClass\": null,\n" +
                                "    \"customField1\": null,\n" +
                                "    \"customField2\": null,\n" +
                                "    \"customField3\": null,\n" +
                                "    \"originType\": null,\n" +
                                "    \"shipperRelease\": null,\n" +
                                "    \"collectOnDelivery\": null\n" +
                                "  },\n" +
                                "  \"originType\": null,\n" +
                                "  \"insuranceProvider\": \"none\",\n" +
                                "  \"tags\": [],\n" +
                                "  \"orderSourceCode\": null,\n" +
                                "  \"packages\": [\n" +
                                "    {\n" +
                                "      \"packageCode\": \"package\",\n" +
                                "      \"weight\": {\n" +
                                "        \"value\": 2.9,\n" +
                                "        \"unit\": \"ounce\"\n" +
                                "      },\n" +
                                "      \"dimensions\": {\n" +
                                "        \"unit\": \"inch\",\n" +
                                "        \"length\": 0,\n" +
                                "        \"width\": 0,\n" +
                                "        \"height\": 0\n" +
                                "      },\n" +
                                "      \"insuredValue\": {\n" +
                                "        \"currency\": \"usd\",\n" +
                                "        \"amount\": 0\n" +
                                "      },\n" +
                                "      \"trackingNumber\": null,\n" +
                                "      \"labelMessages\": {\n" +
                                "        \"reference1\": \"4051492\",\n" +
                                "        \"reference2\": null,\n" +
                                "        \"reference3\": null\n" +
                                "      },\n" +
                                "      \"externalPackageId\": null\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"totalWeight\": {\n" +
                                "    \"value\": 2.9,\n" +
                                "    \"unit\": \"ounce\"\n" +
                                "  },\n" +
                                "  \"rateResponse\": {\n" +
                                "    \"rates\": [\n" +
                                "      {\n" +
                                "        \"rateId\": \"se-784001113\",\n" +
                                "        \"rateType\": \"shipment\",\n" +
                                "        \"carrierId\": \"se-161650\",\n" +
                                "        \"shippingAmount\": {\n" +
                                "          \"currency\": \"usd\",\n" +
                                "          \"amount\": 3.12\n" +
                                "        },\n" +
                                "        \"insuranceAmount\": {\n" +
                                "          \"currency\": \"usd\",\n" +
                                "          \"amount\": 0\n" +
                                "        },\n" +
                                "        \"confirmationAmount\": {\n" +
                                "          \"currency\": \"usd\",\n" +
                                "          \"amount\": 0\n" +
                                "        },\n" +
                                "        \"otherAmount\": {\n" +
                                "          \"currency\": \"usd\",\n" +
                                "          \"amount\": 0\n" +
                                "        },\n" +
                                "        \"taxAmount\": null,\n" +
                                "        \"zone\": 5,\n" +
                                "        \"packageType\": \"package\",\n" +
                                "        \"deliveryDays\": 3,\n" +
                                "        \"guaranteedService\": false,\n" +
                                "        \"estimatedDeliveryDate\": \"2021-07-31T00:00:00Z\",\n" +
                                "        \"carrierDeliveryDays\": \"3\",\n" +
                                "        \"shipDate\": \"2021-07-28T00:00:00Z\",\n" +
                                "        \"negotiatedRate\": false,\n" +
                                "        \"serviceType\": \"USPS First Class Mail\",\n" +
                                "        \"serviceCode\": \"usps_first_class_mail\",\n" +
                                "        \"trackable\": true,\n" +
                                "        \"carrierCode\": \"usps\",\n" +
                                "        \"carrierNickname\": \"USPS\",\n" +
                                "        \"carrierFriendlyName\": \"USPS\",\n" +
                                "        \"validationStatus\": \"valid\",\n" +
                                "        \"warningMessages\": [],\n" +
                                "        \"errorMessages\": []\n" +
                                "      }\n" +
                                "    ],\n" +
                                "    \"invalidRates\": [],\n" +
                                "    \"rateRequestId\": \"se-85117731\",\n" +
                                "    \"shipmentId\": \"se-141694059\",\n" +
                                "    \"createdAt\": \"2021-07-28T16:56:40.6148892Z\",\n" +
                                "    \"status\": \"completed\",\n" +
                                "    \"errors\": []\n" +
                                "  }\n" +
                                "}")
                        .withDelay(TimeUnit.SECONDS, 1));

        Map<String, Object> shipmentDetails = new HashMap<>() {{
            put("shipment", new HashMap<>() {{
                put("carrier_id", "se-1234");
                put("service_code", "usps_first_class_mail");
                put("external_order_id", "string");
                put("items", new ArrayList<>());
                put("tax_identifiers", new ArrayList<>() {{
                    add(new HashMap<>() {{
                        put("taxable_entity_type", "shipper");
                        put("identifier_type", "vat");
                        put("issuing_authority", "string");
                        put("value", "string");
                    }});
                }});
                put("external_shipment_id", "string");
                put("ship_date", "2018-09-23T00:00:00.000Z");
                put("ship_to", new HashMap<>() {{
                    put("name", "John Doe");
                    put("phone", "1-123-456-7894");
                    put("company_name", "The Home Depot");
                    put("address_line1", "1999 Bishop Grandin Blvd.");
                    put("address_line2", "Unit 408");
                    put("address_line3", "Building #7");
                    put("city_locality", "Winnipeg");
                    put("state_province", "Manitoba");
                    put("postal_code", "78756");
                    put("country_code", "CA");
                    put("address_residential_indicator", "no");
                }});
                put("ship_from", new HashMap<>() {{
                    put("name", "John Doe");
                    put("phone", "1-123-456-7894");
                    put("company_name", "The Home Depot");
                    put("address_line1", "1999 Bishop Grandin Blvd.");
                    put("address_line2", "Unit 408");
                    put("address_line3", "Building #7");
                    put("city_locality", "Winnipeg");
                    put("state_province", "Manitoba");
                    put("postal_code", "78756");
                    put("country_code", "CA");
                    put("address_residential_indicator", "no");
                }});
            }});
        }};

        Map<String, String> rateData = new ShipEngine(customConfig).getRatesWithShipmentDetails(shipmentDetails);
        assertEquals("se-141694059", rateData.get("shipmentId"));
    }
}