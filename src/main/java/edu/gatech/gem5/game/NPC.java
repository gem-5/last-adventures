package edu.gatech.gem5.game;



public abstract class NPC extends Human {

    protected NPC(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }

    // private static String[] titles;

    public abstract void processEncounter();


    public String getEncounterMessage() {
        return "Whilst travelling, you have run into an encounter with:\n";
    }
}
