package edu.gatech.gem5.game.data;

import edu.gatech.gem5.game.Ship;
import java.util.Map;

/**
 * A container class for data of the various gadget types.
 *
 * @author Creston Bunch
 */

public class GadgetType {

    /**
     * The key of the GadgetType, used for recognition.
     */
    private String key;
    /**
     * The name of the GadgetType.
     */
    private String name;
    /**
     * The description of the GadgetType.
     */
    private String description;
    /**
     * The price of the GadgetType.
     */
    private int price;
    /**
     * The awarded bonus of the GadgetType.
     */
    private Map<String, Double> bonus;

    /**
     * The GadgetType key, used for accessing GadgetTypes.
     */
    public static final String KEY = "gadget";

    /**
     * Get the key of this gadget.
     * @return the key
     */
    public String getKey() {
        return this.key;
    }
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

    /**
     * Get the price of this gadget.
     * @return the price
     */
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
