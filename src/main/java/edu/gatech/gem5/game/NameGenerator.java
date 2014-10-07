package edu.gatech.gem5.game;

import java.util.Random;

/**
 * A class for generating Random Names for multiple objects in Last Adventures
 * @author Sam Blumenthal
 */
public class NameGenerator {


    private Random rand;
    private boolean unicode;
    private static final String[] consonants = { "b", "c", "d", "f", "g", "h", "j",
                                                 "k", "l", "m", "n", "p", "q", "r",
                                                 "s", "t", "v", "w", "x", "y", "z",
                                                 "th", "gr", "ck", "ph", "br", "kr",
                                                 "qu", "ch", "wh", "dr", "tr", "cr",
                                                 "pr", "sr", "vr", "wr"};
    private static final String[] vowels = { "a", "e", "i", "o", "u", "ea", "ae",
                                             "ei", "ie", "ou", "eu", "ai", "ia", "y"};

    private static final String[] greek = { "Alpha", "Beta", "Gamma", "Delta",
                                            "Epsilon", "Zeta", "Eta", "Theta",
                                            "Iota", "Kappa", "Lambda", "Mu",
                                            "Nu", "Xi", "Omicron", "Pi", "Rho",
                                            "Sigma", "Tau", "Upsilon", "Phi",
                                            "Chi", "Psi", "Omega" };

    private static final String[] realGreek = { "\u03B1", "\u03B2", "\u03B3", "\u03B4", "\u03B5",
                                                "\u03B6", "\u03B7", "\u03B8", "\u03B9", "\u03BA",
                                                "\u03BB", "\u03BC", "\u03BD", "\u03BE", "\u03BF",
                                                "\u03C1", "\u03C2", "\u03C3", "\u03C4", "\u03C5",
                                                "\u03C6", "\u03C7", "\u03C8", "\u03C9" };



    public NameGenerator() {
        this.rand = new Random();
    }

    public NameGenerator(boolean unicode) {
        this.rand = new Random();
        this.unicode = unicode;
    }

    /**
     * Method to generate new names from a given min and max length
     * @param minLength The minimum length of the name to be generated
     * @param maxLength The maximum length of the name to be generated
     * @return the newly generated name
     */
    public String newName(int minLength, int maxLength) {
        int length = rand.nextInt(maxLength - minLength) + minLength;
        boolean vowel = rand.nextBoolean();
        String name = "";
        // for (int i = 0; name.length() < length; i++) {
        while (name.length() < length) {
            if (vowel) {
                // name += pickNVowels(rand.nextInt(2) + 1);
                name += consonants[rand.nextInt(consonants.length)];
            } else {
                // name += pickNConsonants(rand.nextInt(2) + 1);
                name += vowels[rand.nextInt(vowels.length)];
            }
            vowel = !vowel;
        }
        return java.lang.Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    /**
     * Method to generate new names with a maximum length of the given value
     * and a minimum length of half that value
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
        return newName(4, 9);
    }
    /**
     * Method to generate new planet names with Greek characters
     * @return the newly generated name
     */
    public String newPlanetName() {
        String name = newName();
        if (unicode) {
            name += "-" + realGreek[rand.nextInt(24)];
        } else {
            name += "-" + greek[rand.nextInt(24)];
        }
        return name;
    }

    /**
     * Function to name planets in an already named Solar System.
     * @param numberOfPlanets the total number of Planets in the Solar System.
     * @param solarSystemName the name of the Solar System
     * @return an array of randomly generated planet names with Greek Characters in alphabetic order.
     */
    public String[] planetNames(int numberOfPlanets, String solarSystemName) {
        String[] names = new String[numberOfPlanets];
        String name = solarSystemName;
        // int i = numberOfPlanets;
        for (int i = 0, j = 0; i < numberOfPlanets; i++, j++) {
            j = rand.nextInt(24 - j - numberOfPlanets + i) + j;
            names[i] = name + "-" + (unicode ? realGreek[j] : greek[j]);
        }
        return names;
    }

    /**
     * Function to create new random human names in the form of: Firstname Lastname.
     * @return the newly generated Human name as a String.
     */
    public String newHumanName() {
        return String.format("%s %s", newName(4,6), newName(4,8));
    }


    /**
     * A test function for testing the generation of different names.
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
