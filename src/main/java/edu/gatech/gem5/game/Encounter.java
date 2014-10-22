package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;
import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.GovernmentType;
import edu.gatech.gem5.game.EncounterManager;
import edu.gatech.gem5.game.data.EventType;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Controller class to handle the logic behind processing Encounters
 *
 */
public class Encounter {

    private static final ShipType[] ships = Data.SHIPS.get().values().toArray(new ShipType[0]);
    private static final WeaponType[] weapons = Data.WEAPONS.get().values().toArray(new WeaponType[0]);
    private static final ShieldType[] shields = Data.SHIELDS.get().values().toArray(new ShieldType[0]);
    private static final GadgetType[] gadgets = Data.GADGETS.get().values().toArray(new GadgetType[0]);
    private static final GoodType[] goods = Data.GOODS.get().values().toArray(new GoodType[0]);

    private static final Random r = new Random();

    private EncounterManager trip;

    public Encounter(EncounterManager trip) {
        this.trip = trip;
    }

    /**
     * Randomly generates a pirate, police, or trader encounter
     * @param p The planet the player is currently traveling to.
     * @return any type of object encounterable on a trip
     *
     */
    public Encounterable getType(Planet p) {
        int seed = Math.max(LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth(), 1);
        return getType(seed, p);
    }

    private Encounterable getType(int seed, Planet p) {
        Encounterable spawn = null;
        GovernmentType gov = p.getGovernment();
        //@TODO come up with a better way to randomly select something
        int totalChance = 0;
        if(r.nextBoolean()) {
            int policeChance = (int) (gov.getPolice() * 10);
            totalChance += policeChance;
            int traderChance = (int) (gov.getTraders() * 10);
            totalChance += traderChance;
            int pirateChance = (int) (gov.getPirates() * 10);
            totalChance += pirateChance;

            int encounter = r.nextInt(totalChance) + 1;
            if (encounter <= policeChance) {
                spawn = Police.createPolice(seed);
            } else if (encounter <= traderChance) {
                spawn = Trader.createTrader(seed);
            } else if (encounter <= pirateChance){
                spawn = Pirate.createPirate(seed);
            }
            // spawn = Trader.createTrader(seed);
        } else {
            int gainMoneyChance = (int) (Data.EVENT.get().get("gainmoney").getOccurrence() * 10);
            totalChance += gainMoneyChance;
            int loseMoneyChance = (int) (Data.EVENT.get().get("losemoney").getOccurrence() * 10);
            totalChance += loseMoneyChance;
            int encounter = r.nextInt(totalChance) + 1;

            if (encounter <= gainMoneyChance) {
                spawn = new Event(eventEncounter("gainmoney"));
            } else {
                spawn = new Event(eventEncounter("losemoney"));
            }
        }
        if (spawn != null) {
            spawn.setManager(trip);
        }
        return spawn;

    }

    // private Pirate pirateEncounter(int seed) {
    //     int shipIndex = Math.min(r.nextInt(seed) / 7500, ships.length - 1);
    //     ShipType shipT = ships[shipIndex];
    //     Ship ship = new Ship(shipT);

    //     int weaponsNum = Math.min(r.nextInt(seed) / 10000, shipT.getWeaponSlots() - 1);
    //     for (int i = 0; i < weaponsNum; i++) {
    //         ship.getWeaponList().add(weapons[i]);
    //     }

    //     int shieldsNum = Math.min(r.nextInt(seed) / 15000, shipT.getShieldSlots() - 1);
    //     for (int i = 0; i < shieldsNum; i++) {
    //         ship.getShieldList().add(new Shield(shields[i]));
    //     }

    //     int gadgetsNum = Math.min(r.nextInt(seed) / 20000, shipT.getGadgetSlots() - 1);
    //     for (int i = 0; i < gadgetsNum; i++) {
    //         ship.getGadgetList().add(gadgets[i]);
    //     }

    //     return Pirate.createPirate(seed, ship);

    // }

    // private Trader traderEncounter(int seed) {
    //     int shipIndex = Math.min(r.nextInt(seed) / 10000, ships.length - 1);
    //     ShipType shipT = ships[shipIndex];
    //     Ship ship = new Ship(shipT);

    //     int weaponsNum = Math.min(r.nextInt(seed) / 20000, shipT.getWeaponSlots() - 1);
    //     for (int i = 0; i < weaponsNum; i++) {
    //         ship.getWeaponList().add(weapons[i]);
    //     }

    //     int shieldsNum = Math.min(r.nextInt(seed) / 15000, shipT.getShieldSlots() - 1);
    //     for (int i = 0; i < shieldsNum; i++) {
    //         ship.getShieldList().add(new Shield(shields[i]));
    //     }

    //     int gadgetsNum = Math.min(r.nextInt(seed) / 10000, shipT.getGadgetSlots() - 1);
    //     for (int i = 0; i < gadgetsNum; i++) {
    //         ship.getGadgetList().add(gadgets[i]);
    //     }

    //     // Traders also get trade goods in their inventory
    //     int cargoSize = shipT.getCargoSlots();

    //     List<GoodType> legalGoods = new ArrayList<>(); // Traders only carry legal goods
    //     for (GoodType g: goods) {
    //         if (g.isLegal()) {
    //             legalGoods.add(g);
    //         }
    //     }

    //     // TODO: Sort goods according to their value, then fill up cargo bays w/ loot based on seed
    //     legalGoods.sort(new Comparator<GoodType>(){
    //             public int compare(GoodType g1, GoodType g2) {
    //                 return g1.getValue() - g2.getValue();
    //             }
    //         });

    //     int goodsIndex = Math.min(r.nextInt(seed) / 20000, legalGoods.size() - 3);
    //     int maxValue = seed;

    //     for (int i = 0; i < 3; i++) {
    //         GoodType good = legalGoods.get(goodsIndex + i);
    //         ship.addCargo(good.getKey(), r.nextInt(maxValue / good.getValue()));
    //     }

    //     return Trader.createTrader(seed, ship);

    // }

    // private Police policeEncounter(int seed) {
    //     int shipIndex = Math.min(r.nextInt(seed) / 5000, ships.length - 1);
    //     ShipType shipT = ships[shipIndex];
    //     Ship ship = new Ship(shipT);

    //     int weaponsNum = Math.min(r.nextInt(seed) / 15000, shipT.getWeaponSlots() - 1);
    //     for (int i = 0; i < weaponsNum; i++) {
    //         ship.getWeaponList().add(weapons[i]);
    //     }

    //     int shieldsNum = Math.min(r.nextInt(seed) / 10000, shipT.getShieldSlots() - 1);
    //     for (int i = 0; i < shieldsNum; i++) {
    //         ship.getShieldList().add(new Shield(shields[i]));
    //     }

    //     int gadgetsNum = Math.min(r.nextInt(seed) / 20000, shipT.getGadgetSlots() - 1);
    //     for (int i = 0; i < gadgetsNum; i++) {
    //         ship.getGadgetList().add(gadgets[i]);
    //     }

    //     return Police.createPolice(seed, ship);
    // }

    private EventType eventEncounter(String eventKey) {
        return Data.EVENT.get().get(eventKey);
    }

    /**
     * Test function to test spawning encounters.
     * @param args commandline arguments.
     */
    public static void main(String[] args) {
        Encounter e = new Encounter(new EncounterManager());
        for (int i = 1; i < 20; i++) {
            e.getType(100000 * i, null);
        }

    }
}
