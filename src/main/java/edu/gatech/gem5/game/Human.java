package edu.gatech.gem5.game;

/**
 * Super class for Humans in Last Adventures
 * @author Sam Blumenthal
 */
public abstract class Human {


    private String name;
    // Skills
    private int pilot;
    private int fighter;
    private int trader;
    private int engineer;
    private int investor;
    // Ship
    private Ship ship;
    // Money
    private int money;


    protected Human(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship, int money) {
        this.name = name;
        this.pilot = pilot;
        this.fighter = fighter;
        this.trader = trader;
        this.engineer = engineer;
        this.investor = investor;
        this.ship = ship;
        this.money = money;

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

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void incMoney(int amount) {
        this.money += amount;
    }

    public void decMoney(int amount) {
        this.money -= amount;
    }

    public int getNetWorth() {
        int worth = money;
        if (ship != null)
            worth += ship.getNetWorth();
        return worth;
    }
    /**
     * @return A String representation of the Human.
     */
    public String toString() {
        if (this.ship == null) {
            return String.format("Character name: %s%nSkills:%n\tPilot\t\t%d%n\tFighter\t%d%n\tTrader\t%d%n\tEngineer\t%d%n\tInvestor\t%d%nShip: None%nNet Worth: %d",
                                 name, pilot, fighter, trader, engineer, investor, this.getNetWorth());
        } else {
            return String.format("Character name: %s%nSkills:%n\tPilot\t\t%d%n\tFighter\t%d%n\tTrader\t%d%n\tEngineer\t%d%n\tInvestor\t%d%n%s%nNet Worth: %d",
                                 name, pilot, fighter, trader, engineer, investor, ship.toString(), this.getNetWorth());
        }

    }

    public double getOffensiveMultiplier() {
        return this.fighter / 50.0;
    }

    public String attackShip(Ship s) {
        String result = "";
        double damage = this.ship.calcWeaponDamage();
        // if (damage < 50) {
        //     damage = 50;
        // }
        damage *= getOffensiveMultiplier();
        if (damage < 0.01) {
            result = "The attack misses the ship.\n";
        } else {
            result = s.receiveDamage(damage);
        }

        return result;

    }
}
