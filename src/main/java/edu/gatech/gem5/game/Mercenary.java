package edu.gatech.gem5.game;

import java.util.Random;

/**
 * A class for crew members for ships.
 *
 * @author Creston Bunch
 * @author Sam Blumenthal
 */

public class Mercenary extends AbstractHuman {

    /**
.
     */
    /**
     * Construct the mercenary with the given stats.
     * 
     * @param n name of the mercenary
     * @param pilotSkill pilot skill that can override the employer's skill
     * @param fighterSkill fighter skill that can override the employer's skill
     * @param traderSkill trader skill that can override the employer's skill
     * @param engineerSkill engineer skill that can override the employer's skill
     * @param investorSkill investor skill that can override the employer's skill
     */
    private Mercenary(String n, int pilotSkill, int fighterSkill, 
            int traderSkill, int engineerSkill, int investorSkill) {

        // Mercenaries don't have a ship of their own
        // Mercenary money starts at 0. Gets increased once player pays him/her.
        super(n, pilotSkill, fighterSkill, traderSkill, engineerSkill,
                investorSkill, null, 0);
    }

    /**
     * 
     * @param seed seed for random generator to use
     * @return A new mercenary based on the given seed
     */
    private static Mercenary createMercenary(int seed) {
        Random r = new Random();
        NameGenerator rand = new NameGenerator();

        //use seed to randomly allocate stats
        int statTotal = (r.nextInt(seed) + 1) / 500;
        int[] stats = {1, 1, 1, 1, 1};

        for (int i = 0; i < statTotal; i++) {
            stats[r.nextInt(5)]++;
        }

        String name = rand.newHumanName();


        return new Mercenary(name, stats[0], stats[1], stats[2], stats[3], stats[4]);

    }

    /**
     * 
     * @return default method of creating mercenary, which uses the player's
     * net worth as the seed for random generation
     */
    public static Mercenary createMercenary() {
        int seed = LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth();
        return createMercenary(seed);
    }

    /**
     * Should be refactored into a JUnit test.
     * @param args command line args
     */
    public static void main(String[] args) {

        for (int i = 1; i < 100; i++) {
            Mercenary bob = createMercenary((1000 * i % 50000) + 1);
            System.out.println(bob);
        }
    }

}
