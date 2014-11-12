package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 * A container class for data of the various environment types.
 *
 * @author Creston Bunch
 */

public class EnvironmentType extends AbstractDataType {

    /**
     * The name of the EnvironmentType.
     */
    private String name;
    /**
     * The supply of the EnvironmentType.
     */
    private Map<String, Double> supply;
    /**
     * The demand of the EnvironmentType.
     */
    private Map<String, Double> demand;
    /**
     * The occurrence of the EnvironmentType.
     */
    private double occurrence;
    /**
     * The wealth of the EnvironmentType.
     */
    private double wealth;

    /**
     * The EnviromentType's key, used for accessing EnvironmentTypes.
     */
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
