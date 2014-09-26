package edu.gatech.gem5.game.data;

/**
 * A container class for data of the various weapon types.
 *
 * @author Creston Bunch
 */

public class WeaponType {

    private String name;
    private String description;
    private int price;
    private int damage;
    private double accuracy;
    private double rate;

    /**
     * Get the name of this weapon.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the description of this weapon.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the price of this weapon.
     *
     * @return the price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Get the base damage of this weapon.
     *
     * @return the base damage
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Get the base accuracy of this weapon.
     *
     * @return the base accuracy
     */
    public double getAccuracy() {
        return this.accuracy;
    }

    /**
     * Get the base rate of this weapon.
     *
     * @return the base rate
     */
    public double getRate() {
        return this.rate;
    }

}
