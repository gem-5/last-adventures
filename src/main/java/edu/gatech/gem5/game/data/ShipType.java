package edu.gatech.gem5.game.data;

/**
 * A container class for data of the various ship types.
 *
 * @author Sam Blumenthal
 * @author Creston Bunch
 */
public class ShipType extends DataType {

    // List of all parameters given by the table in Project Description
    /**
     * The name of the ShipType.
     */
    private String name;
    /**
     * The ship description.
     */
    private String description;
    /**
     * The number of cargo slots of the ShipType.
     */
    private int cargoBay;
    /**
     * The number of weapon slots of the ShipType.
     */
    private int weaponSlots;
    /**
     * The number of shield slots of the ShipType.
     */
    private int shieldSlots;
    /**
     * The number of gadget slots of the ShipType.
     */
    private int gadgetSlots;
    /**
     * The number of crew slots of the ShipType.
     */
    private int crew;
    /**
     * The maximum range of the ShipType.
     */
    private int range;
    /**
     * The cost of fuel of the ShipType.
     */
    private int fuelCost;
    /**
     * The price of the ShipType.
     */
    private int price;
    /**
     * The bounty of the ShipType.
     */
    private int bounty;
    /**
     * The hullstrength of the ShipType.
     */
    private int hullStrength;

    /**
     * The key of the ShipType, used for accessing ShipTypes.
     */
    public static final String KEY = "ship";

    /**
     * Return the name of this ship type.
     *
     * @return The name of this ship.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the description of this ship type.
     *
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Return the number of cargo slots in this ship type.
     *
     * @return the number of cargo slots
     */
    public int getCargoSlots() {
        return this.cargoBay;
    }

    /**
     * Return the number of weapon slots in this ship type.
     *
     * @return the number of weapon slots
     */
    public int getWeaponSlots() {
        return this.weaponSlots;
    }

    /**
     * Return the number of shield slots in this ship type.
     *
     * @return the number of shield slots
     */
    public int getShieldSlots() {
        return this.shieldSlots;
    }

    /**
     * Return the number of gadget slots in this ship type.
     *
     * @return the number of gadget slots
     */
    public int getGadgetSlots() {
        return this.gadgetSlots;
    }
    /**
     * Return the number of crew slots in this ship type.
     *
     * @return the number of crew slots
     */
    public int getCrewSlots() {
        return this.crew;
    }

    /**
     * Return the range of this ship type.
     *
     * @return the maximum range
     */
    public int getRange() {
        return this.range;
    }

    /**
     * Return the price of fuel for this ship type.
     *
     * @return the fuel cost
     */
    public int getFuelCost() {
        return this.fuelCost;
    }

    /**
     * Return the base price of this ship type.
     *
     * @return the price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Return the base bounty for destroying this ship type.
     *
     * @return the bounty
     */
    public int getBounty() {
        return this.bounty;
    }

    /**
     * Return the hull strength of this ship type.
     *
     * @return the hull strength
     */
    public int getHullStrength() {
        return this.hullStrength;
    }

    /**
     * Prints out a string representation of a Ship.
     * @return a string representing the Ship
     */
    public String toString() {
        // eventually maybe also add in other Ship info for debugging purposes
        return this.name;
    }
}
