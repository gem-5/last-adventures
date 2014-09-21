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
 * @autor James
 */
public class Universe {
    private final ArrayList<SolarSystem> universe;
    //size on screen in pixels
    private final int width;
    private final int height;
    private final int numberOfPlanets;
    //temporary list of names for systems
    private static String[] names = {
    "Acamar",
    "Adahn",		// The alternate personality for The Nameless One in "Planescape: Torment"
    "Aldea",
    "Andevian",
    "Antedi",
    "Balosnee",
    "Baratas",
    "Brax",			// One of the heroes in Master of Magic
    "Bretel",		// This is a Dutch device for keeping your pants up.
    "Calondia",
    "Campor",
    "Capelle",		// The city I lived in while programming this game
    "Carzon",
    "Castor",		// A Greek demi-god
    "Cestus",
    "Cheron",		
    "Courteney",	// After Courteney Cox??
    "Daled",
    "Damast",
    "Davlos",
    "Deneb",
    "Deneva",
    "Devidia",
    "Draylon",
    "Drema",
    "Endor",
    "Esmee",		// One of the witches in Pratchett's Discworld
    "Exo",
    "Ferris",		// Iron
    "Festen",		// A great Scandinavian movie
    "Fourmi",		// An ant, in French
    "Frolix",		// A solar system in one of Philip K. Dick's novels
    "Gemulon",
    "Guinifer",		// One way of writing the name of king Arthur's wife
    "Hades",		// The underworld
    "Hamlet",		// From Shakespeare
    "Helena",		// Of Troy
    "Hulst",		// A Dutch plant
    "Iodine",		// An element
    "Iralius",
    "Janus",		// A seldom encountered Dutch boy's name
    "Japori",
    "Jarada",
    "Jason",		// A Greek hero
    "Kaylon",
    "Khefka",
    "Kira",			// My dog's name
    "Klaatu",		// From a classic SF movie
    "Klaestron",
    "Korma",		// An Indian sauce
    "Kravat",		// Interesting spelling of the French word for "tie"
    "Krios",
    "Laertes",		// A king in a Greek tragedy
    "Largo",
    "Lave",			// The starting system in Elite
    "Ligon",
    "Lowry",		// The name of the "hero" in Terry Gilliam's "Brazil"
    "Magrat",		// The second of the witches in Pratchett's Discworld
    "Malcoria",
    "Melina",
    "Mentar",		// The Psilon home system in Master of Orion
    "Merik",
    "Mintaka",
    "Montor",		// A city in Ultima III and Ultima VII part 2
    "Mordan",
    "Myrthe",		// The name of my daughter
    "Nelvana",
    "Nix",			// An interesting spelling of a word meaning "nothing" in Dutch
    "Nyle",			// An interesting spelling of the great river
    "Odet",
    "Og",			// The last of the witches in Pratchett's Discworld
    "Omega",		// The end of it all
    "Omphalos",		// Greek for navel
    "Orias",
    "Othello",		// From Shakespeare
    "Parade",		// This word means the same in Dutch and in English
    "Penthara",
    "Picard",		// The enigmatic captain from ST:TNG
    "Pollux",		// Brother of Castor
    "Quator",
    "Rakhar",
    "Ran",			// A film by Akira Kurosawa
    "Regulas",
    "Relva",
    "Rhymus",
    "Rochani",
    "Rubicum",		// The river Ceasar crossed to get into Rome
    "Rutia",
    "Sarpeidon",
    "Sefalla",
    "Seltrice",
    "Sigma",
    "Sol",			// That's our own solar system
    "Somari",
    "Stakoron",
    "Styris",
    "Talani",
    "Tamus",
    "Tantalos",		// A king from a Greek tragedy
    "Tanuga",
    "Tarchannen",
    "Terosa",
    "Thera",		// A seldom encountered Dutch girl's name
    "Titan",		// The largest moon of Jupiter
    "Torin",		// A hero from Master of Magic
    "Triacus",
    "Turkana",
    "Tyrus",
    "Umberlee",		// A god from AD&D, which has a prominent role in Baldur's Gate
    "Utopia",		// The ultimate goal
    "Vadera",
    "Vagra",
    "Vandor",
    "Ventax",
    "Xenon",
    "Xerxes",		// A Greek hero
    "Yew",			// A city which is in almost all of the Ultima games
    "Yojimbo",		// A film by Akira Kurosawa
    "Zalkon",
    "Zuul"
    };    

    public Universe(int num, int min, int max) {
        this.width = 100;
        this.height = 150;
        this.numberOfPlanets = num;
        //places systems appropriate distance from each other
        ArrayList<Point> layout = layoutUniverse(min, max);
        
        this.universe = new ArrayList<>();
        for (int i = 0; i < layout.size(); i++) {
            universe.add(new SolarSystem(names[i], layout.get(i).xCoordinate, 
                    layout.get(i).yCoordinate));
        }
    }
    
    private ArrayList<Point> layoutUniverse(int min, int max) {
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
        for (int n = 0; n < numberOfPlanets -1; n++) {
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
     * This method uses an algorithm to generate names for the Solar Systems
     * 
     * @return the list of names generate by the algorithm
     */
    public static String[] generateNames() {
        return names;
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
        Universe uni = new Universe(Universe.names.length, 4, 13);
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


