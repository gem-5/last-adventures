package edu.gatech.gem5.game;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author Jack
 * @author James
 */
public class Universe {
    /**
     * A map of names to the instance of a Solar System of that name.
     */
    private final Map<String, SolarSystem> universe;

    /**
     * Number of columns where a solar system can be located in the universe.
     */
    private final int width;
    /**
     * Number of rows where a solar system can be located in the universe.
     */
    private final int height;
    /**
     * The capacity of this universe in terms of solar systems that it will
     * hold.
     */
    private final int numberOfPlanets;
    /**
     * The name generator a Universe uses for its names.
     */
    private final NameGenerator nameGen;

    /**
     * Creates a universe of default width, height, and number of planets.
     */
    public Universe() {
        this.width = 500;
        this.height = 500;
        this.numberOfPlanets = 250;
        this.nameGen = new NameGenerator();
        //places systems appropriate distance from each other
        List<Point> layout = layoutUniverse(numberOfPlanets);

        this.universe = new HashMap<>();
        for (Point p : layout) {
            String name = nameGen.newName();
            universe.put(name, new SolarSystem(
                    name,
                    p.xCoordinate,
                    p.yCoordinate
            )
            );
        }
    }

    /**
     * This method contains the logic to layout solar system in a logically 
     * manner in the universe.
     * 
     * @param num the number of planets to include in a universe
     * @return a list of Points that define where Solar Systems are in a given
     * universe.
     */
    private List<Point> layoutUniverse(int num) {
        Random rng = new Random();
        ArrayList<Point> locations = new ArrayList<>();

        int numArms = 4;
        int armCapacity = num / numArms;
        double rMax = Math.min(this.width, this.height) / 2;
        double dr = rMax / armCapacity;

        double b = 0.3;
        double rVariance = dr * 0.5;
        double tVariance = 0.5 / (2 * Math.PI);

        // let's go around in a circle and make spirals
        for (int j = 0; j < numArms; j++) {
            double r = rMax;
            double theta = 0;
            double tOffset = (2 * Math.PI / numArms) * j;
            for (int i = 0; i < armCapacity; i++) {
                double rv = 2 * rVariance * rng.nextDouble() - rVariance;
                double rt = 2 * tVariance * rng.nextGaussian() - tVariance;
                theta = (1 / b) * Math.log(r / rMax) + tOffset + rt;
                int x = (int) Math.round((r + rv) * Math.cos(theta)) + width / 2;
                int y = (int) Math.round((r + rv) * Math.sin(theta)) + height / 2;

                Point p = new Point(x, y);
                locations.add(p);
                r -= dr;
            }
        }
        return locations;
    }

    /**
     * Gets a list of all the solar systems in the universe.
     *
     * @return the list of systems in the universe
     */
    public Map<String, SolarSystem> getUniverse() {
        return universe;
    }

    /**
     * The width of the universe in arbitrary units.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * The height of the universe in arbitrary units.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get a single solar system at the given x and y coordinates.
     *
     * @param name name of the desired Solar System
     * @return a solar system with a matching name
     */
    public SolarSystem getSolarSystemByName(String name) {
        return universe.get(name);
    }

    /**
     * Get a single solar system at the given x and y coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return A solar system, if such a system exists in this universe
     */
    public Optional<SolarSystem> getSolarSystem(int x, int y) {
        // TODO: really this should be made to be O(1) with a map or something
        for (SolarSystem s : getUniverse().values()) {
            if (s.getXCoordinate() == x && s.getYCoordinate() == y) {

                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    /**
     * 
     * @return a 2 dimensional array containing solar systems where there should
     * be ones in a universe, and null where there shouldn't be.
     */
    private SolarSystem[][] getSolarSystemField() {
        SolarSystem[][] field = new SolarSystem[width][height];
        for (SolarSystem system : universe.values()) {
            int xCoordinate = system.getXCoordinate();
            int yCoordinate = system.getYCoordinate();
            if (xCoordinate >= 0 && yCoordinate >= 0
                    && xCoordinate < width && yCoordinate < height) {
                // @TODO the above check shouldn't be necessary, universe
                //generation is creating some invalid system locations
                field[xCoordinate][yCoordinate] = system;
            }
        }
        return field;
    }

    /**
     * Probes for a solar system near the given coordinates. Chooses a random
     * direction and makes a circle before expanding the search radius
     *
     * @param universe universe that you are searching in
     * @param x x coordinate of location this system should be near
     * @param y y coordinate of location this system should be near
     * @return one of the closest solar system to the given location
     */
    public static SolarSystem getSolarSystemNear(Universe universe, int x,
            int y) {
        SolarSystem[][] field = universe.getSolarSystemField();
        int radius = 1;
        double thetaStart = new Random().nextDouble() * 2 * Math.PI;
        SolarSystem close = null;

        while (close == null) {
            SolarSystem check;
            //goes from a random direction around in a circle, checks 180
            //directions, snaps to integer grid, so there are redundant checks
            //unless the radius expands too far
            for (double theta = thetaStart;
                    theta < thetaStart + 2 * Math.PI; theta += 2 * Math.PI / 180) {
                int xCheck = x + (int) (radius * Math.cos(theta));
                int yCheck = y + (int) (radius * Math.sin(theta));
                check = field[xCheck][yCheck];

                if (check != null) {
                    close = check;
                }
            }
            //this expands the search radius if nothing close enough was found
            radius++;
        }

        return close;
    }

    @Override
    public String toString() {
        String result = "";
        for (SolarSystem system : universe.values()) {
            result += system.toString() + "\n";
        }
        return result;
    }
    /**
     * This is a simple class to couple an x and y coordinate together.
     */
    private class Point {

        /**
         * A point's x coordinate.
         */
        private int xCoordinate;
        /**
         * A point's y coordinate.
         */
        private int yCoordinate;

        /**
         * 
         * @param x x coordinate
         * @param y y coordinate
         */
        public Point(int x, int y) {
            this.xCoordinate = x;
            this.yCoordinate = y;
        }

        /**
         * 
         * @return the x coordinate of a point
         */
        public int getX() {
            return this.xCoordinate;
        }

        /**
         * 
         * @return the y coordinate of a point
         */
        public int getY() {
            return this.yCoordinate;
        }

        /**
         * 
         * @param o the other point
         * @return Euclidean distance between two points
         */
        public double distance(Point o) {
            int dx = o.getX() - getX();
            int dy = o.getY() - getY();
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            else if (!(o instanceof Point) ) {
                return false;
            } else {
                return ((Point) o).getX() == getX() && ((Point) o).getY() == getY();
            }
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + this.xCoordinate;
            hash = 97 * hash + this.yCoordinate;
            return hash;
        }

        @Override
        public String toString() {
            return "(" + xCoordinate + ", " + yCoordinate + ")";
        }
    }
}
