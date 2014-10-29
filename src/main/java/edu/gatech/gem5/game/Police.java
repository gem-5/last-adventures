package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EncounterController;
import edu.gatech.gem5.game.controllers.Controller;
import edu.gatech.gem5.game.NameGenerator;
import java.util.Random;

/**
 * A class representation of police encounters while travelling
 *
 * @author Sam Blumenthal
 *
 */

public class Police extends AbstractNPC {

    public final String VIEW_FILE = "/fxml/encounter.fxml";

    private Police(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }

    private static final String[] titles = { "Deputy",
                                             "Inspector",
                                             "Sergeant",
                                             "Lieutenant",
                                             "Captain",
                                             "Major",
                                             "Colonel",
                                             "Deputy Sheriff",
                                             "Sheriff"
    };
    /**
     * A factory function used to create new police.
     * @param seed The seed used to determine police stats and rank
     * @param ship The ship that the new police will spawn with
     * @return a new instance of the Police class
     */
    public static Police createPolice(int seed) {
        Random r = new Random();
        NameGenerator rand = new NameGenerator();

        //use seed to randomly allocate stats, with a string bias towards pilot & fighter
        int statTotal = (r.nextInt(seed) + 1) / 1500;
        int loot = r.nextInt(seed) / 20;
        int[] stats = {1, 1, 1, 1, 1};
        int titleIndex = Math.min((int) Math.sqrt(statTotal), titles.length - 1);
        String title = titles[titleIndex];

        for (int i = 0; i < statTotal; i++) {
            int n = r.nextInt(100);
            if (n < 30)
                stats[1]++;
            else if (n < 70)
                stats[0]++;
            else if (n < 80)
                stats[2]++;
            else if (n < 90)
                stats[3]++;
            else
                stats[4]++;

        }

        String name = title + " " + rand.newHumanName();

        // Now let's create and load the ship
        Ship ship = createShip(seed, 5000, 15000, 10000, 20000, false);

        return new Police(name, stats[0], stats[1], stats[2], stats[3], stats[4], ship, loot);
    }

    // private static Police createPolice() {
    //     int seed = LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth();
    //     return createPolice(seed, null);
    // }

    // public static void main(String[] args) {

    //     for (int i = 1; i < 100; i++) {
    //         Police bob = createPolice((1000 * i % 50000) + 1);
    //         System.out.println(bob);
    //     }
    // }
    @Override
    public Controller getEncounterController() {
        // LastAdventures.swap(new EncounterController(this));
        return new EncounterController(this);

    }


    @Override
        public String getEncounterMessage() {
        String msg = super.getEncounterMessage();
        msg += this.toString();
        msg += "\n\nHowever infamy and cargo checks are not yet implemented, so the Police flee in confusion.";
        return msg;
    }

    @Override
    public String toString() {
        return "*POLICE*\n" + super.toString();
    }

    @Override
    public String getViewFile() {
        return VIEW_FILE;
    }
}
