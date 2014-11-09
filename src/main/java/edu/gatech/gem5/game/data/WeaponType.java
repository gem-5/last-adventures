package edu.gatech.gem5.game.data;

/**
 * A container class for data of the various weapon types.
 *
 * @author Creston Bunch
 */

public class WeaponType extends DataType {

    /**
     * The WeaponType's key, used for identification.
     */
    private String key;
    /**
     * The WeaponType's name, used for human identification.
     */
    private String name;
    /**
     * The WeaponType's description, contains info about the WeaponType.
     */
    private String description;
    /**
     * The WeaponType's price, used for buying the WeaponType.
     */
    private int price;
    /**
     * The WeaponType's damage, used for damaging enemy ships.
     */
    private int damage;
    /**
     * The WeaponType's accuracy, used for determining the accuracy of attacks.
     */
    private double accuracy;
    /**
     * The WeaponType's rate, used for determining the rate of fire.
     */
    private double rate;

    /**
     * String key denoting the type of WeaponType.
     * Used for accessing WeaponType information
     */
    public static final String KEY = "weapon";

    /**
     * Get the key of this weapon.
     * @return the key
     */
    public String getKey() {
        return this.key;
    }
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
