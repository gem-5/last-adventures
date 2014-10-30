package edu.gatech.gem5.game;

import java.util.Random;

/**
 * A class for generating Random Names for multiple objects in Last Adventures.
 * @author Sam Blumenthal
 */
public class NameGenerator {

    /**
     * Name generator for the instance to use, so it will generate the same 
     * names given the same seed.
     */
    private Random rand;
    /**
     * True if there is Unicode support on the machine (not implemented).
     */
    private boolean unicode;
    /**
     * List of standalone consonants and diphthongs that may appear in generated
     * names.
     */
    private static final String[] CONSONANTS = {"b", "c", "d", "f", "g", "h", "j",
        "k", "l", "m", "n", "p", "q", "r",
        "s", "t", "v", "w", "x", "y", "z",
        "th", "gr", "ck", "ph", "br", "kr",
        "qu", "ch", "wh", "dr", "tr", "cr",
        "pr", "sr", "vr", "wr"};
    /**
     * List of standalone vowels and vowel blends that may appear in generated
     * names.
     */
    private static final String[] VOWELS = {"a", "e", "i", "o", "u", "ea", "ae",
        "ei", "ie", "ou", "eu", "ai", "ia", "y"};

    /**
     * List of the Romanized phonetic versions of Greek letters.
     */
    private static final String[] GREEK = {"Alpha", "Beta", "Gamma", "Delta",
        "Epsilon", "Zeta", "Eta", "Theta",
        "Iota", "Kappa", "Lambda", "Mu",
        "Nu", "Xi", "Omicron", "Pi", "Rho",
        "Sigma", "Tau", "Upsilon", "Phi",
        "Chi", "Psi", "Omega"};

    /**
     * List of Greek letters by their Unicode value.
     */
    private static final String[] REAL_GREEK = {"\u03B1", "\u03B2", "\u03B3", "\u03B4", "\u03B5",
        "\u03B6", "\u03B7", "\u03B8", "\u03B9", "\u03BA",
        "\u03BB", "\u03BC", "\u03BD", "\u03BE", "\u03BF",
        "\u03C1", "\u03C2", "\u03C3", "\u03C4", "\u03C5",
        "\u03C6", "\u03C7", "\u03C8", "\u03C9"};

    /**
     * Create a new name generator that generates names pseudorandomly. The seed
     * is chosen by Random's default way of determining seeds.
     */
    public NameGenerator() {
        this.rand = new Random();
    }

    /**
     * Create a new name generator that generates names pseudorandomly. The seed
     * is chosen by Random's default way of determining seeds.
     * @param u if true, it is assumed that the system support unicode,
     * false if it does not.
     */
    public NameGenerator(boolean u) {
        this.rand = new Random();
        this.unicode = u;
    }

    /**
     * Method to generate new names from a given min and max length.
     * @param minLength The minimum length of the name to be generated
     * @param maxLength The maximum length of the name to be generated
     * @return the newly generated name
     */
    public String newName(int minLength, int maxLength) {
        int length = rand.nextInt(maxLength - minLength) + minLength;
        boolean vowel = rand.nextBoolean();
        String name = "";
        while (name.length() < length) {
            if (vowel) {
                name += CONSONANTS[rand.nextInt(CONSONANTS.length)];
            } else {
                name += VOWELS[rand.nextInt(VOWELS.length)];
            }
            vowel = !vowel;
        }
        return java.lang.Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    /**
     * Method to generate new names with a maximum length of the given value
     * and a minimum length of half that value.
     * @param maxLength The maximum length of the name to be generated
     * @return the newly generated name
     */
    public String newName(int maxLength) {
        return newName(maxLength / 2, maxLength);
    }
    /**
     * Method to generate new names with a size between 4-9 characters.
     * @return the newly generated name
     */
    public String newName() {
        return newName(4, 9); //TODO magic numbers
    }
    /**
     * Method to generate planet names with Greek characters. Planet names
     * consist of the system name that is passed in, followed by the Greek
     * letter whose index in the alphabet is passed.
     * @param solarSystemName name of this planet's solar system
     * @param greekIndex index of desired letter in GREEK and REAL_GREEK
     * @return the newly generated name
     */
    public String newPlanetName(String solarSystemName, int greekIndex) {
        String name = solarSystemName + "-";
        if (unicode) {
            name += REAL_GREEK[greekIndex];
        } else {
            name += GREEK[greekIndex];
        }
        return name;
    }

    /**
     * Function to name planets in an already named Solar System.
     * @param numberOfPlanets the total number of Planets in the Solar System.
     * @param solarSystemName the name of the Solar System
     * @return an array of randomly generated planet names with Greek Characters
     * in alphabetic order.
     */
    public String[] planetNames(int numberOfPlanets, String solarSystemName) {
        String[] names = new String[numberOfPlanets];
        
        /*
         * Make sure the starting Greek letter is such that there are enough
         * letters afterwards to be alphabetical for all the planets in the
         * systems
         */
        int greekIndex = rand.nextInt(GREEK.length - numberOfPlanets);
        for (int i = 0; i < numberOfPlanets; i++) {
            names[i] = newPlanetName(solarSystemName, greekIndex);
            
            //update the Greek letter used, using the same rules as above
            greekIndex = rand.nextInt(GREEK.length - numberOfPlanets + i + 1 
                    - greekIndex) + greekIndex;
        }
        return names;
    }

    /**
     * Function to create new random human names in the form of: Firstname Lastname.
     * @return the newly generated Human name as a String.
     */
    public String newHumanName() {
        return String.format("%s %s", newName(4, 6), newName(4, 8));
    }


    //TODO refactor into a JUnit test
    /**
     * A test function for testing the generation of different names.
     * @param args commandline arguments.
     */
    public static void main(String[] args) {
        NameGenerator n = new NameGenerator(true);
        // System.out.println("Random Names:");
        // for (int i = 0; i < 10; i++) {
        //     System.out.println(n.newName());
        // }
        // System.out.println();
        // System.out.println("Planet Names:");
        // for (int i = 0; i < 10; i++) {
        //     System.out.println(n.newPlanetName());
        // }
        for (int i = 0; i < 10; i++) {
            System.out.println();
            String system = n.newName();
            System.out.printf("System: %s, Planets:%n", system);
            for (String p: n.planetNames(5, system)) {
                System.out.println(p);
            }
        }
        // System.out.println();
        // System.out.println("Human Names:");
        // for (int i = 0; i < 10; i++) {
        //     System.out.println(n.newHumanName());
        // }

    }
}
