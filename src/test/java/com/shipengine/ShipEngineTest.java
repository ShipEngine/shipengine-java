package com.shipengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import com.shipengine.ShipEngine;

/**
 * Unit test for simple App.
 */
public class ShipEngineTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        new ShipEngine();
        assertEquals( new ShipEngine().sayHi(), "Hello World from CONFIG!" );
    }
}
