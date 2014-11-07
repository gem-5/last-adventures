package edu.gatech.gem5.game;

import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class Turn {

    /**
     * A universe in which time, therefore turns, can pass.
     */
    private Universe universe;

    /**
     * Creates an instance of Turn with a universe which can be updated by a
     * turn passing.
     */
    public Turn(Universe uni) {
        universe = uni;
    }



    /**
     * Handles all changes that a universe undergoes when a turn passes.
     */
    public void pass() {
        Map<String, SolarSystem> systems = universe.getUniverse();
        for (SolarSystem system : systems.values()) {
            for (Planet planet : system.getPlanets()) {
                //update planet's stock
                planet.increaseStock();
                //set the condition every turn
                planet.getNewCondition();
            }
        }
    }



}
