package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShieldType;

/**
 * A class for shield objects for ships.
 *
 * @author Creston Bunch
 */

public class Shield {

    private final ShieldType type;

    /**
     * Construct the shield from a given shield type.
     *
     * @param type The ShieldType to copy.
     */
    public Shield(ShieldType type) {
        this.type = type;
    }

}
