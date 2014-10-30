package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShieldType;

/**
 * A class to keep track of dynamic shield data. It includes a ShieldType
 * flyweight for all final data.
 * 
 * @author Jack Mueller
 */
public class Shield {
    /**
     * A shield's type that contains unchanging properties.
     */
    private final ShieldType type;
    /**
     * Remaining hit points of the shield at any time.
     */
    private double remainingIntegrity;

    /**
     * Creates a shield with maximum hit points of the given type.
     * 
     * @param t A set of shield properties
     */
    public Shield(ShieldType t) {
        this.type = t;
        this.remainingIntegrity = t.getIntegrity();
    }

    /**
     * 
     * @return the type of shield, along with relevant properties.
     */
    public ShieldType getType() {
        return type;
    }

    /**
     * 
     * @return the remaining integrity
     */
    public double getHealth() {
        return this.remainingIntegrity;
    }

    /**
     * Deals the given damage to a shield, but any remaining damage after hit
     * points have been reduced to 0 is ignored.
     * 
     * @param damage the damage to be dealt to a shield
     */
    public void decrementHealth(double damage) {
        this.remainingIntegrity -= damage;
        this.remainingIntegrity = Math.max(0, remainingIntegrity);
    }

    /**
     * 
     * @return the maximum possible hit points for any shield of this type.
     */
    public double maxHealth() {
        return this.type.getIntegrity();
    }

}