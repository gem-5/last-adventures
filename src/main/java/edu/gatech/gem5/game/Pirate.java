package edu.gatech.gem5.game;

import edu.gatech.gem5.game.NameGenerator;
import java.util.Random;

/**
 * A class representation of pirate encounters while travelling
 *
 * @author Sam Blumenthal
 *
 */

public class Pirate extends Human {

    private Pirate(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }

    public static Pirate createPirate(int seed, Ship ship) {
        Random r = new Random();
        NameGenerator rand = new NameGenerator();

        //use seed to randonly allocate stats, with a string bias towards pilot & fighter
        int statTotal = (r.nextInt(seed) + 1) / 1500;
        int loot = r.nextInt(seed) / 100;
        int[] stats = {1, 1, 1, 1, 1};

        for (int i = 0; i < statTotal; i++) {
            int n = r.nextInt(100);
            if (n < 30)
                stats[0]++;
            else if (n < 70)
                stats[1]++;
            else if (n < 80)
                stats[2]++;
            else if (n < 90)
                stats[3]++;
            else
                stats[4]++;

        }

        String name = rand.newHumanName();

        return new Pirate(name, stats[0], stats[1], stats[2], stats[3], stats[4], ship, loot);
    }

    // private static Pirate createPirate() {
    //     int seed = LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth();
    //     return createPirate(seed, null);
    // }

    // public static void main(String[] args) {

    //     for (int i = 1; i < 100; i++) {
    //         Pirate bob = createPirate((1000 * i % 50000) + 1);
    //         System.out.println(bob);
    //     }
    // }

    @Override
    public String toString() {
        return "*PIRATE*\n" + super.toString();
    }
}
