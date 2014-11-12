package edu.gatech.gem5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.readers.ShipReader;
import edu.gatech.gem5.game.readers.ShieldReader;



public class ShipDamageTest {


    @Test
    public void testNoShields() {
        ShipReader r = new ShipReader("/data/Ships.json");
        ShipType stype = r.get("tester");
        Ship ship = new Ship(stype);

        // Test to see if this doesn't hurt the ship
        ship.receiveDamage(0);
        assertEquals(ship.getHealth(), 25.0);

        // Test to see if this damages the ship by 10
        ship.receiveDamage(10);
        assertEquals(ship.getHealth(), 15.0);

        // Test to see if this finishes off the ship
        ship.receiveDamage(15);
        assertEquals(ship.getHealth(), 0.0);
        assertTrue(ship.isDestroyed());
    }
    @Test
    public void testNoShieldsOverkill() {
        ShipReader r = new ShipReader("/data/Ships.json");
        ShipType stype = r.get("tester");
        Ship ship = new Ship(stype);

        // Test to see if ship's health doesn't go into negatives
        ship.receiveDamage(100);
        assertEquals(ship.getHealth(), 0.0);
        assertTrue(ship.isDestroyed());
    }

    @Test
    public void testShields() {
        ShipReader r = new ShipReader("/data/Ships.json");
        ShipType stype = r.get("tester");
        Ship ship = new Ship(stype);

        ShieldReader sr = new ShieldReader("/data/Shields.json");
        ShieldType srtype = sr.get("kinetic_stabilizers");
        ship.addUpgrade(srtype);

        // Test to see if the shield absorbs this
        ship.receiveDamage(50);
        assertEquals(ship.getHealth(), 25.0);

        // Test to see if the shield partially absorbs this
        ship.receiveDamage(70);
        assertEquals(ship.getHealth(), 5.0);

        // Test to see if the ship is destroyed
        ship.receiveDamage(5);
        assertEquals(ship.getHealth(), 0.0);
        assertTrue(ship.isDestroyed());
    }

}
