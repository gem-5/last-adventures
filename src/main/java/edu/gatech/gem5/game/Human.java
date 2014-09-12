
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


    public Player(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship) {
        this.name = name;
        this.pilot = pilot;
        this.fighter = fighter;
        this.trader = trader;
        this.engineer = engineer;
        this.investory = investor;
        this.ship = ship;

    }

    public String getName() {
        return this.name;

    }


    public int getPilot() {
        return this.pilot;
    }

    public int getFighter() {
        return this.fighter;
    }

    public int getTrader() {
        return this.trader;
    }

    public int getEngineer() {
        return this.engineer;
    }

    public int getInvestor() {
        return this.investor;
    }

    public Ship getShip() {
        return this.ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void incPilot() {
        this.pilot++;
    }

    public void decPilot() {
        this.pilot--;
    }

    public void incFighter() {
        this.fighter++;
    }

    public void decFighter() {
        this.fighter--;
    }

    public void incTrader() {
        this.trader++;
    }

    public void decTrader() {
        this.trader--;
    }

    public void incEngineer() {
        this.engineer++;
    }

    public void decEngineer() {
        this.engineer--;
    }

    public void incInvestor() {
        this.investor++;
    }

    public void decInvestor() {
        this.investor--;
    }

    public String toString() {
        if (this.ship == null) {
            return String.format("Character name: %s%nSkills:%n%tPilot%t%d%n%tFighter%t%d%n%tTrader%t%d%n%tEngineer%t%d%n%tInvestor%t%d%nShip: None",
                                 name, pilot, fighter, trader, engineer, investor);
        } else {
            return String.format("Character name: %s%nSkills:%n%tPilot%t%d%n%tFighter%t%d%n%tTrader%t%d%n%tEngineer%t%d%n%tInvestor%t%d%nShip: %s",
                                 name, pilot, fighter, trader, engineer, investor, ship.toString());
        }

    }
}
