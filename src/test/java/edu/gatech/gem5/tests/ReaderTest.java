package edu.gatech.gem5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.gem5.game.readers.*;
import edu.gatech.gem5.game.data.*;
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
        ShipReader r = new ShipReader("/data/Ships.json");
        //assertEquals(2, r.size());
        assertEquals("Tester", r.get("tester").getName());
        assertEquals("This is a test ship.",
                r.get("tester").getDescription());
        assertEquals(10, r.get("tester").getCargoSlots());
        assertEquals(1, r.get("tester").getWeaponSlots());
        assertEquals(2, r.get("tester").getShieldSlots());
        assertEquals(3, r.get("tester").getGadgetSlots());
        assertEquals(4, r.get("tester").getCrewSlots());
        assertEquals(11, r.get("tester").getRange());
        assertEquals(1, r.get("tester").getFuelCost());
        assertEquals(800, r.get("tester").getPrice());
        assertEquals(5, r.get("tester").getBounty());
        assertEquals(25, r.get("tester").getHullStrength());
    }

    @Test
    public void testGadgets() {
        GadgetReader r = new GadgetReader("/data/Gadgets.json");
        //assertEquals(5, r.size());
        assertEquals("Extra Cargo Bays", r.get("cargo_bays").getName());
        assertEquals(
            "Adds 5 additional cargo bays.",
            r.get("cargo_bays").getDescription()
        );
        assertEquals(
            5, r.get("cargo_bays").getBonus().get("cargo").intValue()
        );
    }

    @Test
    public void testGoods() {
        GoodReader r = new GoodReader("/data/Goods.json");
        //assertEquals(12, r.size());
        assertEquals("Water", r.get("water").getName());
        assertEquals(40, r.get("water").getValue());
        assertEquals(true, r.get("water").isLegal());
    }

    @Test
    public void testShields() {
        ShieldReader r = new ShieldReader("/data/Shields.json");
        //assertEquals(8, r.size());
        assertEquals(
            "Kinetic Stabilizers", r.get("kinetic_stabilizers").getName()
        );
        assertEquals("", r.get("kinetic_stabilizers").getDescription());
        assertEquals(1000, r.get("kinetic_stabilizers").getPrice());
        assertEquals(1, r.get("kinetic_stabilizers").getFactor(), 0.0);
        assertEquals(100, r.get("kinetic_stabilizers").getIntegrity());
    }

    @Test
    public void testWeapons() {
        WeaponReader r = new WeaponReader("/data/Weapons.json");
        //assertEquals(10, r.size());
        assertEquals("Pulse Laser", r.get("pulse1").getName());
        assertEquals("", r.get("pulse1").getDescription());
        assertEquals(1000, r.get("pulse1").getPrice());
        assertEquals(4, r.get("pulse1").getDamage());
        assertEquals(0.5, r.get("pulse1").getAccuracy(), 0.0);
        assertEquals(0.75, r.get("pulse1").getRate(), 0.0);
    }

    @Test
    public void testCompanies() {
        CompanyReader r = new CompanyReader("/data/Companies.json");
        //assertEquals(22, r.size());
        assertEquals("TerraceCo", r.get("terraceco").getName());
        assertEquals("", r.get("terraceco").getDescription());
        assertEquals("water", r.get("terraceco").getProducts().get(0));
        assertEquals("food", r.get("terraceco").getProducts().get(1));
        assertEquals(1, r.get("terraceco").getMinTech());
        assertEquals(6, r.get("terraceco").getMaxTech());
        assertEquals(
            0.33, (double) r.get("terraceco").getOccurrence(), 0.0
        );
        assertEquals(
            1.0, (double) r.get("terraceco").getWealth(), 0.0
        );
        assertEquals(
            0,
            (double) r.get("terraceco").getEnvironments().get("desert"),
            0.0

        );
        assertEquals(
            1.5,
            (double) r.get("terraceco").getEnvironments().get("sweet_water")
            , 0.0
        );
        assertEquals(
            0,
            (double) r.get("terraceco").getEnvironments().get("poor_soil"),
            0.0
        );
        assertEquals(
            1.5,
            (double) r.get("terraceco").getEnvironments().get("rich_soil"),
            0.0
        );
        assertEquals(
            1.5,
            (double) r.get("terraceco").getGovernments().get("capitalist"),
            0.0
        );
        assertEquals(
            1.25,
            (double) r.get("terraceco").getGovernments().get("feudal"),
            0.0
        );
        assertEquals(
            1.25, (double) r.get("terraceco").getGovernments().get("satori")
            , 0.0
        );
    }

    @Test
    public void testGovernments() {
        GovernmentReader r = new GovernmentReader("/data/Governments.json");
        //assertEquals(16, r.size());
        assertEquals("Anarchy", r.get("anarchy").getName());
        assertEquals("", r.get("anarchy").getDescription());
        assertEquals(0.0, (double) r.get("anarchy").getPolice(), 0.0);
        assertEquals(0.1, (double) r.get("anarchy").getTraders(), 0.0);
        assertEquals(0.9, (double) r.get("anarchy").getPirates(), 0.0);
        assertEquals(1.0, (double) r.get("anarchy").getCorruption(), 0.0);
        assertEquals(0, r.get("anarchy").getMinTech());
        assertEquals(4, r.get("anarchy").getMaxTech());
        assertEquals(0.1, (double) r.get("anarchy").getWealth(), 0.0);
        assertEquals(
            1.25, (double) r.get("anarchy").getDemand().get("food"), 0.0
        );
        assertEquals(
            0.5, (double) r.get("cybernetic").getSupply().get("robots"), 0.0
        );
        assertEquals(
            "drugs", r.get("cybernetic").getForbidden().get(0)
        );
        assertEquals(
            "firearms", r.get("cybernetic").getForbidden().get(1)
        );
        assertEquals(
            0.08823529, (double) r.get("cybernetic").getOccurrence(), 0.0
        );
    }

    @Test
    public void testConditions() {
        ConditionReader r = new ConditionReader("/data/Conditions.json");
        //assertEquals(8, r.size());
        assertEquals("None", r.get("none").getName());
        assertEquals(
            2.0, (double) r.get("drought").getDemand().get("water"), 0.0
        );
        assertEquals(0.5625, (double) r.get("none").getOccurrence(), 0.0);
    }

    @Test
    public void testEnvironments() {
        EnvironmentReader r = new EnvironmentReader("/data/Environments.json");
        //assertEquals(13, r.size());
        assertEquals("Nothing", r.get("nothing").getName());
        assertEquals(
            2.0,
            (double) r.get("mineral_poor").getDemand().get("ore"),
            0.0
        );
        assertEquals(
            0.5,
            (double) r.get("mineral_rich").getSupply().get("ore"),
            0.0
        );
        assertEquals(1.0, (double) r.get("nothing").getWealth(), 0.0);
    }

    @Test
    public void testTechLevels() {
        TechReader r = new TechReader("/data/TechLevels.json");
        //assertEquals(8, r.size());
        assertEquals("Pre-agriculture", r.get(0).getName());
        assertEquals(0, r.get(0).getRank());
        assertEquals(0.3, r.get(0).getOccurrence(), 0.0);
        assertEquals(0.3, r.get(0).getWealth(), 0.0);
    }

}
