package edu.gatech.gem5.game;

import java.util.Random;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import edu.gatech.gem5.game.data.StarType;

/**
 *
 * @author Jack
 * @author Creston Bunch
 */
public class SolarSystem {
    /**
     * The name of a SolarSystem.
     */
    private final String name;
    /**
     * A Star key that corresponds to a star type defined in the JSON files.
     */
    private final String type;
    /**
     * The x coordinate of a system in its universe.
     */
    private final int xCoordinate;
    /**
     * The y coordinate of a system in its universe.
     */
    private final int yCoordinate;
    /**
     * A list of planets that exist in a solar system.
     */
    private List<Planet> planets;
    /**
     * A random number generator for this class.
     */
    private static final Random RANDOM = new Random();
    /**
     * Construct a solar system with a name, x, and y coordinate.
     *
     * Creates a random number of planets.
     *
     * @param n The name of the system.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public SolarSystem (String n, int x, int y) {
        this.name = n;
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.type = determineType();
        this.planets = determinePlanets();
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
     * Get the type of the system.
     *
     * @return the star type
     */
    public StarType getType() {
        return Data.STARS.get(type);
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
     * @return A human-readable string representation of the system including
     * the name, the location in the universe, and a description of each planet.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getName());
        result.append("\nLocation: (")
                .append(getXCoordinate())
                .append(", ")
                .append(getYCoordinate())
                .append(")");
        for (Planet p : getPlanets()) {
            String delim = "\n\t";
            result.append(delim).append(p.toString().replace("\n", delim));
        }
        return result.toString();
    }

    /**
     * Choose the type of this solar system.
     * @return the key for the type of star that was determined
     */
    private String determineType() {
        Map<String, StarType> stars = Data.STARS.get();

        double roll = RANDOM.nextDouble();
        double sum = 0;
        for (Map.Entry<String, StarType> t : stars.entrySet()) {
            sum += t.getValue().getOccurrence();
            if (roll <= sum) {
                return t.getKey();
            }
        }

        // this should never happen unless max(sum) < 1.0
        return null;
    }

    /**
     * Randomly creates a list of planets based on factors.
     *
     * @return The list of planets that the solar system was determined to have.
     * How many planets were determined is based on probabilities define in the
     * star type JSON file.
     */
    private List<Planet> determinePlanets() {
        NameGenerator nameGen = new NameGenerator(true);
        List<Planet> orbits = new ArrayList<>();
        int num = 0;
        double[] probs = getType().getPlanetProbabilities();
        // roll through the list of probabilities
        for (int i = 0; i < probs.length; i++) {
            double p = probs[i];
            double roll = RANDOM.nextDouble();
            if (roll <= p) {
                num++;
            } else {
                break; // stop generating planets as soon as a roll fails
            }
        }
        // gives each planet a proper name based on the system name
        String[] names = nameGen.planetNames(num, this.name);
        for (int i = 0; i < num; i++) {
            orbits.add(new Planet(names[i], i));
        }
        return orbits;
    }
}
