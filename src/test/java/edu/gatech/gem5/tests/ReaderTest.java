package edu.gatech.gem5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.gem5.game.readers.*;
import edu.gatech.gem5.game.Ship;

import java.util.Map;

/**
 * Test reader objects.
 *
 *
 * @author  Creston Bunch
 * @version 1.0.0
 */

public class ReaderTest {
    
    @Test
    public void testShips() {
        ShipReader r = new ShipReader();
        Map<String, Ship> trial1 = r.load("/data/Ships.json");
        assertEquals(20, trial1.size());
        assertEquals("Bolt", trial1.get("bolt").getName());
    }

}
