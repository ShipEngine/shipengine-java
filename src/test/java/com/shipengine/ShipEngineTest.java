package com.shipengine;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class ShipEngineTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertEquals(new ShipEngine("test").trackUsingCarrierCodeAndTrackingNumber(), "https://api.shipengine.com/");
    }

    @Test
    public void successfulListCarriers() {
        Map listOfCarriers = new ShipEngine("TEST_vMiVbICUjBz4BZjq0TRBLC/9MrxY4+yjvb1G1RMxlJs").listCarriers();
        System.out.println("listOfCarriers = " + listOfCarriers);
    }
}
