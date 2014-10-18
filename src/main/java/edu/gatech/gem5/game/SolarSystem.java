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
    private final String name;
    private final String type;
    private final int xCoordinate;
    private final int yCoordinate;
    private List<Planet> planets;

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
        return (StarType) LastAdventures.data.get(StarType.KEY).get(type);
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
     * Choose the type of this solar system.
     */
    private String determineType() {
        Map<String, StarType> stars = (Map<String, StarType>)
            LastAdventures.data.get(StarType.KEY);

        double roll = new Random().nextDouble();
        double sum = 0;
        for (Map.Entry<String, StarType> t : stars.entrySet()) {
            sum += t.getValue().getOccurrence();
            if (roll <= sum) return t.getKey();
        }

        // this should never happen unless max(sum) < 1.0
        return null;
    }

    /**
     * Randomly creates a list of planets based on factors.
     */
    private List<Planet> determinePlanets() {
        Random random = new Random();
        NameGenerator nameGen = new NameGenerator(true);
        List<Planet> orbits = new ArrayList<>();
        int num = 0;
        double[] probs = getType().getPlanetProbabilities();
        // roll through the list of probabilities
        for (int i = 0; i < probs.length; i++) {
            double p = probs[i];
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
