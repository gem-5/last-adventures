package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 * A container class for the data of planet condition types.
 *
 * @author Creston Bunch
 */

public class ConditionType extends DataType {

    /**
     * The name of the ConditionType.
     */
    private String name;
    /**
     * The demand of the ConditionType.
     */
    private Map<String, Double> demand;
    /**
     * The supply of the ConditionType.
     */
    private Map<String, Double> supply;
    /**
     * The occurrence of the ConditionType.
     */
    private double occurrence;

    /**
     * The ConditionType's key, used for accessing ConditionTypes.
     */
    public static final String KEY = "condition";

    /**
     * Get the name of this condition.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the map of demands of this condition.
     *
     * @return the demands
     */
    public Map<String, Double> getDemand() {
        return this.demand;
    }

    /**
     * Get the map of supply multipliers of this condition.
     *
     * @return the demands
     */
    public Map<String, Double> getSupply() {
        return this.supply;
    }

    /**
     * Get the occurrence of this condition.
     *
     * @return the occurrence
     */
    public double getOccurrence() {
        return this.occurrence;
    }

}
