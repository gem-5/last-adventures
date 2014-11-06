package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.TraderEncounterController;
import edu.gatech.gem5.game.controllers.Controller;
import edu.gatech.gem5.game.data.GoodType;
import java.util.Random;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;

/**
 * A class representation of trader encounters while traveling.
 *
 * @author Sam Blumenthal
 *
 */

public class Trader extends AbstractNPC implements Traderable {

    /**
     *
     * @param name Trader's name
     * @param pilot pilot skill
     * @param fighter fighter skill
     * @param trader trader skill
     * @param engineer engineer skill
     * @param investor investor skill
     * @param ship ship used by the trader during encounters
     * @param loot amount of money given on defeat
     */
    private Trader(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int loot) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot, "/fxml/traderencounter.fxml");
    }

    /**
     * Possible titles for traders.
     */
    private static final String[] TITLES = {"Peddler",
        "Traveling Salesman",
        "Space Trader",
        "Merchant",
        "Investor",
        "Cargo Captain",
        "Freighter Captain",
        "Guild Chief",
        "Senior Guild Chief"

    };

    /**
     * A factory function used to create new traders.
     * @param seed The seed used to determine trader stats and rank
     * @return a new instance of the Trader class
     */
    public static Trader createTrader(int seed) {
        Random r = new Random();
        NameGenerator rand = new NameGenerator();

        //use seed to randomly allocate stats, with a string bias towards trader & investor
        int statTotal = (r.nextInt(seed) + 1) / 1500;
        int loot = r.nextInt(seed) / 20;
        int[] stats = {1, 1, 1, 1, 1};
        int titleIndex = Math.min((int) Math.sqrt(statTotal), TITLES.length - 1);
        String title = TITLES[titleIndex];

        for (int i = 0; i < statTotal; i++) {
            int n = r.nextInt(100);
            if (n < 30) {
                stats[4]++;
            } else if (n < 70) {
                stats[2]++;
            } else if (n < 80) {
                stats[1]++;
            } else if (n < 90) {
                stats[2]++;
            } else {
                stats[3]++;
            }

        }

        String name = title + " " + rand.newHumanName();

        // create the ship
        Ship ship = createShip(seed, 10000, 20000, 15000, 10000, true);

        return new Trader(name, stats[0], stats[1], stats[2], stats[3], stats[4], ship, loot);
    }

    /**
     * Avoids the generic encounter controller so functionality can be added.
     * @return An encounter controller specific to Traders
     */
    @Override
    public Controller getEncounterController() {
        return new TraderEncounterController(this);
    }

    @Override
    public String getEncounterMessage() {
        String msg = super.getEncounterMessage();
        msg += this.getName();
        msg += "\n\n\"Why, it is a pleasure to meet a fellow trader on such rough space as this. Would you care to trade?\"";
        return msg;
    }

    @Override
    public Map<String, Integer> getStock() {
        return this.getShip().getCargoList();
    }

    @Override
    public Map<String, Integer> getSupply() {
        Map<String, Integer> out = new TreeMap<>();
        Set<String> cargo = this.getShip().getCargoList().keySet();
        //Character player = LastAdventures.getCurrentSaveFile().getCharacter();
        //double multiplier = (this.getTrader() - player.getTrader() + 100) / 100.0;
        for (String s: cargo) {
            GoodType g = Data.GOODS.get(s);
            double value = g.getValue();
            out.put(s, (int) Math.round(value));
        }
        return out;
    }

    @Override
    public Map<String, Integer> getDemand(Collection<String> goods) {
        Map<String, Integer> in = new TreeMap<>();
        //double multiplier = (player.getTrader() - this.getTrader() + 100) / 100.0;
        for (String g : goods) {
            GoodType gt = Data.GOODS.get(g);
            double value = gt.getValue();
            in.put(g, (int) Math.round(value));
        }
        return in;
    }

    @Override
    public String toString() {
        return "*TRADER*\n" + super.toString();
    }

}
