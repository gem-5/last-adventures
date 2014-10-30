package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EncounterController;
import edu.gatech.gem5.game.controllers.Controller;
import java.util.Random;

/**
 * A class representation of police encounters while traveling.
 *
 * @author Sam Blumenthal
 *
 */

public class Police extends AbstractNPC {
    
    /**
     * The initial FXML view for a police encounter.
     */
    public final String viewFile = "/fxml/encounter.fxml";

    /**
     * 
     * @param name Police's name
     * @param pilot pilot skill
     * @param fighter fighter skill
     * @param trader trader skill
     * @param engineer engineer skill
     * @param investor investor skill
     * @param ship ship used by the police during encounters
     * @param loot amount of money given on defeat
     */
    private Police(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }

    /**
     * Possible titles for police.
     */
    private static final String[] TITLES = {"Deputy",
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
     * @return a new instance of the Police class
     */
    public static Police createPolice(int seed) {
        Random r = new Random();
        NameGenerator rand = new NameGenerator();

        //use seed to randomly allocate stats, with a string bias towards pilot & fighter
        int statTotal = (r.nextInt(seed) + 1) / 1500;
        int loot = r.nextInt(seed) / 20;
        int[] stats = {1, 1, 1, 1, 1};
        int titleIndex = Math.min((int) Math.sqrt(statTotal), TITLES.length - 1);
        String title = TITLES[titleIndex];

        for (int i = 0; i < statTotal; i++) {
            int n = r.nextInt(100);
            if (n < 30) {
                stats[1]++;
            } else if (n < 70) {
                stats[0]++;
            } else if (n < 80) {
                stats[2]++;
            } else if (n < 90) {
                stats[3]++;
            } else {
                stats[4]++;
            }
        }

        String name = title + " " + rand.newHumanName();

        // Now let's create and load the ship
        Ship ship = createShip(seed, 5000, 15000, 10000, 20000, false);

        return new Police(name, stats[0], stats[1], stats[2], stats[3], stats[4], ship, loot);
    }

    @Override
    public Controller getEncounterController() {
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
        return viewFile;
    }
}
