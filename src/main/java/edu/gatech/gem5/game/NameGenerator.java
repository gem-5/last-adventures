package edu.gatech.gem5.game;

import java.util.Random;

public class NameGenerator {


    private Random rand;
    private static final String[] consonants = { "b", "c", "d", "f", "g", "h", "j",
                                                 "k", "l", "m", "n", "p", "q", "r",
                                                 "s", "t", "v", "w", "x", "y", "z",
                                                 "th", "gr", "ck", "ph", "br", "kr",
                                                 "qu", "ch", "wh", "dr", "tr", "cr",
                                                 "pr", "sr", "vr", "wr"};
    private static final String[] vowels = { "a", "e", "i", "o", "u", "ea", "ae", "ei", "ie", "ou", "eu", "ai", "ia", "y"};



    public NameGenerator() {
        this.rand = new Random();
    }

    /**
     * Method to generate new names
     * @return the newly generated name
     */
    public String newName() {
        int length = rand.nextInt(5) + 4;
        boolean vowel = rand.nextBoolean();
        String name = "";
        for (int i = 0; i < length; i++) {
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

    public static void main(String[] args) {
        NameGenerator n = new NameGenerator();
        for (int i = 0; i < 100; i++) {
            System.out.println(n.newName());
        }
    }
}
