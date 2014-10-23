package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.PlanetController;

/**
 *
 * @author Jack Mueller
 */
public class EncounterManager {


    int encountersLeft = 5;

    public void nextEncounter() {
        if(encountersLeft > 0) {
            encountersLeft--;
            Encounterable encounter = new Encounter(this).getType(LastAdventures.getCurrentSaveFile().getPlanet());
            if (encounter != null) {
                encounter.processEncounter();
            }
        } else {
            LastAdventures.swap(new PlanetController());
        }
    }

}
