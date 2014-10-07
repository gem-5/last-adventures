package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EncounterController;
import edu.gatech.gem5.game.NameGenerator;
import java.util.Random;

/**
 * A class representation of trader encounters while travelling
 *
 * @author Sam Blumenthal
 *
 */

public class Trader extends NPC {

    private Trader(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }

    private static final String[] titles = { "Peddler",
                                      "Traveling Salesman",
                                      "Space Trader",
                                      "Merchant",
                                      "Investor",
                                      "Cargo Captain",
                                      "Freighter Captain",
                                      "Guild Chief",
                                      "Senior Guild Chief"

    };

    public static Trader createTrader(int seed, Ship ship) {
        Random r = new Random();
        NameGenerator rand = new NameGenerator();

        //use seed to randomly allocate stats, with a string bias towards trader & investor
        int statTotal = (r.nextInt(seed) + 1) / 1500;
        int loot = r.nextInt(seed) / 20;
        int[] stats = {1, 1, 1, 1, 1};
        int titleIndex = Math.min((int) Math.sqrt(statTotal), titles.length - 1);
        String title = titles[titleIndex];

        for (int i = 0; i < statTotal; i++) {
            int n = r.nextInt(100);
            if (n < 30)
                stats[4]++;
            else if (n < 70)
                stats[2]++;
            else if (n < 80)
                stats[1]++;
            else if (n < 90)
                stats[2]++;
            else
                stats[3]++;

        }

        String name = title + " " + rand.newHumanName();

        return new Trader(name, stats[0], stats[1], stats[2], stats[3], stats[4], ship, loot);
    }

    // private static Trader createTrader() {
    //     int seed = LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth();
    //     return createTrader(seed, null);
    // }

    // public static void main(String[] args) {

    //     for (int i = 1; i < 100; i++) {
    //         Trader bob = createTrader((1000 * i % 50000) + 1);
    //         System.out.println(bob);
    //     }
    // }
    public void processEncounter() {
        LastAdventures.swap(new EncounterController(this));
    }

    public String getEncounterMessage() {
        String msg = super.getEncounterMessage();
        msg += this.toString();
        msg += "\n\nHowever human to human trading is not yet implemented, so the Trader flees in confusion.";
        return msg;
    }

    @Override
    public String toString() {
        return "*TRADER*\n" + super.toString();
    }
}
