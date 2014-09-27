package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 * A container class for data of the various environment types.
 *
 * @author Creston Bunch
 */

public class EnvironmentType extends DataType {
    
    private String name;
    private Map<String, Double> supply;
    private Map<String, Double> demand;
    private double occurrence;
    private double wealth;

    public static final String KEY = "environment";

    /**
     * Get the name of this environment type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the supply deficits for this environment type.
     *
     * @return the supply
     */
    public Map<String, Double> getSupply() {
        return this.supply;
    }

    /**
     * Get the demand bonuses for this environment type.
     *
     * @return the demand
     */
    public Map<String, Double> getDemand() {
        return this.demand;
    }

    /**
     * Get the occurrence of this environment type.
     *
     * @return the occurrence
     */
    public double getOccurrence() {
        return this.occurrence;
    }

    /**
     * Get the wealth of this environment type.
     *
     * @return the wealth
     */
    public double getWealth() {
        return this.wealth;
    }
}
