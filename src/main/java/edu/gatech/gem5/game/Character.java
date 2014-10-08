package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;
import java.util.Map;

/**
 * Class implementation of Player Character in Last Adventures
 * @author Sam Blumenthal
 */
public class Character extends Human {

    private LastAdventures game;

    public Character(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship) {
        super(name, pilot, fighter, trader, engineer, investor, ship, 100000);
    }

    @Override
    public String toString() {
        return "*PLAYER CHARACTER*\n" + super.toString();
    }


}
