package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GadgetType;

/**
 * A class for gadget objects belonging to a ship.
 *
 * @author Creston Bunch
 */

public class Gadget {

    private final GadgetType type;

    /**
     * Construct the gadget from a gadget type.
     *
     * @param type The gadget type.
     */
    public Gadget(GadgetType type) {
        this.type = type;
    }

}
