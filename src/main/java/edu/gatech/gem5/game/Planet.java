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
    private static final String[] techLevels = {"Pre-Agriculture", "Agriculture",
                                                "Medieval", "Renaissance", "Early Industrial",
                                                "Industrial", "Post-Industrial", "Hi-Tech"};

    // temporary, we'll probably want different ones based on our goods.
    // Also, if we follow the original, then it should be weighted a bit towards NOSPECIALRESOURCES
    private static final String[] resourceLevels = {"NOSPECIALRESOURCES", "MINERALRICH",
                                                    "MINERALPOOR", "DESERT", "LOTSOFWATER",
                                                    "RICHSOIL", "POORSOIL", "RICHFAUNA",
                                                    "LIFELESS", "WEIRDMUSHROOMS", "LOTSOFHERBS",
                                                    "ARTISTIC", "WARLIKE"};

    public Planet() {
        techLevel = new Random().nextInt(8);
        resource = new Random().nextInt(13);
        companies = null;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Tech Level: " + techLevels[techLevel] + " {" + techLevel + "}";
        result += "\n";
        result += "Special Resource: " + resourceLevels[resource] + " {" + resource + "}";
        return result;
    }
}
