/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.gem5.game;

/**
 *
 * @author Jack
 */
public class SolarSystem {
    private final String name;
    private final int xCoordinate;
    private final int yCoordinate;
    
    public SolarSystem (String name, int x, int y) {
        this.name = name;
        this.xCoordinate = x;
        this.yCoordinate = y;
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
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * @return the yCoordinate of the system in its universe
     */
    public int getyCoordinate() {
        return yCoordinate;
    }
}
