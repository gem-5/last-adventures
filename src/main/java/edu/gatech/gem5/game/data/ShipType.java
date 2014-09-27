package edu.gatech.gem5.game.data;

/**
 * A container class for data of the various ship types.
 *
 * @author Sam Blumenthal
 * @author Creston Bunch
 */
public class ShipType extends DataType {

    // List of all parameters given by the table in Project Description
    private String name;
    private String description;
    private int cargoBay;
    private int weaponSlots;
    private int shieldSlots;
    private int gadgetSlots;
    private int crewSlots;
    private int range;
    private int fuelCost;
    private int price;
    private int bounty;
    private int hullStrength;

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
        return this.crewSlots;
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
     * Prints out a string representation of a Ship
     * @return a string representing the Ship
     */
    public String toString() {
        // eventually maybe also add in other Ship info for debugging purposes
        return this.name;
    }
}
