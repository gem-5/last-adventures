package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 * A container class for data of the various tech level types.
 *
 * @author Creston Bunch
 */

public class TechType extends AbstractDataType {

    /**
     * The TechType's name, used for human identification.
     */
    private String name;
    /**
     * The TechType's rank, used for determining the tech level of a planet.
     */
    private int rank;
    /**
     * The TechType's occurrence, used for determining the occurrence of the tech level.
     */
    private double occurrence;
    /**
     * The TechType's wealth, used for determining the Planet wealth associated with the TechType.
     */
    private double wealth;
    /**
     * The TechType's String, used for determining which governments are associated with this TechType.
     */
    private Map<String, Double> governments;

    /**
     * The key used for accessing TechType information.
     */
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
