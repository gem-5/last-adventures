/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * Represents a planet in a system.
 *
 * @author Jack
 * @author Creston
 */
public class Planet {

    private int techLevel;
    private String environment;
    private String government;
    private List<String> companyList;
    private String condition;
    private SolarSystem solarSystem;
    private Map<String, Integer> currentStock;
    private final Map<String, Integer> maxStock;
    private String name;

    private static final double COMPETITION_FACTOR = 0.75;

    /**
     * Construct a planet with a random tech level, environment, government,
     * and list of companies based on the data files.
     *
     * @param sys The solar system that contains this planet.
     * @param name the name of the Planet
     */
    public Planet(SolarSystem sys, String name) {
        this.solarSystem = sys;
        this.techLevel = chooseTechLevel();
        this.environment = chooseEnvironment();
        this.government = chooseGovernment();
        this.companyList = chooseCompanies();
        this.maxStock = getMaxStock();
        this.currentStock = maxStock;
        this.condition = setCondition();
        this.name = name;

    }

    /**
     * Get the solar system that contains this planet.
     *
     * @return the solar system.
     */
    public SolarSystem getSolarySystem() {
        return this.solarSystem;
    }

   /**
     * Get the tech level.
     *
     * @return the tech level.
     */
    public TechType getTechLevel() {
        Map<Integer, TechType> techs = LastAdventures.data.get(TechType.KEY);
        return techs.get(this.techLevel);
    }


    /**
     * Get the environment type.
     *
     * @return the environment type
     */
    public EnvironmentType getEnvironment() {
        Map<String, EnvironmentType> environments =
        LastAdventures.data.get(EnvironmentType.KEY);
        return environments.get(this.environment);
    }

    /**
     * Get the government type.
     *
     * @return the government type
     */
    public GovernmentType getGovernment() {
        Map<String, GovernmentType> governments =
        LastAdventures.data.get(GovernmentType.KEY);
        return governments.get(this.government);
    }

    /**
     *
     * @param condition the key of the condition for this planet
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     *
     * @return the condition type of the planet
     */
    public ConditionType getCondition() {
        Map<String, ConditionType> conditions =
        LastAdventures.data.get(ConditionType.KEY);
        return conditions.get(this.condition);
    }

    /**
     * Get the list of companies.
     *
     * @return the company list.getCon
     */
    public List<CompanyType> getCompanies() {
        List<CompanyType> out = new ArrayList<>();
        for (String s : this.companyList) {
            Map<String, CompanyType> companies =
            LastAdventures.data.get(CompanyType.KEY);
            out.add(companies.get(s));
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

    /**
     * Get a map of goods and their quantities sold by this planet.
     *
     * @return the map
     */
    public Map<String, Integer> getStock() {
        return currentStock;
    }

    private Map<String, Integer> getMaxStock() {
        // only generate the stock once
        Map<String, Integer> out = new TreeMap<>();
        for (CompanyType c : getCompanies()) {
            for (String s : c.getProducts()) {
                if (!out.containsKey(s)) out.put(s, 0);
                GoodType g = (GoodType)
                             LastAdventures.data.get(GoodType.KEY).get(s);
                int amt =  g.getMaxStock();
                // adds more stock when several companies sell the same thing
                out.put(s, out.get(s) + amt);
            }
        }
        currentStock = out;
        return currentStock;
    }

    public void increaseStock() {
        for (Map.Entry<String, Integer> entry : currentStock.entrySet() ) {
            int maxOfGood = maxStock.get(entry.getKey());
            Random random = new Random();
            currentStock.put(entry.getKey(), Math.max(
                    entry.getValue() + random.nextInt(4), maxOfGood));

        }
    }

    /**
     * Get a map of goods and their prices sold by this planet.
     *
     * @return the map
     */
    public Map<String, Integer> getSupply() {
        Map<String, Integer> out = new TreeMap<>();
        for (CompanyType c : getCompanies()) {
            for (String s : c.getProducts()) {
                GoodType g = (GoodType)
                    LastAdventures.data.get(GoodType.KEY).get(s);
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
                // TODO: Apply Condition multipliers
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
     * Get a map of goods and their prices bought by this planet.
     *
     * @return the map
     */
    public Map<String, Integer> getDemand() {
        Map<String, Integer> in = new TreeMap<>();
        Ship playerShip = LastAdventures.getCurrentSaveFile().getCharacter().getShip();
        for (String g : playerShip.getCargoList().keySet()) {
            GoodType gt = (GoodType) LastAdventures.data.get(GoodType.KEY).get(g);
            double value = gt.getValue();
            String s = gt.getKey();

            Map<String, Double> govMap = getGovernment().getDemand();
            if (govMap.get(s) != null) {
                value *= govMap.get(s);
            }
            Map<String, Double> envMap = getEnvironment().getDemand();
            if (envMap.get(s) != null) {
                value *= envMap.get(s);
            }
            // If any company produces the good, it sells for less
            Map<String, Integer> f = getCompetitions();
            if (f.get(s) != null) {
                value *= COMPETITION_FACTOR;
            }

            in.put(s, (int) Math.round(value));
        }
        return in;
    }

    private Map<String, Integer> getCompetitions() {
        List<CompanyType> coms = getCompanies();
        Map<String, Integer> competitions = new HashMap<>();
        for (CompanyType c : coms) {
            for (String f : c.getProducts()) {
                // keep track of how many companies sell each good
                if (!competitions.containsKey(f)) competitions.put(f,0);
                competitions.put(f, competitions.get(f) + 1);
            }
        }
        return competitions;
    }

    private int chooseTechLevel() {
        Map<Integer, TechType> levels = LastAdventures.data.get(TechType.KEY);
        double roll = new Random().nextDouble();
        double sum = 0;
        for (Map.Entry<Integer, TechType> t : levels.entrySet()) {
            sum += t.getValue().getOccurrence();
            if (roll <= sum) return t.getKey();
        }
        // this should never happen unless max(sum) < 1.0
        return -1;
    }

    private String chooseEnvironment() {
        Map<String, EnvironmentType> list =
        LastAdventures.data.get(EnvironmentType.KEY);
        double roll = new Random().nextDouble();
        double sum = 0;
        for (Map.Entry<String, EnvironmentType> t : list.entrySet()) {
            sum += t.getValue().getOccurrence();
            if (roll <= sum) return t.getKey();
        }
        // this should never happen unless max(sum) < 1.0
        return null;
    }

    private String chooseGovernment() {
        // TODO:
        //.. this is a bit repetative, I can probably consilidate these into
        //a single private method if all these types have the same interface
        Map<String, GovernmentType> list =
        LastAdventures.data.get(GovernmentType.KEY);
        double roll = new Random().nextDouble();
        double sum = 0;
        for (Map.Entry<String, GovernmentType> t : list.entrySet()) {
            sum += t.getValue().getOccurrence();
            if (roll <= sum) return t.getKey();
        }
        // this should never happen unless max(sum) < 1.0
        return null;
    }

    private List<String> chooseCompanies() {
        Map<String, CompanyType> choices =
        LastAdventures.data.get(CompanyType.KEY);
        List<String> out = new ArrayList<>();
        for (Map.Entry<String, CompanyType> t : choices.entrySet()) {
            if (this.techLevel < t.getValue().getMinTech() ||
                this.techLevel > t.getValue().getMaxTech())
                continue; // not the right tech level, skip this company

            // multiply the occurrence factors to the base probability
            double p = t.getValue().getOccurrence();
            if (t.getValue().getEnvironments().containsKey(this.environment)) {
                p *= t.getValue().getEnvironments().get(this.environment);
            }
            if (t.getValue().getGovernments().containsKey(this.government)) {
                p *= t.getValue().getGovernments().get(this.government);
            }

            double roll = new Random().nextDouble();

            if (roll <= p) out.add(t.getKey());
        }
        return out;
    }

    public String getName() {
        return this.name;
    }


    @Override
    public String toString() {
        String result = "";
        result += "Name: " + this.name;
        result += "\n";
        result += "Tech Level: " + this.techLevel;
        result += "\n";
        result += "Environment: " + this.environment;
        result += "\n";
        result += "Government: " + this.government;
        result += "\n";
        result += "Compnies: " + this.companyList;
        return result;
    }

    public String setCondition() {
        //set new condition
        Random random = new Random();
        double conditionNumber = random.nextDouble();
        Map<String, ConditionType> conditions = LastAdventures.data.get(ConditionType.KEY);
        for( Map.Entry<String, ConditionType> entry : conditions.entrySet()) {
            conditionNumber -= entry.getValue().getOccurrence();
            if (conditionNumber <= 0) {
                return (String) entry.getKey();
            }
        }
        return null; // this should never happen unless
        // sum(conditions.valueSet().getOccurance()) > 1.0
    }
}
