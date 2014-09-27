package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;
import java.util.Stack;

/**
 * A class for ship objects owned by players/NPCs.
 *
 * @author  Creston Bunch
 * @author Jack Mueller
 */

public class Ship {
    
    private final ShipType type;
    private double health;
    private Good[] cargoList;
    private Stack<Integer> openBays;
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
        this.openBays = new Stack<>();
        for (int i = 0; i < cargoList.length; i++) {
            openBays.push(i); // all bays are open at the beginning
        }
        this.weaponList = new Weapon[ship.getWeaponSlots()];
        this.shieldList = new Shield[ship.getShieldSlots()];
        this.gadgetList = new Gadget[ship.getGadgetSlots()];
        this.crewList = new Mercenary[ship.getCrewSlots()];
    }

    /**
     * @return the type of ship.
     */
    public ShipType getType() {
        return this.type;
    }

    /**
     * @return the cargoList
     */
    public Good[] getCargoList() {
        return cargoList;
    }

    /**
     * @param cargoList the cargoList to set
     */
    public void setCargoList(Good[] cargoList) {
        this.cargoList = cargoList;
    }

    /**
     * OpenBays is sent to Transaction class to tell it which bays to put 
     * the cargo in.
     * @return the open bays
     */
    public Stack<Integer> getOpenBays() {
        return openBays;
    }
    
    /**
     * OpenBays is received from Transaction class once it has filled some bays.
     * @param openBays the open bays
     */
    public void setOpenBays(Stack<Integer> openBays) {
        this.openBays = openBays;
    }
    
    /**
     * @param bay the bay to open
     */
    public void openNewBay(int bay) {
        openBays.push(bay);
    }
}
