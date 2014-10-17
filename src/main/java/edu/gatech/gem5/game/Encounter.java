package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;
import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.GovernmentType;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.controllers.EncounterController;
import edu.gatech.gem5.game.controllers.PlanetController;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Controller class to handle the logic behind processing Encounters
 *
 */
public class Encounter {

    //TODO: Find a way to properly get these to cast.
    private static final Object[] ships =
        LastAdventures.data.get(ShipType.KEY).values().toArray();

    private static final Object[] weapons =
        LastAdventures.data.get(WeaponType.KEY).values().toArray();

    private static final Object[] shields =
        LastAdventures.data.get(ShieldType.KEY).values().toArray();

    private static final Object[] gadgets =
        LastAdventures.data.get(GadgetType.KEY).values().toArray();

    private static final Object[] goods =
        LastAdventures.data.get(GoodType.KEY).values().toArray();

    private static final double NOTHING = 1;
    private static final double NPC = 0;
    private static final double EVENT = .1;
    private static final Random r = new Random();
    

    /**
     * Randomly generates a pirate, police, or trader encounter
     * @param p The planet the player is currently traveling to.
     *
     */
    public void getEncounter(Planet p) {
        double encounterType = r.nextDouble();
        if (encounterType > NOTHING) {
            //skip the encounter
            LastAdventures.swap(new PlanetController());
        } else if (encounterType > NPC) {
            //encounter with a random NPC
            int seed = Math.max(LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth(), 1);
            getEncounter(seed, p);
        } else if (encounterType > EVENT) {
            //choose randomly from a list of events
            
        }

    }

    private void getEncounter(int seed, Planet p) {
        NPC spawn = null;
        GovernmentType gov = p.getGovernment();
        int policeChance = (int) (gov.getPolice() * 10);
        int traderChance = (int) (gov.getTraders() * 10);
        int pirateChance = (int) (gov.getPirates() * 10);
        int encounter = r.nextInt(policeChance + traderChance + pirateChance) + 1;
        if (encounter <= policeChance) {
            spawn = policeEncounter(seed);
        } else if (encounter <= traderChance) {
            spawn = traderEncounter(seed);
        } else {
            spawn = pirateEncounter(seed);   
        }
        //LastAdventures.swap(new EncounterController(this));
        spawn.processEncounter();

    }

    private Pirate pirateEncounter(int seed) {
        int shipIndex = Math.min(r.nextInt(seed) / 7500, ships.length - 1);
        ShipType shipT = (ShipType) ships[shipIndex];
        Ship ship = new Ship(shipT);

        int weaponsNum = Math.min(r.nextInt(seed) / 10000, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.getWeaponList().add((WeaponType) weapons[i]);
        }

        int shieldsNum = Math.min(r.nextInt(seed) / 15000, shipT.getShieldSlots() - 1);
        for (int i = 0; i < shieldsNum; i++) {
            ship.getShieldList().add((ShieldType) shields[i]);
        }

        int gadgetsNum = Math.min(r.nextInt(seed) / 20000, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.getGadgetList().add((GadgetType) gadgets[i]);
        }

        return Pirate.createPirate(seed, ship);

    }

    private Trader traderEncounter(int seed) {
        int shipIndex = Math.min(r.nextInt(seed) / 10000, ships.length - 1);
        ShipType shipT = (ShipType) ships[shipIndex];
        Ship ship = new Ship(shipT);

        int weaponsNum = Math.min(r.nextInt(seed) / 20000, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.getWeaponList().add((WeaponType) weapons[i]);
        }

        int shieldsNum = Math.min(r.nextInt(seed) / 15000, shipT.getShieldSlots() - 1);
        for (int i = 0; i < shieldsNum; i++) {
            ship.getShieldList().add((ShieldType) shields[i]);
        }

        int gadgetsNum = Math.min(r.nextInt(seed) / 10000, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.getGadgetList().add((GadgetType) gadgets[i]);
        }

        // Traders also get trade goods in their inventory
        int cargoSize = shipT.getCargoSlots();

        Object[] goods = LastAdventures.data.get(GoodType.KEY).values().toArray();
        List<GoodType> legalGoods = new ArrayList<>(); // Traders only carry legal goods
        for (Object g: goods) {
            GoodType gt = (GoodType) g;
            if (gt.isLegal()) {
                legalGoods.add(gt);
            }
        }

        // TODO: Sort goods according to their value, then fill up cargo bays w/ loot based on seed
        legalGoods.sort(new Comparator<GoodType>(){
                public int compare(GoodType g1, GoodType g2) {
                    return g1.getValue() - g2.getValue();
                }
            });

        int goodsIndex = Math.min(r.nextInt(seed) / 20000, legalGoods.size() - 3);
        int maxValue = seed;

        for (int i = 0; i < 3; i++) {
            GoodType good = legalGoods.get(goodsIndex + i);
            ship.addCargo(good.getKey(), r.nextInt(maxValue / good.getValue()));
        }

        return Trader.createTrader(seed, ship);

    }

    private Police policeEncounter(int seed) {
        int shipIndex = Math.min(r.nextInt(seed) / 5000, ships.length - 1);
        ShipType shipT = (ShipType) ships[shipIndex];
        Ship ship = new Ship(shipT);

        int weaponsNum = Math.min(r.nextInt(seed) / 15000, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.getWeaponList().add((WeaponType) weapons[i]);
        }

        int shieldsNum = Math.min(r.nextInt(seed) / 10000, shipT.getShieldSlots() - 1);
        for (int i = 0; i < shieldsNum; i++) {
            ship.getShieldList().add((ShieldType) shields[i]);
        }

        int gadgetsNum = Math.min(r.nextInt(seed) / 20000, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.getGadgetList().add((GadgetType) gadgets[i]);
        }

        return Police.createPolice(seed, ship);

    }

    /**
     * Test function to test spawning encounters.
     * @param args commandline arguments.
     */
    public static void main(String[] args) {
        Encounter e = new Encounter();
        for (int i = 1; i < 20; i++) {
            e.getEncounter(100000 * i, null);
        }

    }
}
