package edu.gatech.gem5.tests;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.Traderable;
import edu.gatech.gem5.game.Transaction;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.readers.ShipReader;
import java.util.HashMap;
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
 * @author James Jong Han Park
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
        player = new Character("c1", 0, 0, 0, 0, 0, null);

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
        full.addCargo("water", full.getOpenBays() - 1);
        ships.put("fullButOne", fullButOne);

        partners = new HashMap<>();
        Planet p = new Planet("0ne", 1);
        Traderable dummy = new Planet("one", 1);
        p.getSupply();
        dummy.getStock();

        partners.put("dummy", dummy);
        p.getStock();
        //p.get
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
        boolean result = instance.validateBuy(purchases);
        assertTrue(result);
    }

    /**
     * Tests setting player's money.
     */
    @Test(timeout = 250)
    public void testSetMoney() {
        player.setMoney(100000);
        assertEquals(player.getMoney(), 100000);
    }

    /**
     * Tests buying a single item.
     */
    @Test(timeout = 250)
    public void testBuySingle() {
        Map<String, Integer> purchases = new HashMap<>();
        purchases.put("water", 1);
        player.setShip(ships.get("empty"));
        Traderable partner = partners.get("dummy");
        int unitPrice = partner.getSupply().get("water");
        player.setMoney(unitPrice);
        Transaction instance = new Transaction(player, partner);
        instance.buy(purchases);
        assertEquals(player.getMoney(), 0);
        assertEquals(player.getShip().getCargoList().size(), 1);
    }

    /**
     * Tests buying multiple items.
     */
    @Test(timeout = 250)
    public void testBuyMultiple() {
        Map<String, Integer> purchases = new HashMap<>();
        purchases.put("water", 3);
        
        player.setShip(ships.get("empty"));
        Traderable partner = partners.get("dummy");
        int unitPrice = partner.getSupply().get("water");
        player.setMoney(unitPrice * 3);
        Transaction instance = new Transaction(player, partner);
        instance.buy(purchases);
        
        assertEquals(player.getMoney(), 0);
        assertEquals(player.getShip().getCargoList().size(), 1);
    }
    
     /**
     * Tests buying multiple items with multiple map entries.
     */
    @Test(timeout = 250)
    public void testBuyMultipleTwo() {
        Map<String, Integer> purchases = new HashMap<>();
        purchases.put("water", 3);
        purchases.put("water", 2);
        purchases.put("water", 1);
        purchases.put("water", 1);
        purchases.put("water", 1);
        purchases.put("water", 2);
        
        player.setShip(ships.get("empty"));
        Traderable partner = partners.get("dummy");
        int unitPrice = partner.getSupply().get("water");
        player.setMoney(unitPrice * 2);
        Transaction instance = new Transaction(player, partner);
        instance.buy(purchases);
        
        assertEquals(player.getMoney(), 0);
        assertEquals(player.getShip().getCargoList().size(), 1);
    }
    
     /**
     * Tests buying multiple items with multiple map entries.
     */
    @Test(expected = IllegalArgumentException.class, timeout = 250)
    public void testBuyNullMap() {
        Map<String, Integer> purchases = null;
        
        player.setShip(ships.get("empty"));
        Traderable partner = partners.get("dummy");
        int unitPrice = partner.getSupply().get("water");
        player.setMoney(unitPrice * 9);
        Transaction instance = new Transaction(player, partner);
        instance.buy(purchases);
        
        assertEquals(player.getMoney(), 0);
        assertEquals(player.getShip().getCargoList().size(), 9);
    }
    /**
     * Tests buying a null item.
     */
    @Test(expected = IllegalArgumentException.class, timeout = 250)
    public void testBuyNullItems() {
        Map<String, Integer> purchases = new HashMap<>();
        purchases.put(null, null);
        player.setShip(ships.get("empty"));
        Traderable partner = partners.get("dummy");
        int unitPrice = partner.getSupply().get("water");
        player.setMoney(unitPrice * 9);
        Transaction instance = new Transaction(player, partner);
        instance.buy(purchases);
    }
    
    /**
     * Tests buying an item with a null value.
     */
    @Test(expected = IllegalArgumentException.class, timeout = 250)
    public void testBuyNullItemsTwo() {
        Map<String, Integer> purchases = new HashMap<>();
        purchases.put("water", null);
        player.setShip(ships.get("empty"));
        Traderable partner = partners.get("dummy");
        int unitPrice = partner.getSupply().get("water");
        player.setMoney(unitPrice * 9);
        Transaction instance = new Transaction(player, partner);
        instance.buy(purchases);
    }

}
