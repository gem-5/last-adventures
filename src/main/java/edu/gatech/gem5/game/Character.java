
public class Character extends Human {

    public Character(String name, int pilot, int fighter, int trader, int engineer, int investor, Ship ship) {
        super(name, pilot, fighter, trader, engineer, investor, ship);
    }

    public String toString() {
        return "*PLAYER CHARACTER*\n" + super.toString();
    }

    public static Character createCharacterFromStrings(String nameString, String pilotString, String traderString, String engineerString, String investorString, String shipString) {
        pilot = Integer.parseInt(pilotString);
        trader = Integer.parseInt(traderString);
        engineer = Integer.parseInt(engineerString);
        investor = Integer.parseInt(investorString);
        // Implement this with a Ship constructor once Ship class is created.
        ship = null;

        return new Character(nameString, pilot, fighter, trader, engineer, investor)
    }
}
