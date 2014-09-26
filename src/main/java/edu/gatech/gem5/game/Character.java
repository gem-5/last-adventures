package edu.gatech.gem5.game;
/**
 * Class implementation of Player Character in Last Adventures
 * @author Sam Blumenthal
 */
public class Character extends Human {


    public Character(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship) {
        super(name, pilot, fighter, trader, engineer, investor, ship);
    }

    public String toString() {
        return "*PLAYER CHARACTER*\n" + super.toString();
    }
/**
 * Convenience method for creating a new character from string inputs.
 * @param nameString the name of the character
 * @param pilotString the pilot skill of the character
 * @param fighterString the fighter skill of the character
 * @param traderString the trader skill of the character
 * @param engineerString the engineer skill of the character
 * @param investorString the investor skill of the character
 * @return A new Character created from the given parameters.
 */
    public static Character createCharacterFromStrings(String nameString, String pilotString, String fighterString, String traderString, String engineerString, String investorString) {
        int pilot = Integer.parseInt(pilotString);
        int fighter = Integer.parseInt(fighterString);
        int trader = Integer.parseInt(traderString);
        int engineer = Integer.parseInt(engineerString);
        int investor = Integer.parseInt(investorString);
        // Implement this with a Ship constructor once Ship class is created.
        // Ship ship = null;
        Ship ship = new Vagabond();

        return new Character(nameString, pilot, fighter, trader, engineer, investor, ship);
    }
}
