package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.ShipType;
import java.util.Stack;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class for ship objects owned by players/NPCs.
 *
 * @author  Creston Bunch
 * @author Jack Mueller
 */

public class Ship {

    private final ShipType type;
    private double health;
    private Map<String, Integer> cargoList;
    private Weapon[] weaponList;
    private Shield[] shieldList;
    private Gadget[] gadgetList;
    private Mercenary[] crewList;

    /**
     * Construct the ship from a given ship type.
     *
     * @param ship The ShipBase to copy.
     */
    public Ship(ShipType ship) {
        this.type = ship;
        this.cargoList = new TreeMap<String, Integer>();
        this.weaponList = new Weapon[ship.getWeaponSlots()];
        this.shieldList = new Shield[ship.getShieldSlots()];
        this.gadgetList = new Gadget[ship.getGadgetSlots()];
        this.crewList = new Mercenary[ship.getCrewSlots()];
    }

    /**
     * @return the type of ship.
     */
    public ShipType getType() {
        return this.type;
    }

    /**
     * @return the cargoList
     */
    public Map<String, Integer> getCargoList() {
        return cargoList;
    }

    /**
     * Add cargo to this ship
     *
     * @param good the key of the good to add
     * @param quantity the amount to add
     */
    public void addCargo(String good, int quantity) {
        if (!cargoList.containsKey(good)) {
            cargoList.put(good, 0);
        }
        cargoList.put(good, cargoList.get(good) + quantity);
    }

    /**
     * Take cargo from this ship
     *
     * @param good the key of the good to take
     * @param quantity the amount to take
     */
    public void takeCargo(String good, int quantity) {
        cargoList.put(good, cargoList.get(good) - quantity);
    }

    /**
     * Return the number of available cargo slots.
     *
     * @return int the number of slots available
     */
    public int getOpenBays() {
        int sum = 0;
        for (int i : cargoList.values()) {
            sum += i;
        }
        return getType().getCargoSlots() - sum;
    }

    public int getNetWorth() {
        int worth = type.getPrice();

        for (Weapon w: weaponList) {
            worth += w.getWorth();
        }

        for (Shield s: shieldList) {
            worth += s.getWorth();
        }

        for (Gadget g: gadgetList) {
            worth += g.getWorth();
        }

        return worth;

    }

}
