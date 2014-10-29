package edu.gatech.gem5.game;

/**
 * Class implementation of Player Character in Last Adventures.
 * @author Sam Blumenthal
 */
public class Character extends Human {

    /**
     * Constructor for character with a ship, starts with 1000 credits.
     * 
     * @param name Character's name
     * @param pilot Character's pilot skill
     * @param fighter Character's fighter skill
     * @param trader Character's trader skill
     * @param engineer Character's engineer skill
     * @param investor Character's investor skill
     * @param ship Character's ship
     */
    public Character(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship) {
        super(name, pilot, fighter, trader, engineer, investor, ship, 1000);
    }

    @Override
    public String toString() {
        return "*PLAYER CHARACTER*\n" + super.toString();
    }

    /**
     * 
     * @return true if refuel was successful, false if not enough money
     */
    public boolean refuel() {
        Ship s = getShip();
        int costOfFuel = (s.getType().getRange() 
                - s.getFuel()) * s.getType().getFuelCost();
        //make sure you have enough money to refuel
        if (getMoney() - costOfFuel > 0) {
            setMoney(getMoney() - costOfFuel);
            s.setFuel(s.getType().getRange());
            return true;
        }
        return false;
    }
}
