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

    // each entry n represents the probability of an nth planet existing
    private static final double[] PROBABILITIES = {1, 1, 0.66, 0.2};

    /**
     * Construct a solar system with a name, x, and y coordinate.
     *
     * Creates a random number of planets.
     *
     * @param name The name of the system.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public SolarSystem (String name, int x, int y) {
        this.name = name;
        this.xCoordinate = x;
        this.yCoordinate = y;
        planets = determinePlanets();
    }

    /**
     * Get the name of the system.
     *
     * @return the name of the system
     */
    public String getName() {
        return name;
    }

    /**
     * Get a list of all the planets in a system.
     *
     * @return the array of planets in this system
     */
    public List<Planet> getPlanets() {
        return planets;
    }
    /**
     * Get the x coordinate of this planet.
     *
     * @return the xCoordinate of the system in its universe
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Get the y coordinate of this planet.
     *
     * @return the yCoordinate of the system in its universe
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Return a planet at the given index.
     *
     * @param index The index of the planet in this system.
     * @return the desired planet
     */
    public Planet getPlanetAt(int index) {
        return getPlanets().get(index);
    }

    /**
     * Return this system as a string for debugging, etc.
     *
     * @return A human-readable string representation.
     */
    @Override
    public String toString() {
        String result = getName();
        result += "\nLocation: (" + getXCoordinate() + ", " + getYCoordinate() +")";
        for (Planet p : getPlanets()) {
            result += "\n\t" + p.toString().replace("\n", "\n\t");
        }
        return result;
    }

    /**
     * Randomly creates a list of planets based on factors.
     */
    private List<Planet> determinePlanets() {
        Random random = new Random();
        NameGenerator nameGen = new NameGenerator(true);
        List<Planet> orbits = new ArrayList<>();
        int num = 0;
        // roll through the list of probabilities
        for (int i = 0; i < PROBABILITIES.length; i++) {
            double p = PROBABILITIES[i];
            double roll = random.nextDouble();
            if (roll <= p) num++;
            else break; // stop generating planets as soon as a roll fails
        }
        // gives each planet a proper name based on the system name
        String[] names = nameGen.planetNames(num, this.name);
        for (int i = 0; i < num; i++) {
            orbits.add(new Planet(names[i]));
        }
        return orbits;
    }
}
