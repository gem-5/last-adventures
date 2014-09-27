package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GoodType;

/**
 * A class for good objects in a ships inventory / planet inventory.
 *
 * @author Creston Bunch
 */

public class Good {

    private final GoodType type;

    /**
     * Construct the good from a good type.
     *
     * @param type The GoodType to copy.
     */
    public Good(GoodType type) {
        this.type = type;
    }
    
    public GoodType getType() {
        return type;
    }
}
