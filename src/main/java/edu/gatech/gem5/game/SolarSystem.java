package edu.gatech.gem5.game;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Jack
 */
public class SolarSystem {
    private final String name;
    private final int xCoordinate;
    private final int yCoordinate;
    private List<Planet> planets;

    // max(planets.size()) == PROBABILITIES.length
    // each entry n represents the probability of an nth planet existing
    private static final double[] PROBABILITIES = {1, 1, 0.66, 0.2};

    public SolarSystem (String name, int x, int y) {
        this.name = name;
        this.xCoordinate = x;
        this.yCoordinate = y;
        planets = determinePlanets();
    }

    /**
     * @return the name of the system
     */
    public String getName() {
        return name;
    }

    /**
     * @return the array of planets in this system
     */
    public List<Planet> getPlanets() {
        return planets;
    }
    /**
     * @return the xCoordinate of the system in its universe
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * @return the yCoordinate of the system in its universe
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    @Override
    public String toString() {
        String result = getName();
        result += "\nLocation: (" + getXCoordinate() + ", " + getYCoordinate() +")";
        for (Planet p : getPlanets()) {
            result += "\n\t" + p.toString().replace("\n", "\n\t");
        }
        return result;
    }

    private List<Planet> determinePlanets() {
        Random random = new Random();
        NameGenerator nameGen = new NameGenerator();
        List<Planet> orbits = new ArrayList<>();
        int num = 0;
        // roll through the list of probabilities
        for (int i = 0; i < PROBABILITIES.length; i++) {
            double p = PROBABILITIES[i];
            double roll = random.nextDouble();
            if (roll <= p)
                num++;
                // orbits.add(new Planet(this));
            else break; // stop generating planets as soon as a roll fails
        }
        // gives each planet a proper name based on the system name
        String[] names = nameGen.planetNames(num, this.name);
        for (int i = 0; i < num; i++) {
            orbits.add(new Planet(this, names[i]));
        }
        return orbits;
    }
}
