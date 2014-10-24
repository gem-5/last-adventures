package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GoodType;

/**
 * A class for good objects in a ships inventory / planet inventory.
 *
 * @author Creston Bunch
 */

public class Good {

    /**
     * The data on the nature of this good, including name, value, legality,
     * etc.
     */
    private final GoodType type;

    /**
     * Construct the good from a good type.
     *
     * @param goodType The GoodType to copy.
     */
    public Good(GoodType goodType) {
        this.type = goodType;
    }
    
    /**
     * 
     * @return returns the type of this good
     */
    public GoodType getType() {
        return type;
    }
}
