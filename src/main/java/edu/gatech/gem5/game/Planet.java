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

import edu.gatech.gem5.game.data.TechType;
import edu.gatech.gem5.game.data.EnvironmentType;
import edu.gatech.gem5.game.data.GovernmentType;
import edu.gatech.gem5.game.data.CompanyType;
import edu.gatech.gem5.game.data.ConditionType;

/**
 * Represents a planet in a system.
 *
 * @author Jack
 * @author Creston
 */
class Planet {

    private int techLevel;
    private String environment;
    private String government;
    private List<String> companies;
    private String condition;

    /**
     * Construct a planet with a random tech level, environment, government,
     * and list of companies based on the data files.
     */
    public Planet() {
        this.techLevel = chooseTechLevel();
        this.environment = chooseEnvironment();
        this.government = chooseGovernment();
        this.companies = chooseCompanies();
        // TODO: a new condition should be applied every turn
        // some conditions should last longer than one turn.
        this.condition = null;
    }

    /**
     * Get the tech level.
     *
     * @return the tech level.
     */
    public TechType getTechLevel() {
        Map<Integer, TechType> techs = LastAdventures.manager.getInfo("techs");
        return techs.get(this.techLevel);
    }

    /**
     * Get the environment type.
     *
     * @return the environment type
     */
    public EnvironmentType getEnvironment() {
        Map<String, EnvironmentType> environments = LastAdventures.manager.getInfo("environments");
        return environments.get(this.environment);
    }

    /**
     * Get the government type.
     *
     * @return the government type
     */
    public GovernmentType getGovernment() {
        Map<String, GovernmentType> governments = LastAdventures.manager.getInfo("governments");
        return governments.get(this.government);
    }

    /**
     * Get the list of companies.
     *
     * @return the company list.
     */
    public List<CompanyType> getCompanies() {
        List<CompanyType> out = new ArrayList<>();
        for (String s : this.companies) {
            Map<String, CompanyType> companies = LastAdventures.manager.getInfo("companies");
            out.add(companies.get(s));
        }
        return out;
    }

    private int chooseTechLevel() {
        Map<Integer, TechType> levels = LastAdventures.manager.getInfo("techs");
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
        Map<String, EnvironmentType> list = LastAdventures.manager.getInfo("environments");
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
        Map<String, GovernmentType> list = LastAdventures.manager.getInfo("governments");
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
        Map<String, CompanyType> choices = LastAdventures.manager.getInfo("companies");
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

    @Override
    public String toString() {
        String result = "";
        result += "Tech Level: " + this.techLevel;
        result += "\n";
        result += "Environment: " + this.environment;
        result += "\n";
        result += "Government: " + this.government;
        result += "\n";
        result += "Compnies: " + this.companies;
        return result;
    }
}
