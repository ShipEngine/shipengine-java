package com.shipengine;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class ShipEngineTest {
    /**
     * A stub Api-Key to be used for testing
     */
    private final String apiKey = "TEST_vMiVbICUjBz4BZjq0TRBLC/9MrxY4+yjvb1G1RMxlJs";

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertEquals(
                new ShipEngine(apiKey).trackUsingCarrierCodeAndTrackingNumber(),
                "https://api.shipengine.com/"
        );
    }

    /**
     * Testing Address Validation with a valid address.
     */
//    @Test
//    public void successfulAddressValidation() {
//        List<HashMap<String , String>> unvalidatedAddress = Arrays.asList(
//                new HashMap<String , String>() {{
//                    put("name", "ShipEngine");
//                    put("company", "Auctane");
//                    put("phone", "1-123-456-7891");
//                    put("address_line1", "3800 N Lamar Blvd");
//                    put("address_line2", "ste 220");
//                    put("city_locality", "Austin");
//                    put("state_province", "TX");
//                    put("postal_code", "78756");
//                    put("country_code", "US");
//                    put("address_residential_indicator", "unknown");
//                }}
//        );
//        List validatedAddress = new ShipEngine(apiKey).validateAddresses(unvalidatedAddress);
//        System.out.println("validatedAddress = " + validatedAddress);
//    }

    /**
     * Testing successful call to listCarriers which fetches all
     * carrier accounts connected o given ShipEngine Account.
     */
    @Test
    public void successfulListCarriers() {
        Map listOfCarriers = new ShipEngine(apiKey).listCarriers();
        System.out.println("listOfCarriers = " + listOfCarriers);
    }
}
