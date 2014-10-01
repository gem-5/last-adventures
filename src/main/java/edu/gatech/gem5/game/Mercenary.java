package edu.gatech.gem5.game;

import edu.gatech.gem5.game.NameGenerator;
import java.util.Random;

/**
 * A class for crew members for ships.
 *
 * @author Creston Bunch
 * @author Sam Blumenthal
 */

public class Mercenary extends Human {

    /**
     * Construct the mercenary with some stats.
     */
    private Mercenary(String name, int pilot, int fighter, int trader, int engineer, int investor) {

        // Mercenaries don't have a ship of their own
        // Mercenary money starts at 0. Gets increased once player pays him/her.
        super(name, pilot, fighter, trader, engineer, investor, null, 0);
    }

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

    public static Mercenary createMercenary() {
        int seed = LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth();
        return createMercenary(seed);
    }

    public static void main(String[] args) {

        for (int i = 1; i < 100; i++) {
            Mercenary bob = createMercenary((1000 * i % 50000) + 1);
            System.out.println(bob);
        }
    }

}
