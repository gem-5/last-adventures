package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;
import java.util.Map;

/**
 * Super class for Humans in Last Adventures
 * @author Sam Blumenthal
 */
public class Human {


    private String name;
    // Skills
    private int pilot;
    private int fighter;
    private int trader;
    private int engineer;
    private int investor;
    // Ship
    private Ship ship;


    public Human(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship) {
        this.name = name;
        this.pilot = pilot;
        this.fighter = fighter;
        this.trader = trader;
        this.engineer = engineer;
        this.investor = investor;
        this.ship = ship;

    }
    /**
     * @return The name of the Human
     */
    public String getName() {
        return this.name;

    }

    /**
     * @return The pilot skill of the Human
     */
    public int getPilot() {
        return this.pilot;
    }
    /**
     * @return The fighter skill of the Human
     */
    public int getFighter() {
        return this.fighter;
    }
    /**
     * @return The trader skill of the Human
     */
    public int getTrader() {
        return this.trader;
    }
    /**
     * @return The engineer skill of the Human
     */
    public int getEngineer() {
        return this.engineer;
    }
    /**
     * @return The investor skill of the Human
     */
    public int getInvestor() {
        return this.investor;
    }
    /**
     * @return The Human's Ship
     */
    public Ship getShip() {
        return this.ship;
    }
    /**
     * @param ship The new ship for the Human
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    /**
     * Increments the Pilot skill by one.
     */
    public void incPilot() {
        this.pilot++;
    }
    /**
     * Decrements the Pilot skill by one.
     */
    public void decPilot() {
        this.pilot--;
    }
    /**
     * Increments the Fighter skill by one.
     */
    public void incFighter() {
        this.fighter++;
    }
    /**
     * Decrements the Fighter skill by one.
     */
    public void decFighter() {
        this.fighter--;
    }
    /**
     * Increments the Trader skill by one.
     */
    public void incTrader() {
        this.trader++;
    }
    /**
     * Decrements the Trader skill by one.
     */
    public void decTrader() {
        this.trader--;
    }
    /**
     * Increments the Engineer skill by one.
     */
    public void incEngineer() {
        this.engineer++;
    }
    /**
     * Decrements the Engineer skill by one.
     */
    public void decEngineer() {
        this.engineer--;
    }
    /**
     * Increments the Investor skill by one.
     */
    public void incInvestor() {
        this.investor++;
    }
    /**
     * Decrements the Investor skill by one.
     */
    public void decInvestor() {
        this.investor--;
    }
    /**
     * @return A String representation of the Human.
     */
    public String toString() {
        if (this.ship == null) {
            return String.format("Character name: %s%nSkills:%n\tPilot\t\t%d%n\tFighter\t\t%d%n\tTrader\t\t%d%n\tEngineer\t%d%n\tInvestor\t%d%nShip: None",
                                 name, pilot, fighter, trader, engineer, investor);
        } else {
            return String.format("Character name: %s%nSkills:%n\tPilot\t\t%d%n\tFighter\t\t%d%n\tTrader\t\t%d%n\tEngineer\t%d%n\tInvestor\t%d%nShip: %s",
                                 name, pilot, fighter, trader, engineer, investor, ship.toString());
        }

    }

    public static void main(String[] args) {
        Map<String, ShipType> ships = LastAdventures.manager.getInfo("ships");
        Human bob = new Human("Bob", 10, 11, 7, 9, 4,
                new Ship(ships.get("vagabond")));
        System.out.println(bob.toString());
    }
}
