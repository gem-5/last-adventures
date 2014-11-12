package edu.gatech.gem5.game.data;

import java.util.Map;
import java.util.List;

/**
 * A container class for data of the various gadget types.
 *
 * @author Creston Bunch
 */

public class GovernmentType extends AbstractDataType {

    /**
     * The name of the GovernmentType.
     */
    private String name;
    /**
     * The description of the GovernmentType.
     */
    private String description;
    /**
     * The police rate of the GovernmentType.
     */
    private double police;
    /**
     * The trader rate of the GovernmentType.
     */
    private double traders;
    /**
     * The pirate rate of the GovernmentType.
     */
    private double pirates;
    /**
     * The corruption rate of the GovernmentType.
     */
    private double corruption;
    /**
     * The min tech level of the GovernmentType.
     */
    private int minTech;
    /**
     * The max tech level of the GovernmentType.
     */
    private int maxTech;
    /**
     * The wealth of the GovernmentType.
     */
    private double wealth;
    /**
     * The demand map of the GovernmentType.
     */
    private Map<String, Double> demand;
    /**
     * The supply map of the GovernmentType.
     */
    private Map<String, Double> supply;
    /**
     * The forbidden goods list of the GovernmentType.
     */
    private List<String> forbidden;
    /**
     * The occurence of the GovernmentType.
     */
    private double occurrence;

    /**
     * The Key of the GovernmentType, used for accessing GovernmentTypes.
     */
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
