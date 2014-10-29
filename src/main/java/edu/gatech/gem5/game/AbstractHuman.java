package edu.gatech.gem5.game;

/**
 * Super class for Humans in Last Adventures.
 *
 * @author Sam Blumenthal
 */
public abstract class AbstractHuman {

    /**
     * Human's name.
     */
    private String name;
    // Skills
    /**
     * Pilot skill, affects chance of fleeing battles, and chasing fleeing
     * opponents.
     */
    private int pilot;
    /**
     * Fighter skill, affects offensive power.
     */
    private int fighter;
    /**
     * Trader skill, affects prices of buying/selling goods.
     */
    private int trader;
    /**
     * Engineer skill, affects ship defense.
     */
    private int engineer;
    /**
     * Investor skill, affects stock market.
     */
    private int investor;
    /**
     * Currently owned ship, contains upgrades and goods, properties based on
     * type.
     */
    private Ship ship;
    /**
     * Amount of money (credits) this human has.
     */
    private int money;

    /**
     *
     * @param n Human's name
     * @param pilotSkill pilot skill
     * @param fighterSkill fighter skill
     * @param traderSkill trader skill
     * @param engineerSkill engineer skill
     * @param investorSkill investor skill
     * @param s ship to be owned by this Human
     * @param m amount of money this Human owns
     */
    protected AbstractHuman(String n, int pilotSkill, int fighterSkill, 
            int traderSkill, int engineerSkill, int investorSkill, Ship s,
            int m) {
        this.name = n;
        this.pilot = pilotSkill;
        this.fighter = fighterSkill;
        this.trader = traderSkill;
        this.engineer = engineerSkill;
        this.investor = investorSkill;
        this.ship = s;
        this.money = m;

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
     * @param s The new ship for the Human
     */
    public void setShip(Ship s) {
        this.ship = s;
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
     * 
     * @return Human's owned money
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * 
     * @param m Human's new amount of money
     */
    public void setMoney(int m) {
        this.money = m;
    }

    /**
     * 
     * @param amount amount to increase Human's money by
     */
    public void incMoney(int amount) {
        this.money += amount;
    }

    /**
     * 
     * @param amount amount to decrease Human's money by
     */
    public void decMoney(int amount) {
        this.money -= amount;
    }

    /**
     * 
     * @return an amount of credits based on Human's money, and the value of
     * the contents of the Human's ship
     */
    public int getNetWorth() {
        int worth = money;
        if (ship != null) {
            worth += ship.getNetWorth();
        }
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

    /**
     * 
     * @param s The ship to attack
     * @return A message describing the result of the attack
     */
    public String attackShip(Ship s) {
        String result = "";
        double damage = this.ship.calcWeaponDamage();
        damage *= this.fighter / 50.0;
        if (damage < 0.01) {
            result = "The attack misses the ship.\n";
        } else {
            result = s.receiveDamage(damage);
        }
        return result;
    }
}
