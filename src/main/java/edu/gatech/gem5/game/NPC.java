package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EncounterController;
import edu.gatech.gem5.game.controllers.EncounterManager;


/**
 * An abstract superclass for all NPCs in the game
 * @author Sam Blumenthal
 */
public abstract class NPC extends Human implements Encounterable {

    EncounterManager manager;
    
    protected NPC(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }

    /**
     * @return the encounter message an NPC gives in an encounter with the Character.
     */
    @Override
    public String getEncounterMessage() {
        return "Whilst travelling, you have run into an encounter with:\n";
    }
    
    @Override
    public void processEncounter() {
        LastAdventures.swap(new EncounterController(this));
    }
    
    @Override
    public EncounterManager getManager() {
        return manager;
    }
    
    public void setManager(EncounterManager manager) {
        this.manager = manager;
    }
}
