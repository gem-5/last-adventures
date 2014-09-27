package edu.gatech.gem5.game.data;

import java.util.Map;
import java.util.List;

/**
 * A container class for data of the various gadget types.
 *
 * @author Creston Bunch
 */

public class GovernmentType extends DataType {

    private String name;
    private String description;
    private double police;
    private double traders;
    private double pirates;
    private double corruption;
    private int minTech;
    private int maxTech;
    private double wealth;
    private Map<String, Double> demand;
    private Map<String, Double> supply;
    private List<String> forbidden;
    private double occurrence;

    public static final String KEY = "government";

    /**
     * Get the name of the government type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the description of the government type.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the occurrence of police in this government type.
     *
     * @return the occurrence
     */
    public double getPolice() {
        return this.police;
    }

    /**
     * Get the occurrence of traders in this government type.
     *
     * @return the occurrence
     */
    public double getTraders() {
        return this.traders;
    }

    /**
     * Get the occurrence of pirates in this government type.
     *
     * @return the occurrence
     */
    public double getPirates() {
        return this.pirates;
    }

    /**
     * Get the corruption of this government type.
     *
     * @return the corruption
     */
    public double getCorruption() {
        return this.corruption;
    }

    /**
     * Get the minimum tech level required for this government type.
     *
     * @return the tech level
     */
    public int getMinTech() {
        return this.minTech;
    }

    /**
     * Get the maximum tech level required for this government type.
     *
     * @return the tech level
     */
    public int getMaxTech() {
        return this.maxTech;
    }

    /**
     * Get the wealth of this government type.
     *
     * @return the wealth
     */
    public double getWealth() {
        return this.wealth;
    }

    /**
     * Get a map of demanded items by this government type.
     *
     * @return the map
     */
    public Map<String, Double> getDemand() {
        return this.demand;
    }

    /**
     * Get a map of supplied items by this government type.
     *
     * @return the supply
     */
    public Map<String, Double> getSupply() {
        return this.supply;
    }

    /**
     * Get a list of forbidden items by this government type.
     *
     * @return the list
     */
    public List<String> getForbidden() {
        return this.forbidden;
    }

    /**
     * Get the occurrence of this government type.
     *
     * @return the occurrence
     */
    public double getOccurrence() {
        return this.occurrence;
    }
}
