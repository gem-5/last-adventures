package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
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
    private int fuel;

    private Map<String, Integer> cargoList;
    private List<String> weaponList;
    private List<String> shieldList;
    private List<String> gadgetList;

    private Mercenary[] crewList;

    /**
     * Construct the ship from a given ship type.
     *
     * @param type The ShipType to use
     */
    public Ship(ShipType type) {
        this.type = type;
        this.fuel = type.getRange(); // range == max fuel
        this.cargoList = new TreeMap<>();
        this.weaponList = new ArrayList<>(type.getWeaponSlots());
        this.shieldList = new ArrayList<>(type.getShieldSlots());
        this.gadgetList = new ArrayList<>(type.getGadgetSlots());
        this.crewList = new Mercenary[type.getCrewSlots()];
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

        for (WeaponType w: getWeaponList()) {
            worth += w.getPrice();
        }

        for (ShieldType s: getShieldList()) {
            worth += s.getPrice();
        }

        for (GadgetType g: getGadgetList()) {
            worth += g.getPrice();
        }

        return worth;

    }

    public List<WeaponType> getWeaponList() {
        List<WeaponType> out = new ArrayList<>();
        Map weps = LastAdventures.data.get(WeaponType.KEY);
        for (String s : this.weaponList) {
            out.add((WeaponType) weps.get(s));
        }
        return out;
    }

    public List<ShieldType> getShieldList() {
        List<ShieldType> out = new ArrayList<>();
        Map shields = LastAdventures.data.get(ShieldType.KEY);
        for (String s : this.shieldList) {
            out.add((ShieldType) shields.get(s));
        }
        return out;
    }

    public List<GadgetType> getGadgetList() {
        List<GadgetType> out = new ArrayList<>();
        Map gadgets = LastAdventures.data.get(GadgetType.KEY);
        for (String s : this.gadgetList) {
            out.add((GadgetType) gadgets.get(s));
        }
        return out;
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
        for (WeaponType w: getWeaponList()) {
            if (w != null) {
                result += String.format("%n\t%s", w.getName());
            }
        }
        result += "\n  Shields:";
        for (ShieldType s: getShieldList()) {
            if (s != null) {
                result += String.format("%n\t%s", s.getName());
            }
        }
        result += "\n  Gadgets:";
        for (GadgetType g: getGadgetList()) {
            if (g != null) {
                result += String.format("%n\t%s", g.getName());
            }
        }
        return result;

    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getFuel() {
        return this.fuel;
    }

}
