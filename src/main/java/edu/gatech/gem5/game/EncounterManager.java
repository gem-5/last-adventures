package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.PlanetController;
import edu.gatech.gem5.game.controllers.Controller;

/**
 *
 * @author Jack Mueller
 */
public class EncounterManager {

    /**
     * The max number of encounters an EncounterManager will give for a given
     * trip.
     */
    int encountersLeft = 5;

    /**
     * The planet being encountered.
     */
    Planet planet;

    /**
     * The player encountering things.
     */
    Character player;

    /**
     * Construct the encounter manager between a character and planet.
     *
     * @param c The player
     * @param p The planet.
     */
    public EncounterManager(Character c, Planet p) {
        this.planet = p;
        this.player = c;
    }

    /**
     * If there are encounters left for this trip, process the next one. If not,
     * the player will 'encounter' the planet.
     */
    public void nextEncounter() {
        if (encountersLeft > 0) {
            encountersLeft--;
            Encounterable encounter = new Encounter(this).getType(player.getNetWorth(), planet);
            if (encounter != null) {
                // encounter.processEncounter();
                Controller c = encounter.getEncounterController();
                LastAdventures.swap(c);
            }
        } else {
            LastAdventures.swap(new PlanetController());
        }
    }

}
