package com.shipengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ShipEngineTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertEquals(new ShipEngine("test").listCarriers(), "https://api.shipengine.com/");
    }
}
