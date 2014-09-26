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
        ShipReader r = new ShipReader();
        Map<String, ShipType> trial = r.load("/data/Ships.json");
        assertEquals(2, trial.size());
        assertEquals("Tester", trial.get("tester").getName());
        assertEquals("This is a test ship.",
                trial.get("tester").getDescription());
        assertEquals(10, trial.get("tester").getCargoSlots());
        assertEquals(1, trial.get("tester").getWeaponSlots());
        assertEquals(2, trial.get("tester").getShieldSlots());
        assertEquals(3, trial.get("tester").getGadgetSlots());
        assertEquals(4, trial.get("tester").getCrewSlots());
        assertEquals(11, trial.get("tester").getRange());
        assertEquals(1, trial.get("tester").getFuelCost());
        assertEquals(800, trial.get("tester").getPrice());
        assertEquals(5, trial.get("tester").getBounty());
        assertEquals(25, trial.get("tester").getHullStrength());
    }

    @Test
    public void testGadgets() {
        GadgetReader r = new GadgetReader();
        Map<String, GadgetType> trial = r.load("/data/Gadgets.json");
        assertEquals(5, trial.size());
        assertEquals("Extra Cargo Bays", trial.get("cargo_bays").getName());
        assertEquals(
            "Adds 5 additional cargo bays.",
            trial.get("cargo_bays").getDescription()
        );
        assertEquals(
            5, trial.get("cargo_bays").getBonus().get("cargo").intValue()
        );
    }

    @Test
    public void testGoods() {
        GoodReader r = new GoodReader();
        Map<String, GoodType> trial = r.load("/data/Goods.json");
        assertEquals(12, trial.size());
        assertEquals("Water", trial.get("water").getName());
        assertEquals(40, trial.get("water").getValue());
        assertEquals(true, trial.get("water").isLegal());
    }

    @Test
    public void testShields() {
        ShieldReader r = new ShieldReader();
        Map<String, ShieldType> trial = r.load("/data/Shields.json");
        assertEquals(8, trial.size());
        assertEquals(
            "Kinetic Stabilizers", trial.get("kinetic_stabilizers").getName()
        );
        assertEquals("", trial.get("kinetic_stabilizers").getDescription());
        assertEquals(1000, trial.get("kinetic_stabilizers").getPrice());
        assertEquals(1, trial.get("kinetic_stabilizers").getFactor(), 0.0);
        assertEquals(100, trial.get("kinetic_stabilizers").getIntegrity());
    }

    @Test
    public void testWeapons() {
        WeaponReader r = new WeaponReader();
        Map<String, WeaponType> trial = r.load("/data/Weapons.json");
        assertEquals(10, trial.size());
        assertEquals("Pulse Laser", trial.get("pulse1").getName());
        assertEquals("", trial.get("pulse1").getDescription());
        assertEquals(1000, trial.get("pulse1").getPrice());
        assertEquals(4, trial.get("pulse1").getDamage());
        assertEquals(0.5, trial.get("pulse1").getAccuracy(), 0.0);
        assertEquals(0.75, trial.get("pulse1").getRate(), 0.0);
    }

    @Test
    public void testCompanies() {
        CompanyReader r = new CompanyReader();
        Map<String, CompanyType> trial = r.load("/data/Companies.json");
        assertEquals(22, trial.size());
        assertEquals("TerraceCo", trial.get("terraceco").getName());
        assertEquals("", trial.get("terraceco").getDescription());
        assertEquals("water", trial.get("terraceco").getProducts().get(0));
        assertEquals("food", trial.get("terraceco").getProducts().get(1));
        assertEquals(1, trial.get("terraceco").getMinTech());
        assertEquals(6, trial.get("terraceco").getMaxTech());
        assertEquals(
            0.33, (double) trial.get("terraceco").getOccurrence(), 0.0
        );
        assertEquals(
            1.0, (double) trial.get("terraceco").getWealth(), 0.0
        );
        assertEquals(
            0,
            (double) trial.get("terraceco").getEnvironments().get("desert"),
            0.0

        );
        assertEquals(
            1.5,
            (double) trial.get("terraceco").getEnvironments().get("sweet_water")
            , 0.0
        );
        assertEquals(
            0,
            (double) trial.get("terraceco").getEnvironments().get("poor_soil"),
            0.0
        );
        assertEquals(
            1.5, 
            (double) trial.get("terraceco").getEnvironments().get("rich_soil"),
            0.0
        );
        assertEquals(
            1.5,
            (double) trial.get("terraceco").getGovernments().get("capitalist"),
            0.0
        );
        assertEquals(
            1.25,
            (double) trial.get("terraceco").getGovernments().get("feudal"),
            0.0
        );
        assertEquals(
            1.25, (double) trial.get("terraceco").getGovernments().get("satori")
            , 0.0
        );
    }

    @Test
    public void testGovernments() {
        GovernmentReader r = new GovernmentReader();
        Map<String, GovernmentType> trial = r.load("/data/Governments.json");
        assertEquals(16, trial.size());
        assertEquals("Anarchy", trial.get("anarchy").getName());
        assertEquals("", trial.get("anarchy").getDescription());
        assertEquals(0.0, (double) trial.get("anarchy").getPolice(), 0.0);
        assertEquals(0.1, (double) trial.get("anarchy").getTraders(), 0.0);
        assertEquals(0.9, (double) trial.get("anarchy").getPirates(), 0.0);
        assertEquals(1.0, (double) trial.get("anarchy").getCorruption(), 0.0);
        assertEquals(0, trial.get("anarchy").getMinTech());
        assertEquals(4, trial.get("anarchy").getMaxTech());
        assertEquals(0.1, (double) trial.get("anarchy").getWealth(), 0.0);
        assertEquals(
            1.25, (double) trial.get("anarchy").getDemand().get("food"), 0.0
        );
        assertEquals(
            0.5, (double) trial.get("cybernetic").getSupply().get("robots"), 0.0
        );
        assertEquals(
            "drugs", trial.get("cybernetic").getForbidden().get(0)
        );
        assertEquals(
            "firearms", trial.get("cybernetic").getForbidden().get(1)
        );
        assertEquals(
            0.08823529, (double) trial.get("cybernetic").getOccurrence(), 0.0
        );
    }

    @Test
    public void testConditions() {
        ConditionReader r = new ConditionReader();
        Map<String, ConditionType> trial = r.load("/data/Conditions.json");
        assertEquals(8, trial.size());
        assertEquals("None", trial.get("none").getName());
        assertEquals(
            2.0, (double) trial.get("drought").getDemand().get("water"), 0.0
        );
        assertEquals(0.5625, (double) trial.get("none").getOccurrence(), 0.0);
    }

    @Test
    public void testEnvironments() {
        EnvironmentReader r = new EnvironmentReader();
        Map<String, EnvironmentType> trial = r.load("/data/Environments.json");
        assertEquals(13, trial.size());
        assertEquals("Nothing", trial.get("nothing").getName());
        assertEquals(
            2.0,
            (double) trial.get("mineral_poor").getDemand().get("ore"),
            0.0
        );
        assertEquals(
            0.5,
            (double) trial.get("mineral_rich").getSupply().get("ore"),
            0.0
        );
        assertEquals(1.0, (double) trial.get("nothing").getWealth(), 0.0);
    }

    @Test
    public void testTechLevels() {
        TechReader r = new TechReader();
        Map<Integer, TechType> trial = r.load("/data/TechLevels.json");
        assertEquals(8, trial.size());
        assertEquals("Pre-agriculture", trial.get(0).getName());
        assertEquals(0, trial.get(0).getRank());
        assertEquals(0.3, trial.get(0).getOccurrence(), 0.0);
        assertEquals(0.3, trial.get(0).getWealth(), 0.0);
    }

}
