package edu.gatech.gem5.tests;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.Traderable;
import edu.gatech.gem5.game.Transaction;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.readers.GoodReader;
import edu.gatech.gem5.game.readers.ShipReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jack Mueller
 */
public class TransactionTest {
    /**
     * Character to hold all the ships need for trade.
     */
    static Character player;
    
    /**
     * Map to hold ships with different quantities of goods.
     */
    static Map<String, Ship> ships;
    /**
     * Map to hold tradable entities with different wealth/quantities of goods.
     */
    static Map<String, Traderable> partners;
    /**
     * A member variable to hold transaction instances.
     */
    static Transaction trans;
    /**
     * A reader for ship types.
     */
    static ShipReader shipTypes = new ShipReader("/data/Ships.json");

    public TransactionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //initialize the player
        player = new Character("c1",0,0,0,0,0, null);
        
        //initialize the map
        ships = new HashMap<>();
        ShipType type = shipTypes.get("tester");
        
        //an empty ship
        Ship empty = new Ship(type);
        ships.put("empty", empty);

        //a full ship
        Ship full = new Ship(shipTypes.get("tester"));
        full.addCargo("water", full.getOpenBays());
        ships.put("full", full);
        
        //a ship that can still buy things, one more cargo bay open
        Ship fullButOne = new Ship(shipTypes.get("tester"));
        full.addCargo("water", full.getOpenBays() -1);
        ships.put("fullButOne", fullButOne);
        
        partners = new HashMap<>();
        Planet p = new Planet("0ne");
        
        Traderable dummy = new Planet("one");
        partners.put("dummy", dummy);
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of validateBuy method, of class Transaction.
     */
    @Test
    public void testValidateBuy() {
        System.out.println("validateBuy");
        
        //Happy Path, reaches the end
        Map<String, Integer> purchases = new HashMap<>();
        purchases.put("water", 1);
        player.setShip(ships.get("empty"));
        Traderable partner = partners.get("dummy");
        Transaction instance = new Transaction(player, partner);
        boolean expResult = true;
        boolean result = instance.validateBuy(purchases);
        assertEquals(expResult, result);
    }
    
}
