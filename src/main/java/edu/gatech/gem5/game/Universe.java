/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.gem5.game;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jack
 */
public class Universe {
    private final ArrayList<SolarSystem> universe;
    //size on screen in pixels
    private int length;
    private int width;
    private int numberOfPlanets;
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
    "Courteney",	// After Courteney Cox…
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
    
    ArrayList<Point> locations;
    

    public Universe(int length, int width, int num, int min, int max) {
        this.length = length;
        this.width = width;
        this.numberOfPlanets = num;
        //places systems appropriate distance from each other
        this.locations = layoutUniverse(min, max);
        
        this.universe = new ArrayList<>();
        for (int i = 0; i < numberOfPlanets; i++) {
            universe.add(new SolarSystem(names[i], locations.get(i).xCoordinate, 
                    locations.get(i).yCoordinate));
        }
        
    }
    
    private ArrayList<Point> layoutUniverse(int min, int max) {
        Random random = new Random();
        Point[][] field = new Point[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = new Point(i,j);
            }
        }
        
        ArrayList<Point> locations = new ArrayList<>();
        Point current = field[random.nextInt(length)][random.nextInt(width)];
        locations.add(current);
        
        for (int n = 0; n < numberOfPlanets -1; n++) {
            current.state = 2;
            //declare i and j before loop
            //change <= current.xCoo to <=....
            for (int i = current.xCoordinate - min; i <= current.xCoordinate + min; i++) {
                for (int j = current.yCoordinate - min; j <= current.yCoordinate + min; j++) {
                    field[i][j].state = 2;
                }
            }
            for (int i = current.xCoordinate + min + 1; i <= current.xCoordinate + max; i++) {
                for (int j = current.yCoordinate + min + 1; j <= current.yCoordinate + max; j++) {
                    field[i][j].state = 1;
                }
            }
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
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    private class Point {
        //location
        private int xCoordinate;
        private int yCoordinate;
        /*state = 0 is open
          state = 1 is primed
          state = anything else is locked
        */
        private int state;
        
        public Point (int x, int y) {
            this.xCoordinate = x;
            this.yCoordinate = y;
            this.state = 0;
        }
    }
}


