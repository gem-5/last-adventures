/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.gem5.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Jack
 * @author James
 */
public class Universe {
    private final ArrayList<SolarSystem> universe;
    //size on screen in pixels
    private final int width;
    private final int height;
    private final int numberOfPlanets;
    private final NameGenerator nameGen;
    
    public Universe(int num, int min, int max) {
        this.width = 100;
        this.height = 150;
        this.numberOfPlanets = num;
        this.nameGen = new NameGenerator();
        //places systems appropriate distance from each other
        ArrayList<Point> layout = layoutUniverse(min, max, numberOfPlanets);

        this.universe = new ArrayList<>();
        for (int i = 0; i < layout.size(); i++) {
            universe.add(new SolarSystem(nameGen.newName(), layout.get(i).xCoordinate,
                    layout.get(i).yCoordinate));
        }
    }

    private ArrayList<Point> layoutUniverse(int min, int max, int num) {
        Random random = new Random();
        //the resulting list of locations
        ArrayList<Point> locations = new ArrayList<>();
        //these points are the right distance away from all planets in locations
        HashMap<String, Point> primed = new HashMap<>();
        //these points are either planets, or too close to planets to be
        //considered for new planets
        HashSet<String> locked = new HashSet<>();
        //pick an inital planet
        Point current = new Point(random.nextInt(width), random.nextInt(height));
        locations.add(current);
        //add the rest of the planets
        for (int n = 0; n < num -1; n++) {
            //lock the current planet and the points within min distance of it
            locked.add(current.toString());
            for(int i = current.xCoordinate - min; i <= current.xCoordinate + min; i++) {
                for (int j = current.yCoordinate - min; j <= current.yCoordinate + min; j++) {
                    locked.add(new Point(i,j).toString());
                    primed.remove(new Point(i,j).toString());
                }
            }
            //prime the points within max distance from the current planet
            for (int i = current.xCoordinate - max; i <= current.xCoordinate + max; i++) {
                for (int j = current.yCoordinate - max; j <= current.yCoordinate + max; j++) {
                    Point prime = new Point(i,j);
                    //don't prime if it's already locked
                    //don't prime if outside of length/height or outside of 0
                    if(!locked.contains(prime.toString()) &&
                            prime.xCoordinate >= 0 &&
                            prime.yCoordinate >= 0 &&
                            prime.xCoordinate < width &&
                            prime.yCoordinate < height) {
                        primed.put(prime.toString(),prime);
                    }
                }
            }
            //don't attempt to overcrowd the universe!
            if(primed.isEmpty()) {
                //universe has reached capacity with n solar systems placed
                break;
            }
            //pick a new planet to add to the final list, which unprimes it
            current = primed.remove((String) primed.keySet().toArray()[random.nextInt(primed.size())]);
            locations.add(current);
        }
        return locations;
    }

    /**
     * @return the list of systems in the universe
     */
    public ArrayList<SolarSystem> getUniverse() {
        return universe;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the width
     */
    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        String result = "";
        for (SolarSystem system : universe) {
           result += system.toString() + "\n";
        }
        return result;
    }

    public static void main(String[] args) {
        Universe uni = new Universe(120, 4, 13);
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

        @Override
        public String toString() {
            return "(" + xCoordinate + ", " + yCoordinate + ")";
        }
    }
}
