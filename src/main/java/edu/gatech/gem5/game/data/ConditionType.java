package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 * A container class for the data of planet condition types.
 *
 * @author Creston Bunch
 */

public class ConditionType extends DataType {

    private String name;
    private Map<String, Double> demand;
    private double occurrence;

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
     * Get the occurrence of this condition.
     *
     * @return the occurrence
     */
    public double getOccurrence() {
        return this.occurrence;
    }

}
