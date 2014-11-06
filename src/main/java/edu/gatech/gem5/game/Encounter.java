package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GovernmentType;
import edu.gatech.gem5.game.data.EventType;
import java.util.Random;

/**
 * Controller class to handle the logic behind processing Encounters.
 *
 */
public class Encounter {

    /**
     * The class' random number generator.
     */
    private static final Random RANDOM_NUMBERS = new Random();

    /**
     * The EncounterManager that is managing this encounter.
     */
    private EncounterManager trip;

    /**
     *
     * @param manager This encounter's manager.
     */
    public Encounter(EncounterManager manager) {
        this.trip = manager;
    }

    /**
     * Determines and returns the type of this encounter.
     * @TODO this method should probably not generate another encounterable
     * if it is called from the same Encounter instance twice
     *
     * @param seed the random seed for determining the type of encounter
     * @param p the planet with data on its distribution of encounterable NPC's
     * @return the type of encounter this is
     */
    public Encounterable getType(int seed, Planet p) {
        Encounterable spawn = null;
        GovernmentType gov = p.getGovernment();
        //@TODO come up with a better way to randomly select something
        int totalChance = 0;
        if (RANDOM_NUMBERS.nextBoolean()) {
            int policeChance = (int) (gov.getPolice() * 10);
            totalChance += policeChance;
            int traderChance = (int) (gov.getTraders() * 10);
            totalChance += traderChance;
            int pirateChance = (int) (gov.getPirates() * 10);
            totalChance += pirateChance;

            int encounter = RANDOM_NUMBERS.nextInt(totalChance) + 1;
            if (encounter <= policeChance) {
                spawn = Police.createPolice(seed);
            } else if (encounter <= traderChance) {
                spawn = Trader.createTrader(seed);
            } else if (encounter <= pirateChance) {
                spawn = Pirate.createPirate(seed);
            }
            // spawn = Trader.createTrader(seed);
        } else {
            String gainmoney = "gainmoney";
            String losemoney = "losemoney";
            int gainMoneyChance = (int) (Data.EVENT.get().get(gainmoney).getOccurrence() * 10);
            totalChance += gainMoneyChance;
            int loseMoneyChance = (int) (Data.EVENT.get().get(losemoney).getOccurrence() * 10);
            totalChance += loseMoneyChance;
            int encounter = RANDOM_NUMBERS.nextInt(totalChance) + 1;

            if (encounter <= gainMoneyChance) {
                spawn = new Event(eventEncounter(gainmoney));
            } else {
                spawn = new Event(eventEncounter(losemoney));
            }
        }
        if (spawn != null) {
            spawn.setManager(trip);
        }
        return spawn;

    }

    /**
     *
     * @param eventKey the key of any possible Event
     * @return the corresponding EventType
     */
    private EventType eventEncounter(String eventKey) {
        return Data.EVENT.get().get(eventKey);
    }
}
