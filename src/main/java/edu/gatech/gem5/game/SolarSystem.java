/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.gem5.game;

import java.util.Random;

/**
 *
 * @author Jack
 */
public class SolarSystem {
    private final String name;
    private final int xCoordinate;
    private final int yCoordinate;
    private int techLevel;
    private final int resource;
    
    public SolarSystem (String name, int x, int y) {
        this.name = name;
        this.xCoordinate = x;
        this.yCoordinate = y;
        techLevel = new Random().nextInt(8);
        resource = new Random().nextInt(13);
        
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
        return getName() + " (" + getXCoordinate() + ", " + getYCoordinate() +")";
    }
}
