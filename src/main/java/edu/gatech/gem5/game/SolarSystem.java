/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.gem5.game;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Jack
 */
public class SolarSystem {
    private final String name;
    private final int xCoordinate;
    private final int yCoordinate;
    private Planet[] planets;
    
    private final double THIRD_PLANET_PERCENT = 66;
    private final double FOURTH_PLANET_PERCENT = 20;
    private final int PLANET_MAX = 4;
    
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
        for (int i = 0; i < PLANET_MAX; i++) {
            if (planets[i] != null) {
                result +=
                "\n\tOrbit " + (i+1) +
                ":\n\t\t" +
                planets[i].toString().replace("\n", "\n\t\t");
            }
        }
        return result;
    }

    private Planet[] determinePlanets() {
        Random random = new Random();
        Planet[] orbits = new Planet[PLANET_MAX];
        int num = 0;
        //ensure at least 2 planets
        while (num < 2) {
            int choice = random.nextInt(PLANET_MAX);
            if (orbits[choice] == null) {
                orbits[choice] = new Planet();
                num++;
            }
        }
        if (random.nextDouble() > THIRD_PLANET_PERCENT/100) {
            while (num < 3) {
            int choice = random.nextInt(PLANET_MAX);
                if (orbits[choice] == null) {
                    orbits[choice] = new Planet();
                    num++;
                }
            }
        }
        if (random.nextDouble() > FOURTH_PLANET_PERCENT/100) {
            while (num < 4) {
            int choice = random.nextInt(PLANET_MAX);
                if (orbits[choice] == null) {
                    orbits[choice] = new Planet();
                    num++;
                }
            }
        }
        return orbits;
    }
}
