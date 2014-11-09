package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.GadgetType;
import edu.gatech.gem5.game.data.ShieldType;
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

    /**
     * The Ship flyweight that determines certain final properties of each ship.
     */
    private final ShipType type;
    /**
     * Amount of remaining hull strength a ship has.
     */
    private double health;
    /**
     * Amount of remaining fuel a ship has.
     */
    private int fuel;

    /**
     * A map of Good keys to the number of that good carried by a ship.
     */
    private Map<String, Integer> cargoList;
    /**
     * A list of objects that hold properties of weapons that the ship can use.
     */
    private List<WeaponType> weaponList;
    /**
     * A list of shield objects that the ship can use.
     */
    private List<Shield> shieldList;
    /**
     * A list of objects that hold properties of gadgets that the ship can use.
     */
    private List<GadgetType> gadgetList;

    /**
     * An array of mercenaries that can override the ship captain's (the
     * player's) skill points for their calculations.
     */
    private Mercenary[] crewList;

    /**
     * Construct the ship from a given ship type.
     *
     * @param t The ShipType to use
     */
    public Ship(ShipType t) {
        this.type = t;
        this.fuel = t.getRange(); // range == max fuel
        this.cargoList = new TreeMap<>();
        this.weaponList = new ArrayList<>(t.getWeaponSlots());
        this.shieldList = new ArrayList<>(t.getShieldSlots());
        this.gadgetList = new ArrayList<>(t.getGadgetSlots());
        this.crewList = new Mercenary[t.getCrewSlots()];
        this.health = (double) t.getHullStrength();
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
     * Add cargo to this ship.
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
     * Take cargo from this ship.
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
     * @return the number of slots available
     */
    public int getOpenBays() {
        int sum = 0;
        for (int i : cargoList.values()) {
            sum += i;
        }
        return getType().getCargoSlots() - sum;
    }

    /**
     * Method to find the net worth of a ship, takes into account ship price and all
     * wep/shield/gadget prices.
     * @return the net worth of the ship
     */
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

        for (String s: getCargoList().keySet()) {
            GoodType cargo = Data.GOODS.get(s);
            worth += cargo.getValue();
        }

        return worth;
    }

    /**
     *
     * @return A list of objects that hold properties of weapons that the ship
     * can use
     */
    public List<WeaponType> getWeaponList() {
        return this.weaponList;
    }
    /**
     * Adds a weapon to the ship's weapon list.
     * @param wt the Weapon Type being added to the ship
     * @return true if successfully added, false otherwise
     */
    public boolean addUpgrade(WeaponType wt) {
        if (weaponList.size() < type.getWeaponSlots()) {
            return weaponList.add(wt);
        }
        return false;
    }

    /**
     * Method to find the number of available slots for new weapons.
     * @return the number of free weapon slots
     */
    public int getOpenWeaponSlots() {
        return type.getWeaponSlots() - weaponList.size();
    }

    /**
     *
     * @return A list of shield objects that the ship can use
     */
    public List<Shield> getShieldList() {
        return this.shieldList;
    }

    /**
     * Adds a shield to the ship's shield list.
     * @param s the Shield being added to the ship
     * @return true if successfully added, false otherwise
     */
    public boolean addUpgrade(ShieldType st) {
        if (shieldList.size() < type.getShieldSlots()) {
            return shieldList.add(new Shield (st));
        }
        return false;
    }

    /**
     * Method to find the number of available slots for new shields.
     * @return the number of free shield slots
     */
    public int getOpenShieldSlots() {
        return type.getShieldSlots() - shieldList.size();
    }

    /**
     *
     * @return A list of objects that hold properties of gadgets that the ship
     * can use.
     */
    public List<GadgetType> getGadgetList() {
        return this.gadgetList;
    }
    /**
     * Adds a gadget to the ship's gadget list.
     * @param gt the Gadget Type being added to the ship
     * @return true if successfully added, false otherwise
     */
    public boolean addUpgrade(GadgetType gt) {
        if (gadgetList.size() < type.getGadgetSlots()) {
            return gadgetList.add(gt);
        }
        return false;
    }

    /**
     * Method to find the number of available slots for new gadgets.
     * @return the number of free gadget slots
     */
    public int getOpenGadgetSlots() {
        return type.getGadgetSlots() - shieldList.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Ship: ");
        result.append(this.type.getName());
        result.append("\n Cargo:");
        String delim = "%n\t%s";
        for (Map.Entry<String, Integer> kv: this.cargoList.entrySet()) {
            String goodName =
                Data.GOODS.get(kv.getKey()).getName();
            result.append(String.format(delim + "  %d", goodName, kv.getValue()));
        }
        result.append("\n Weapons:");
        for (WeaponType w: getWeaponList()) {
            if (w != null) {
                result.append(String.format(delim, w.getName()));
            }
        }
        result.append("\n Shields:");
        for (Shield s: getShieldList()) {
            if (s != null) {
                result.append(String.format(delim, s.getType().getName()));
            }
        }
        result.append("\n Gadjets:");
        for (GadgetType g: getGadgetList()) {
            if (g != null) {
                result.append(String.format(delim, g.getName()));
            }
        }
        return result.toString();
    }

    /**
     *
     * @param f the amount of fuel a ship should now have
     */
    public void setFuel(int f) {
        this.fuel = f;
    }

    /**
     *
     * @return the amount of fuel a ship currently has
     */
    public int getFuel() {
        return this.fuel;
    }

    /**
     *
     * @return true if a ship's health is 0, false otherwise
     */
    public boolean isDestroyed() {
        return health == 0;
    }

    /**
     * TODO: include fighter skill in calculation.
     * @return the amount of damage to do to based on weapons and chance.
     */
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
        // You can do some minimal damage without weapons
        if (getWeaponList().isEmpty()) {
            damage = 50 * r.nextInt(2);
        }
        return damage;
    }

    /**
     *
     * @param damageReceived amount of damage to be dealt to a ship
     * @return A description of the result of a ship after dealing the damage
     */
    public String receiveDamage(double damageReceived) {
        StringBuilder result = new StringBuilder();
        double damage = damageReceived;
        for (Shield s: getShieldList()) {
            if (s.getHealth() != 0 && damage > 0.01) {
                double newDamage = damage - s.getHealth();
                s.decrementHealth(damage);
                result.append(String.format("Shield: %s absorbs the blow, %.2f / %.2f health remains.%n",
                        s.getType().getName(), s.getHealth(), s.maxHealth()));
                damage = Math.max(newDamage, 0);
            }
        }
        this.health = Math.max(0, this.health - damage);
        // this.health = this.health - damage;
        if (damage > 0.01) {
            result.append(String.format("The attack pierces the hull, dealing %.2f damage.%n"
                    + "Remaining Hull Strength: %.2f / %d%n", damage,
                    this.health, this.type.getHullStrength()));
        }
        return result.toString();
    }
}
