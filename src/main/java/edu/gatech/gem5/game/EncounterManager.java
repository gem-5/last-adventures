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
     * If there are encounters left for this trip, process the next one. If not,
     * the player will 'encounter' the planet.
     */
    public void nextEncounter() {
        if (encountersLeft > 0) {
            encountersLeft--;
            Encounterable encounter = new Encounter(this).getType(LastAdventures.getCurrentSaveFile().getPlanet());
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
