package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;

/**
 * A class for ship objects owned by players/NPCs.
 *
 * @author  Creston Bunch
 */

public class Ship {
    
    private final ShipType type;
    private double health;
    private Good[] cargoList;
    private Weapon[] weaponList;
    private Shield[] shieldList;
    private Gadget[] gadgetList;
    private Mercenary[] crewList;

    /**
     * Construct the ship from a given ship type.
     *
     * @param ship The ShipBase to copy.
     */
    public Ship(ShipType ship) {
        this.type = ship;
        this.cargoList = new Good[ship.getCargoSlots()];
        this.weaponList = new Weapon[ship.getWeaponSlots()];
        this.shieldList = new Shield[ship.getShieldSlots()];
        this.gadgetList = new Gadget[ship.getGadgetSlots()];
        this.crewList = new Mercenary[ship.getCrewSlots()];
    }
}
