package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.WeaponType;

/**
 * A class for weapon objects belonging to a ship.
 *
 * @author Creston Bunch
 */

public class Weapon {

    private final WeaponType type;

    /**
     * Construct the weapon from a weapon type.
     *
     * @param type The weapon type.
     */
    public Weapon(WeaponType type) {
        this.type = type;
    }

    public int getWorth() {
        return this.type.getPrice();
    }

}
