package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EncounterController;
import edu.gatech.gem5.game.controllers.Controller;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;
import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.ShipType;
import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


/**
 * An abstract superclass for all NPCs in the game.
 * @author Sam Blumenthal
 */
public abstract class AbstractNPC extends AbstractHuman implements Encounterable {

    /**
     * The manager that this encounter will notify when it has been resolved.
     */
    EncounterManager manager;

    /**
     *
     * @param name NPC's name
     * @param pilot pilot skill
     * @param fighter fighter skill
     * @param trader trader skill
     * @param engineer engineer skill
     * @param investor investor skill
     * @param ship ship used by the NPC during encounters
     * @param loot amount of money given on defeat
     */

    private final String viewFile;


    protected AbstractNPC(String name, int pilot, int fighter, int trader,
                          int engineer, int investor, Ship ship, int loot, String viewFile) {
        super(name, pilot, fighter, trader, engineer, investor, ship, loot);
        this.viewFile = viewFile;
    }

    /**
     * @return the encounter message an NPC gives in an encounter with the Character.
     */
    @Override
    public String getEncounterMessage() {
        return "Whilst travelling, you have run into an encounter with:\n";
    }

    @Override
    public Controller getEncounterController() {
        return new EncounterController(this);
    }

    @Override
    public EncounterManager getManager() {
        return manager;
    }

    @Override
    public void setManager(EncounterManager m) {
        this.manager = m;
    }

    /**
     * Method to create ships loaded with various weapons and other items.
     * @param seed the seed used to generate everything
     * @param shipDivider divider used to determine the ship (smaller divider = better ship)
     * @param weaponDivider divider used to determine the number of weapons (smaller divider = more on the ship)
     * @param shieldDivider divider used to determine the number of shields (smaller divider = more on the ship)
     * @param gadgetDivider divider used to determine the number of gadgets (smaller divider = more on the ship)
     * @param cargo if cargo needs to be generated for this NPC
     * @return the newly created ship
     */
    protected static Ship createShip(int seed, int shipDivider, int weaponDivider,
                                     int shieldDivider, int gadgetDivider, boolean cargo) {
        Random r = new Random();
        ShipType[] ships = Data.SHIPS.get().values().toArray(new ShipType[0]);
        WeaponType[] weapons = Data.WEAPONS.get().values().toArray(new WeaponType[0]);
        ShieldType[] shields = Data.SHIELDS.get().values().toArray(new ShieldType[0]);
        GadgetType[] gadgets = Data.GADGETS.get().values().toArray(new GadgetType[0]);

        // create the ship
        int shipIndex = Math.min(r.nextInt(seed) / shipDivider, ships.length - 1);
        ShipType shipT = ships[shipIndex];
        Ship ship = new Ship(shipT);

        // add some weapons
        int weaponsNum = Math.min(r.nextInt(seed) / weaponDivider, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.addUpgrade(weapons[i]);
        }

        // add some shields
        int shieldsNum = Math.min(r.nextInt(seed) / shieldDivider, shipT.getShieldSlots() - 1);
        for (int i = 0; i < shieldsNum; i++) {
            ship.addUpgrade(shields[i]);
        }

        // add some gadgets
        int gadgetsNum = Math.min(r.nextInt(seed) / gadgetDivider, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.addUpgrade(gadgets[i]);
        }

        if (cargo) {
            GoodType[] goods = Data.GOODS.get().values().toArray(new GoodType[0]);

            List<GoodType> legalGoods = new ArrayList<>(); // Traders only carry legal goods
            for (GoodType g: goods) {
                if (g.isLegal()) {
                    legalGoods.add(g);
                }
            }

            // sort goods by price
            legalGoods.sort(new Comparator<GoodType>() {
                    public int compare(GoodType g1, GoodType g2) {
                        return g1.getValue() - g2.getValue();
                    }
                });

            int goodsIndex = Math.min(r.nextInt(seed) / 20000, legalGoods.size() - 3);
            int maxValue = seed;

            for (int i = 0; i < 3; i++) {
                GoodType good = legalGoods.get(goodsIndex + i);
                ship.addCargo(good.getKey(), r.nextInt(maxValue / good.getValue()));
            }

        }

        return ship;
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
