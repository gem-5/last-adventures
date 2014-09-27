package edu.gatech.gem5.game.data;

/**
 * A container class for data of the various good types.
 *
 * @author Creston Bunch
 */

public class GoodType {

    private String name;
    private int value;
    private boolean legal;

    public static final String KEY = "good";

    /**
     * Return the name of this good type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the base value of this good type.
     *
     * @return the base value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Return the legality of this good type.
     *
     * @return true if it is legal, false otherwise.
     */
    public boolean isLegal() {
        return this.legal;
    }

}
