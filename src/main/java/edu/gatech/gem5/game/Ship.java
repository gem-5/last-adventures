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
    private Good[] cargoList;
    private Stack<Integer> openBays;
    // private Weapon[] weaponList;
    private List<Weapon> weaponList;
    // private Shield[] shieldList;
    private List<Shield> shieldList;
    // private Gadget[] gadgetList;
    private List<Gadget> gadgetList;
    private Mercenary[] crewList;

    /**
     * Construct the ship from a given ship type.
     *
     * @param ship The ShipBase to copy.
     */
    public Ship(ShipType ship) {
        this.type = ship;
        this.cargoList = new Good[ship.getCargoSlots()];
        this.openBays = new Stack<>();
        for (int i = 0; i < cargoList.length; i++) {
            openBays.push(i); // all bays are open at the beginning
        }
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
    public Good[] getCargoList() {
        return cargoList;
    }

    /**
     * @param cargoList the cargoList to set
     */
    public void setCargoList(Good[] cargoList) {
        this.cargoList = cargoList;
    }

    /**
     * OpenBays is sent to Transaction class to tell it which bays to put
     * the cargo in.
     * @return the open bays
     */
    public Stack<Integer> getOpenBays() {
        return openBays;
    }

    /**
     * OpenBays is received from Transaction class once it has filled some bays.
     * @param openBays the open bays
     */
    public void setOpenBays(Stack<Integer> openBays) {
        this.openBays = openBays;
    }

    /**
     * @param bay the bay to open
     */
    public void openNewBay(int bay) {
        openBays.push(bay);
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

    public Map<GoodType, Integer> getCargoCounts() {
        Map<GoodType, Integer> goodMap = new TreeMap<>();
        for (Good g: cargoList) {
            if (g != null) {
                if (goodMap.get(g.getType()) == null) {
                    goodMap.put(g.getType(), 1);
                } else {
                    goodMap.put(g.getType(), goodMap.get(g.getType()) + 1);
                }
            }
        }
        return goodMap;
    }

    public String toString() {
        String result = "Ship: ";
        result += this.type.getName();
        result += "\n  Cargo:";
        for (Map.Entry<GoodType, Integer> kv: getCargoCounts().entrySet()) {
            result += String.format("%n\t%s  %d", kv.getKey().getName(), kv.getValue());
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
