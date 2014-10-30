package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.PirateEncounterController;
import edu.gatech.gem5.game.controllers.Controller;
import java.util.Random;

/**
 * A class representation of pirate encounters while traveling.
 *
 * @author Sam Blumenthal
 *
 */

public class Pirate extends AbstractNPC {

    /**
     * The initial FXML view for a pirate encounter.
     */
    public static final String viewFile = "/fxml/pirateencounter.fxml";

    /**
     * 
     * 
     * @param name Pirate's name
     * @param pilot pilot skill
     * @param fighter fighter skill
     * @param trader trader skill
     * @param engineer engineer skill
     * @param investor investor skill
     * @param ship ship used by the Pirate during fights
     * @param loot amount of money given on defeat
     */
    private Pirate(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
    }
    
    /**
     * Possible titles for pirates.
     */
    private static final String[] TITLES = {"Spaceman Recruit",
        "Deckhand",
        "Spaceman",
        "2nd Mate",
        "1st Mate",
        "Chief Mate",
        "Pirate Captain",
        "Pirate Fleet Commander",
        "Master Chief Commander of Pirates"};

    /**
     * A factory function used to create new pirates.
     * @param seed The seed used to determine pirate stats and rank
     * @return a new instance of the Pirate class
     */
    public static Pirate createPirate(int seed) {
        Random r = new Random();
        NameGenerator rand = new NameGenerator();

        //use seed to randonly allocate stats, with a string bias towards pilot & fighter
        int statTotal = (r.nextInt(seed) + 1) / 1500;
        int loot = r.nextInt(seed) / 100;
        int[] stats = {1, 1, 1, 1, 1};
        int titleIndex = Math.min((int) Math.sqrt(statTotal), TITLES.length - 1);
        String title = TITLES[titleIndex];

        for (int i = 0; i < statTotal; i++) {
            int n = r.nextInt(100);
            if (n < 30) {
                stats[0]++;
            }
            else if (n < 70) {
                stats[1]++;
            }
            else if (n < 80) {
                stats[2]++;
            }
            else if (n < 90) {
                stats[3]++;
            }
            else {
                stats[4]++;
            }
        }

        String name = title + " " + rand.newHumanName();

        // create the ship
        Ship ship = createShip(seed, 7500, 10000, 15000, 20000, false);

        return new Pirate(name, stats[0], stats[1], stats[2], stats[3], stats[4], ship, loot);
    }

    @Override
    public String getEncounterMessage() {
        String msg = super.getEncounterMessage();
        msg += this.getName();
        msg += "\n\n The Pirate attacks, prepare for battle!";
        return msg;
    }
    @Override
    public Controller getEncounterController() {
        return new PirateEncounterController(this);
    }

    @Override
    public String toString() {
        return "*PIRATE*\n" + super.toString();
    }

    /**
     *
     * @return The FXML to be shown that is specific to this type of encounter
     * after the initial encounter screen.
     */
    @Override
    public String getViewFile() {
        return viewFile;
    }
}
