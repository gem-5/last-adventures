package edu.gatech.gem5.game.data;

/**
 * A container class for data of the various shield types.
 *
 * @author Creston Bunch
 */

public class ShieldType extends DataType {

    private String name = "";
    private String description = "";
    private int price;
    private double factor;
    private int integrity;

    public static final String KEY = "shield";

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
