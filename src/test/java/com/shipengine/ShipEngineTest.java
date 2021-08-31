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
    @Test
    public void successfulAddressValidation() {
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

        List<HashMap<String , String>> unvalidatedAddress = List.of(stubAddress);
        List validatedAddress = new ShipEngine(apiKey).validateAddresses(unvalidatedAddress);
        assertEquals(HashMap.class, validatedAddress.get(0).getClass());
    }

    /**
     * Testing successful call to listCarriers which fetches all
     * carrier accounts connected o given ShipEngine Account.
     */
    @Test
    public void successfulListCarriers() {
        Map listOfCarriers = new ShipEngine(apiKey).listCarriers();
        assertEquals(HashMap.class, listOfCarriers.getClass());
    }
}
