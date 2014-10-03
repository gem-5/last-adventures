package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;
import java.util.Stack;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

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
    private List<Weapon> weaponList;
    private List<Shield> shieldList;
    private List<Gadget> gadgetList;

    private Mercenary[] crewList;

    /**
     * Construct the ship from a given ship type.
     *
     * @param ship The ShipBase to copy.
     */
    public Ship(ShipType ship) {
        this.type = ship;
        this.cargoList = new TreeMap<String, Integer>();
        this.weaponList = new ArrayList<>(ship.getWeaponSlots());
        this.shieldList = new ArrayList<>(ship.getShieldSlots());
        this.gadgetList = new ArrayList<>(ship.getGadgetSlots());
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

    public List<Weapon> getWeaponList() {
        return this.weaponList;
    }

    public List<Shield> getShieldList() {
        return this.shieldList;
    }

    public List<Gadget> getGadgetList() {
        return this.gadgetList;
    }

    public String toString() {
        String result = "Ship: ";
        result += this.type.getName();
        result += "\n  Cargo:";
        for (Map.Entry<String, Integer> kv: this.cargoList.entrySet()) {
            String goodName =
                ((GoodType) LastAdventures.data.get(GoodType.KEY).get(kv.getKey())).getName();
            result += String.format("%n\t%s  %d", goodName, kv.getValue());
        }
        result += "\n  Weapons:";
        for (Weapon w: weaponList) {
            if (w != null) {
                result += String.format("%n\t%s", w.getType().getName());
            }
        }
        result += "\n  Shields:";
        for (Shield s: shieldList) {
            if (s != null) {
                result += String.format("%n\t%s", s.getType().getName());
            }
        }
        result += "\n  Gadgets:";
        for (Gadget g: gadgetList) {
            if (g != null) {
                result += String.format("%n\t%s", g.getType().getName());
            }
        }
        return result;

    }

}
