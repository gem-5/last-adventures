package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ConditionType;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Jack Mueller
 */
public class Turn {

    private Universe universe;
    
    public Turn() {
        universe = LastAdventures.getCurrentSaveFile().getUniverse();
    }
    
    public void pass() {
        Random random = new Random();
        List<SolarSystem> systems = universe.getUniverse();
        for (SolarSystem system : systems) {
            for (Planet planet : system.getPlanets()) {
                //update planet's stock
                planet.increaseStock();
                //set the condition every turn
                planet.setCondition();
            }
        }
    }


    
}
