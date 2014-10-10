package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 * A container class for data of the various tech level types.
 *
 * @author Creston Bunch
 */

public class TechType extends DataType {

    private String name;
    private int rank;
    private double occurrence;
    private double wealth;
    private Map<String, Double> governments;

    public static final String KEY = "tech";

    /**
     * Get the name of this tech level.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the rank of this tech level.
     *
     * @return the rank
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Get the occurrence of this tech level.
     *
     * @return the occurrence
     */
    public double getOccurrence() {
        return this.occurrence;
    }

    /**
     * Get the wealth of this tech level.
     *
     * @return the wealth
     */
    public double getWealth() {
        return this.wealth;
    }

    /**
     * Get map of governments and their probabilities of spawning.
     *
     * @return the map
     */
    public Map<String, Double> getGovernments() {
        return this.governments;
    }

}
