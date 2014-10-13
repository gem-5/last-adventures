package edu.gatech.gem5.game;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Jack
 * @author James
 */
public class Universe {
    private final Map<String, SolarSystem> universe;

    private final int width;
    private final int height;
    private final int numberOfPlanets;
    private final NameGenerator nameGen;

    public Universe(int num) {
        this.width = 100;
        this.height = 150;
        this.numberOfPlanets = num;
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

    private List<Point> layoutUniverse(int num) {
        Random rng = new Random();
        ArrayList<Point> locations = new ArrayList<>();

        int numArms = 4;
        int armCapacity = num / numArms;
        double rVariance = 2;
        double tVariance = 0.2;
        double rMax = Math.min(this.width, this.height) / 2;
        double dr = (rMax / armCapacity) * 0.95;
        double b = 0.5;
        double distanceThreshold = 1;
        double highThreshhold = 5;

        // let's go around in a circle and make spirals
        for (int j = 0; j < numArms; j++) {
            double r = rMax;
            double theta = 0;
            double tOffset = (2 * Math.PI / numArms) * j;
            for (int i = 0; i < armCapacity; i++) {
                double rv = 2 * rVariance * rng.nextDouble() - rVariance;
                double rt = 2 * tVariance * rng.nextDouble() - tVariance;
                theta = (1 / b) * Math.log(r / rMax) + tOffset + rt;
                int x = (int) Math.round((r+rv) * Math.cos(theta)) + width / 2;
                int y = (int) Math.round((r+rv) * Math.sin(theta)) + height / 2;

                Point p = new Point(x, y);
                // ensure the point is valid
                boolean valid = false;
                for (Point q : locations) {
                    //close enough to another system?
                    if (!valid && q.distance(p) <= highThreshhold) {
                        valid = true;
                    }
                    //to close to another system
                    if (q.distance(p) <= distanceThreshold || p.equals(q)) {
                        valid = false;
                        break;
                    }
                    
                }
                if (valid || i== 0 ) {//always add first in arm
                    locations.add(p);
                    r -= dr;
                }
                else i--; // try again
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

    @Override
    public String toString() {
        String result = "";
        for (SolarSystem system : universe.values()) {
           result += system.toString() + "\n";
        }
        return result;
    }

    public static void main(String[] args) {
        Universe uni = new Universe(120);
        System.out.println(uni);
    }

    /*
    * This is a simple class to couple an x and y coordinate together
    */
    private class Point {
        //location
        private int xCoordinate;
        private int yCoordinate;

        public Point (int x, int y) {
            this.xCoordinate = x;
            this.yCoordinate = y;
        }

        public int getX() {
            return this.xCoordinate;
        }

        public int getY() {
            return this.yCoordinate;
        }

        public double distance(Point o) {
            int dx = o.getX() - getX();
            int dy = o.getY() - getY();
            return Math.sqrt(dx*dx + dy*dy);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof Point == false) return false;
            return ((Point) o).getX() == getX() && ((Point) o).getY() == getY();
        }

        @Override
        public String toString() {
            return "(" + xCoordinate + ", " + yCoordinate + ")";
        }
    }
}
