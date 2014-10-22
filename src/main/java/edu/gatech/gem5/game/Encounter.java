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
