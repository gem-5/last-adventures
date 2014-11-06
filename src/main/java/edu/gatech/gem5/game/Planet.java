package edu.gatech.gem5.game;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.TechType;
import edu.gatech.gem5.game.data.EnvironmentType;
import edu.gatech.gem5.game.data.GovernmentType;
import edu.gatech.gem5.game.data.CompanyType;
import edu.gatech.gem5.game.data.ConditionType;
import java.util.TreeMap;
import java.util.Collection;

/**
 * Represents a planet in a system.
 *
 * @author Jack
 * @author Creston
 */
public class Planet implements Traderable {

    /**
     * The orbit of this planet around its star.
     */
    private int orbit;

    /**
     * Tech level of planet.
     */
    private int techLevel;
    /**
     * Key of planet environment.
     */
    private String environment;
    /**
     * Key of planet government.
     */
    private String government;
    /**
     * List of keys of a Planet's Companies.
     */
    private List<String> companyList;
    /**
     * Key of the current condition of a planet.
     */
    private String condition;
    /**
     * Keeps track of the amount of each type of good a planet has. Changes
     * differently over time based on what Companies there are on the planet.
     */
    private Map<String, Integer> currentStock;
    /**
     * The maximum amount of each type of good a planet can have at any time.
     */
    private final Map<String, Integer> maxStock;
    /**
     * The name of this planet in the form SolarSystem.name - Greek letter.
     */
    private String name;
    /**
     * Competition factor between companies selling the same good on a planet.
     */
    private static final double COMPETITION_FACTOR = 0.75;

    /**
     * A random number generator for this class.
     */
    private static final Random random = new Random();

    /**
     * Construct a planet with a random tech level, environment, government,
     * and list of companies based on the data files.
     *
     * @param n the name of the Planet
     * @param orb the orbit number around the star
     */
    public Planet(String n, int orb) {
        this.techLevel = chooseTechLevel();
        this.environment = chooseEnvironment();
        this.government = chooseGovernment();
        this.companyList = chooseCompanies();
        this.maxStock = getMaxStock();
        this.currentStock = maxStock;
        this.condition = getNewCondition();
        this.name = n;
        this.orbit = orb;
    }

   /**
     * Get the tech level.
     *
     * @return the tech level.
     */
    public TechType getTechLevel() {
        return Data.TECHS.get(this.techLevel);
    }

    /**
     * Get the environment type.
     *
     * @return the environment type
     */
    public EnvironmentType getEnvironment() {
        return Data.ENVIRONMENTS.get(this.environment);
    }

    /**
     * Get the government type.
     *
     * @return the government type
     */
    public GovernmentType getGovernment() {
        return Data.GOVERNMENTS.get(this.government);
    }

    /**
     *
     * @param c the key of the condition for this planet
     */
    public void setCondition(String c) {
        this.condition = c;
    }

    /**
     *
     * @return the condition type of the planet
     */
    public ConditionType getCondition() {
        return Data.CONDITIONS.get(this.condition);
    }

    /**
     * Get the list of companies.
     *
     * @return the company list.getCon
     */
    public List<CompanyType> getCompanies() {
        List<CompanyType> out = new ArrayList<>();
        for (String s : this.companyList) {
            out.add(Data.COMPANIES.get(s));
        }
        return out;
    }

    /**
     * Get a list of shields that this planet sells.
     *
     * @return the shields
     */
    public List<String> getShields() {
        List<String> out = new ArrayList<>();
        for (CompanyType c : getCompanies()) {
            out.addAll(c.getShields());
        }
        return out;
    }

    /**
     * Get a list of weapons that this planet sells.
     *
     * @return the weapons
     */
    public List<String> getWeapons() {
        List<String> out = new ArrayList<>();
        for (CompanyType c : getCompanies()) {
            out.addAll(c.getWeapons());
        }
        return out;
    }

    /**
     * Get a list of gadgets that this planet sells.
     *
     * @return the ships
     */
    public List<String> getGadgets() {
        //TODO: gadgets are not sold by any company
        return null;
    }

    /**
     * Get a list of ships that this planet sells.
     *
     * @return the ships
     */
    public List<String> getShips() {
        List<String> out = new ArrayList<>();
        for (CompanyType c : getCompanies()) {
            out.addAll(c.getShips());
        }
        return out;
    }

    @Override
    public Map<String, Integer> getStock() {
        return currentStock;
    }

    /**
     *
     * @return the max stock that a planet can have at any time based on the
     * companies that are on it. If a planet does not know this about itself,
     * it is calculated here.
     */
    private Map<String, Integer> getMaxStock() {
        // only generate the stock once
        Map<String, Integer> out = new TreeMap<>();
        for (CompanyType c : getCompanies()) {
            for (String s : c.getProducts()) {
                if (!out.containsKey(s)) {
                    out.put(s, 0);
                }
                GoodType g =  Data.GOODS.get(s);
                int amt =  g.getMaxStock();
                // adds more stock when several companies sell the same thing
                out.put(s, out.get(s) + amt);
            }
        }
        currentStock = out;
        return currentStock;
    }

    /**
     * Replenishes the stock of a planet the amount that one turn of the game
     * time should replenish.
     */
    public void increaseStock() {
        for (Map.Entry<String, Integer> entry : currentStock.entrySet() ) {
            int maxOfGood = maxStock.get(entry.getKey());

            currentStock.put(entry.getKey(), Math.max(
                    entry.getValue() + random.nextInt(4), maxOfGood));
        }
    }

    @Override
    public Map<String, Integer> getSupply() {
        Map<String, Integer> out = new TreeMap<>();
        for (CompanyType c : getCompanies()) {
            for (String s : c.getProducts()) {
                GoodType g = Data.GOODS.get(s);
                double value = g.getValue();
                // Apply government multipliers
                Map<String, Double> govMap = getGovernment().getSupply();
                if (govMap.get(s) != null) {
                    value *= govMap.get(s);
                }
                // Apply environment multipliers
                Map<String, Double> envMap = getEnvironment().getSupply();
                if (envMap.get(s) != null) {
                    value *= envMap.get(s);
                }
                // Apply condition multipliers
                Map<String, Double> conMap = getCondition().getSupply();
                if (conMap.get(s) != null) {
                    value *= conMap.get(s);
                }
                // Apply competition factor
                Map<String, Integer> f = getCompetitions();
                if (f.get(s) != null && f.get(s) > 1) {
                    value *= COMPETITION_FACTOR;
                }
                out.put(s, (int) Math.round(value));
            }
        }
        return out;
    }

    /**
     * Get the prices (demand) for buying goods from this planet.
     *
     * @param goods The collection of goods to ask about.
     * @return A map of goods/prices to buy.
     */
    @Override
    public Map<String, Integer> getDemand(Collection<String> goods) {
        Map<String, Integer> in = new TreeMap<>();
        for (String g : goods) {
            GoodType gt = Data.GOODS.get(g);
            double value = gt.getValue();
            String s = gt.getKey();
            // Apply government multipliers
            Map<String, Double> govMap = getGovernment().getDemand();
            if (govMap.get(s) != null) {
                value *= govMap.get(s);
            }
            // Apply environment multipliers
            Map<String, Double> envMap = getEnvironment().getDemand();
            if (envMap.get(s) != null) {
                value *= envMap.get(s);
            }
            Map<String, Double> conMap = getCondition().getDemand();
            if (conMap.get(s) != null) {
                value *= conMap.get(s);
            }
            // If more than one company produces the good, it sells for less
            Map<String, Integer> f = getCompetitions();
            if (f.get(s) != null && f.get(s) > 1) {
                value *= COMPETITION_FACTOR;
            }

            in.put(s, (int) Math.round(value));
        }
        return in;
    }

    /**
     *
     * @return A map of goods sold on the planet to the amount of companies
     * offering the good.
     */
    private Map<String, Integer> getCompetitions() {
        List<CompanyType> coms = getCompanies();
        Map<String, Integer> competitions = new HashMap<>();
        for (CompanyType c : coms) {
            for (String f : c.getProducts()) {
                // keep track of how many companies sell each good
                if (!competitions.containsKey(f)) {
                    competitions.put(f, 0);
                }
                competitions.put(f, competitions.get(f) + 1);
            }
        }
        return competitions;
    }

    /**
     *
     * @return a tech level for the planet based on its government's weights
     * in the JSON data files.
     */
    private int chooseTechLevel() {
        Map<Integer, TechType> levels = Data.TECHS.get();
        double roll = random.nextDouble();
        double sum = 0;
        for (Map.Entry<Integer, TechType> t : levels.entrySet()) {
            sum += t.getValue().getOccurrence();
            if (roll <= sum) {
                return t.getKey();
            }
        }
        // this should never happen unless max(sum) < 1.0
        return -1;
    }

    /**
     *
     * @return An environment based on weights in the JSON data files.
     */
    private String chooseEnvironment() {
        Map<String, EnvironmentType> list = Data.ENVIRONMENTS.get();
        double roll = random.nextDouble();
        double sum = 0;
        for (Map.Entry<String, EnvironmentType> t : list.entrySet()) {
            sum += t.getValue().getOccurrence();
            if (roll <= sum) {
                return t.getKey();
            }
        }
        // this should never happen unless max(sum) < 1.0
        return null;
    }

    /**
     *
     * @return A government key based on weights in the JSON data files.
     */
    private String chooseGovernment() {
        Map<String, Double> govs = getTechLevel().getGovernments();
        double roll = random.nextDouble();
        double sum = 0;
        for (Map.Entry<String, Double> t : govs.entrySet()) {
            sum += t.getValue();
            if (roll <= sum) {
                return t.getKey();
            }
        }
        // this should never happen unless max(sum) < 1.0
        return null;
    }

    /**
     *
     * @return A list of Company keys based on weights in the JSON data files.
     */
    private List<String> chooseCompanies() {
        Map<String, CompanyType> choices = Data.COMPANIES.get();
        List<String> out = new ArrayList<>();
        for (Map.Entry<String, CompanyType> t : choices.entrySet()) {
            if (this.techLevel < t.getValue().getMinTech()
                    || this.techLevel > t.getValue().getMaxTech()) {
                continue; // not the right tech level, skip this company
            }
            // multiply the occurrence factors to the base probability
            double p = t.getValue().getOccurrence();
            if (t.getValue().getEnvironments().containsKey(this.environment)) {
                p *= t.getValue().getEnvironments().get(this.environment);
            }
            if (t.getValue().getGovernments().containsKey(this.government)) {
                p *= t.getValue().getGovernments().get(this.government);
            }

            double roll = random.nextDouble();

            if (roll <= p) {
                out.add(t.getKey());
            }
        }
        return out;
    }

    /**
     *
     * @return a planet's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return a planet's orbit.
     */
    public int getOrbit() {
        return this.orbit;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Name: " + this.name;
        result += "\nTech Level: " + this.techLevel;
        result += "\nEnvironment: " + this.environment;
        result += "\nGovernment: " + this.government;
        result += "\nCompnies: " + this.companyList;
        return result;
    }

    /**
     *
     * @return a key of a condition for a planet based on weights in the JSON
     * files.
     */
    public String getNewCondition() {
        //set new condition
        double conditionNumber = random.nextDouble();
        Map<String, ConditionType> conditions = Data.CONDITIONS.get();
        for (Map.Entry<String, ConditionType> entry : conditions.entrySet()) {
            conditionNumber -= entry.getValue().getOccurrence();
            if (conditionNumber <= 0) {
                return (String) entry.getKey();
            }
        }
        return null; // this should never happen unless
        // sum(conditions.valueSet().getOccurance()) > 1.0
    }
}
