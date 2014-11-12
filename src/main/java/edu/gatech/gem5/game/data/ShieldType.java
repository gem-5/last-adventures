package edu.gatech.gem5.game.data;

/**
 * A container class for data of the various shield types.
 *
 * @author Creston Bunch
 */

public class ShieldType extends AbstractDataType {

    /**
     * The Key used for accessing info of the ShieldType.
     */
    private String key;
    /**
     * The name of the ShieldType.
     */
    private String name = "";
    /**
     * The description of the ShieldType.
     */
    private String description = "";
    /**
     * The price of the ShieldType.
     */
    private int price;
    /**
     * The defense factor of the ShieldType.
     */
    private double factor;
    /**
     * The integrity of the ShieldType.
     */
    private int integrity;

    /**
     * The Key of the ShieldType, used for accessing ShieldType information.
     */
    public static final String KEY = "shield";

    /**
     * Get the key of this shield type.
     * @return the key
     */
    public String getKey() {
        return this.key;
    }
    /**
     * Return the name of this shield type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the description of this shield type.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Return the price of this shield type.
     *
     * @return the price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Return the defense factor of this shield type.
     *
     * @return the defense factor
     */
    public double getFactor() {
        return this.factor;
    }

    /**
     * Return the integrity of this shield type.
     *
     * @return the integrity
     */
    public int getIntegrity() {
        return this.integrity;
    }

}
