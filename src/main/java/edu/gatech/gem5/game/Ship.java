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
import java.util.Random;

/**
 * A class for ship objects owned by players/NPCs.
 *
 * @author  Creston Bunch
 * @author Jack Mueller
 */

public class Ship {

    private final ShipType type;
    private double health;
    private boolean destroyed;
    private int fuel;

    private Map<String, Integer> cargoList;
    private List<WeaponType> weaponList;
    private List<Shield> shieldList;
    private List<GadgetType> gadgetList;

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
        this.health = (double) type.getHullStrength();
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

        for (Shield s: getShieldList()) {
            worth += s.getType().getPrice();
        }

        for (GadgetType g: getGadgetList()) {
            worth += g.getPrice();
        }

        return worth;

    }

    // public List<WeaponType> getWeaponList() {
    //     List<WeaponType> out = new ArrayList<>();
    //     Map weps = LastAdventures.data.get(WeaponType.KEY);
    //     for (String s : this.weaponList) {
    //         out.add((WeaponType) weps.get(s));
    //     }
    //     return out;
    // }
    public List<WeaponType> getWeaponList() {
        return this.weaponList;
    }
    /**
     * Adds a weapon to the ship's weapon list
     * @param wt the Weapon Type being added to the ship
     * @return true if successfully added, false otherwise
     */
    public boolean addWeapon(WeaponType wt) {
        if (weaponList.size() < type.getWeaponSlots()) {
            return weaponList.add(wt);
        }
        return false;
    }

    /**
     * Method to find the number of available slots for new weapons
     * @return the number of free weapon slots
     */
    public int getOpenWeaponSlots() {
        return type.getWeaponSlots() - weaponList.size();
    }

    // public List<ShieldType> getShieldList() {
    //     List<ShieldType> out = new ArrayList<>();
    //     Map shields = LastAdventures.data.get(ShieldType.KEY);
    //     for (String s : this.shieldList) {
    //         out.add((ShieldType) shields.get(s));
    //     }
    //     return out;
    // }

    public List<Shield> getShieldList() {
        return this.shieldList;
    }

    /**
     * Adds a shield to the ship's shield list
     * @param s the Shield being added to the ship
     * @return true if successfully added, false otherwise
     */
    public boolean addShield(Shield s) {
        if (shieldList.size() < type.getShieldSlots()) {
            return shieldList.add(s);
        }
        return false;
    }

    /**
     * Method to find the number of available slots for new shields
     * @return the number of free shield slots
     */
    public int getOpenShieldSlots() {
        return type.getShieldSlots() - shieldList.size();
    }

    // public List<GadgetType> getGadgetList() {
    //     List<GadgetType> out = new ArrayList<>();
    //     Map gadgets = LastAdventures.data.get(GadgetType.KEY);
    //     for (String s : this.gadgetList) {
    //         out.add((GadgetType) gadgets.get(s));
    //     }
    //     return out;
    // }
    public List<GadgetType> getGadgetList() {
        return this.gadgetList;
    }
    /**
     * Adds a gadget to the ship's gadget list
     * @param gt the Gadget Type being added to the ship
     * @return true if successfully added, false otherwise
     */
    public boolean addGadget(GadgetType gt) {
        if (gadgetList.size() < type.getGadgetSlots()) {
            return gadgetList.add(gt);
        }
        return false;
    }

    /**
     * Method to find the number of available slots for new gadgets
     * @return the number of free gadget slots
     */
    public int getOpenGadgetSlots() {
        return type.getGadgetSlots() - shieldList.size();
    }

    @Override
    public String toString() {
        String result = "Ship: ";
        result += this.type.getName();
        result += "\n  Cargo:";
        for (Map.Entry<String, Integer> kv: this.cargoList.entrySet()) {
            String goodName =
                (Data.GOODS.get(kv.getKey())).getName();
            result += String.format("%n\t%s  %d", goodName, kv.getValue());
        }
        result += "\n  Weapons:";
        for (WeaponType w: getWeaponList()) {
            if (w != null) {
                result += String.format("%n\t%s", w.getName());
            }
        }
        result += "\n  Shields:";
        for (Shield s: getShieldList()) {
            if (s != null) {
                result += String.format("%n\t%s", s.getType().getName());
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

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public double calcWeaponDamage() {
        double damage = 0;
        Random r = new Random();
        double hitChance = r.nextDouble();
        for (WeaponType wt: getWeaponList()) {
            if (wt != null) {
                double acc = wt.getAccuracy();
                if (acc > hitChance) {
                    damage += wt.getDamage() * wt.getRate();
                }
            }
        }
        // You can do some minimal damage without a ship
        if (getWeaponList().size() == 0) {
            damage = 50 * r.nextInt(2);
        }
        return damage;

    }

    public String receiveDamage(double damage) {
        String result = "";
        for (Shield s: getShieldList()) {
            if (s.getHealth() != 0 && damage > 0.01) {
                double newDamage = damage - s.getHealth();
                s.decrementHealth(damage);
                result += String.format("Shield: %s absorbs the blow, %.2f / %.2f health remains.%n",
                                        s.getType().getName(), s.getHealth(), s.maxHealth());
                damage = Math.max(newDamage, 0);
            }
        }
        this.health = Math.max(0, this.health - damage);
        // this.health = this.health - damage;
        if (damage > 0.01) {
            result += String.format("The attack pierces the hull, dealing %.2f damage.%n"
                                    + "Remaining Hull Strength: %.2f / %d%n", damage,
                                    this.health, this.type.getHullStrength());
        }
        this.destroyed = this.health == 0;

        return result;
    }

}
