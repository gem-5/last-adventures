package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EncounterController;
import edu.gatech.gem5.game.NameGenerator;
import edu.gatech.gem5.game.data.GoodType;
import java.util.Random;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class representation of trader encounters while travelling
 *
 * @author Sam Blumenthal
 *
 */

public class Trader extends NPC implements Traderable {

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

    /**
     * A factory function used to create new traders.
     * @param seed The seed used to determine trader stats and rank
     * @param ship The ship that the new trader will spawn with
     * @return a new instance of the Trader class
     */
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


    @Override
    public void processEncounter() {
        LastAdventures.swap(new EncounterController(this));
    }

    @Override
    public String getEncounterMessage() {
        String msg = super.getEncounterMessage();
        msg += this.toString();
        msg += "\n\nHowever human to human trading is not yet implemented, so the Trader flees in confusion.";
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
        Character player = LastAdventures.getCurrentSaveFile().getCharacter();
        double multiplier = (this.getTrader() - player.getTrader() + 100) / 100.0;
        for (String s: cargo) {
            GoodType g = (GoodType)
                LastAdventures.data.get(GoodType.KEY).get(s);
            double value = g.getValue() * multiplier;
            out.put(s, (int) Math.round(value));
        }
        return out;
    }

    @Override
    public Map<String, Integer> getDemand() {
        Map<String, Integer> in = new TreeMap<>();
        Character player = LastAdventures.getCurrentSaveFile().getCharacter();
        Ship playerShip = player.getShip();
        double multiplier = (player.getTrader() - this.getTrader() + 100) / 100.0;
        for (String g : playerShip.getCargoList().keySet()) {
            GoodType gt = (GoodType) LastAdventures.data.get(GoodType.KEY).get(g);
            double value = gt.getValue() * multiplier;
            in.put(g, (int) Math.round(value));
        }
        return in;
    }


    @Override
    public String toString() {
        return "*TRADER*\n" + super.toString();
    }
}
