package edu.gatech.gem5.game.data;

import edu.gatech.gem5.game.Ship;
import java.util.Map;

/**
 * A container class for data of the various gadget types.
 *
 * @author Creston Bunch
 */

public class GadgetType {

    private String name;
    private String description;
    private int price;
    private Map<String, Double> bonus;

    public static final String KEY = "gadget";

    /**
     * Get the name of this gadget.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the description of this gadget.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    public int getPrice() {
        return this.price;
    }

    /**
     * Apply the bonus to a ship or something. I dunno how this should work.
     *
     * @param ship the ship to bonusify
     */
    public void applyBonus(Ship ship) {
        // not implemented
    }

    /**
     * Get the bonus.
     *
     * @return the map of bonuses
     */
    public Map<String, Double> getBonus() {
        return this.bonus;
    }

}
