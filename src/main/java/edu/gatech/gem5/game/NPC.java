package edu.gatech.gem5.game;


/**
 * An abstract superclass for all NPCs in the game
 * @author Sam Blumenthal
 */
public abstract class NPC extends Human {

    protected NPC(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }

    // private static String[] titles;

    /**
     * Handles the logic associated with an encounter between the Characer and an NPC.
     */
    public abstract void processEncounter();


    /**
     * @return the encounter message an NPC gives in an encounter with the Character.
     */
    public String getEncounterMessage() {
        return "Whilst travelling, you have run into an encounter with:\n";
    }
}
