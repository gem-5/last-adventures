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
class Planet {
    private int techLevel;
    private final int resource;
    Company[] companies;
    
    public Planet() {
        techLevel = new Random().nextInt(8);
        resource = new Random().nextInt(13);
        companies = null;
    }
    
    @Override
    public String toString() {
        String result = "";
        result += "Tech Level: " + techLevel;
        result += "\n";
        result += "Special Resource " + resource;
        return result;
    }
}
