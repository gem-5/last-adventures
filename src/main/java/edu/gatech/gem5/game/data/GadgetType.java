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
    private Map<String, Double> bonus;

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
     * Apply the bonus to a ship or something. I dunno how this should work.
     *
     * @param Ship the ship to bonusify
     */
    public void applyBonus(Ship ship) {
        // not implemented
    }

    /**
     * Get the bonus.
     */
    public Map<String, Double> getBonus() {
        return this.bonus;
    }

    /**
     * A wrapper class for bonuses applied by a gadget type.
     *
     * @author Creston Bunch
     */
    public class Bonus {
        private Map<String, String> bonuses;

        public Bonus(Map<String, String> bonuses) {
            this.bonuses = bonuses;
        }
    }

}
