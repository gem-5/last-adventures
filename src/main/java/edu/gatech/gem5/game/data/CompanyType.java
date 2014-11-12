package edu.gatech.gem5.game.data;

import java.util.List;
import java.util.Map;

/**
 * A container class for data of the various company types.
 *
 * @author Creston Bunch
 */

public class CompanyType extends AbstractDataType {

    /**
     * The name of the CompanyType.
     */
    private String name;
    /**
     * The description of the CompanyType.
     */
    private String description;
    /**
     * The list of goods sold by the CompanyType.
     */
    private List<String> sells;
    /**
     * The list of ships sold by the CompanyType.
     */
    private List<String> ships;
    /**
     * The list of shields sold by the CompanyType.
     */
    private List<String> shields;
    /**
     * The list of weapons sold by the CompanyType.
     */
    private List<String> weapons;
    /**
     * The min tech level of the CompanyType.
     */
    private int minTech;
    /**
     * The max tech level of the CompanyType.
     */
    private int maxTech;
    /**
     * The occurrence of the CompanyType.
     */
    private double occurrence;
    /**
     * The wealth of the CompanyType.
     */
    private double wealth;
    /**
     * The environments associated with the CompanyType.
     */
    private Map<String, Double> environments;
    /**
     * The governments associated with the CompanyType.
     */
    private Map<String, Double> governments;

    /**
     * The CompanyType's key, used for accessing CompanyTypes.
     */
    public static final String KEY = "company";

    /**
     * Get the name of this company.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the description of this company.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get a list of goods that this company sells.
     *
     * @return the goods
     */
    public List<String> getProducts() {
        return this.sells;
    }

    /**
     * Get a list of ships that this company sells.
     *
     * @return the ships
     */
    public List<String> getShips() {
        return this.ships;
    }

    /**
     * Get a list of shields that this company sells.
     *
     * @return the shields
     */
    public List<String> getShields() {
        return this.shields;
    }

    /**
     * Get a list of weapons that this company sells.
     *
     * @return the weapons
     */
    public List<String> getWeapons() {
        return this.weapons;
    }

    /**
     * Get the minimun tech level required for this company.
     *
     * @return the tech level
     */
    public int getMinTech() {
        return this.minTech;
    }

    /**
     * Get the maximum tech level required for this company.
     *
     * @return the tech level
     */
    public int getMaxTech() {
        return this.maxTech;
    }

    /**
     * Get the base occurrence of this company.
     *
     * @return the occurrence
     */
    public double getOccurrence() {
        return this.occurrence;
    }

    /**
     * Get the wealth of this company.
     *
     * @return the wealth
     */
    public double getWealth() {
        return this.wealth;
    }

    /**
     * Get a map of environment multipliers for this company.
     *
     * @return the environments
     */
    public Map<String, Double> getEnvironments() {
        return this.environments;
    }

    /**
     * Get a map of government multipliers for this company.
     *
     * @return the governments
     */
    public Map<String, Double> getGovernments() {
        return this.governments;
    }


}
