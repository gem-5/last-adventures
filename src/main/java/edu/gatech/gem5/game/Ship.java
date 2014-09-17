package edu.gatech.gem5.game;
/**
 * Super class for Ships in Last Adventures
 * @author Sam Blumenthal
 */
public class Ship{

    // List of all parameters given by the table in Project Description
    private String name;
    private int cargoBay;
    private int weaponSlots;
    private int shieldSlots;
    private int gadgetSlots;
    private int crew;
    private int fuelMax;
    private int currentFuel;
    private int minTechLevel;
    private int fuelCost;
    private int price;
    private int bounty;
    // private int occurrence;
    private int hullStrength;
    private String manufacturer;
    private double valueFactor;
    private double attackFactor;
    private double bestFactor;
    private double balance;

    // // Lists for storing cargo & ship upgrades, limited by ship parameters
    // private List<Cargo>  cargoList;
    // private List<Weapon> weaponList;
    // private List<Shield> shieldList;
    // private List<Gadget> gadgetList;

    protected Ship(String name, int cargo, int weaponSlots, int shieldSlots, int gadgetSlots,
                   int crew, int fuelMax, int minTechLevel, int fuelCost, int price, int bounty,
                   int hullStrength, String manufacturer) {
        this.name = name;
        this.cargoBay = cargo;
        this.weaponSlots = weaponSlots;
        this.shieldSlots = shieldSlots;
        this.gadgetSlots = gadgetSlots;
        this.crew = crew;
        this.fuelMax = fuelMax;
        this.currentFuel = fuelMax; // The ship starts with full fuel
        this.minTechLevel = minTechLevel;
        this.fuelCost = fuelCost;
        this.price = price;
        this.bounty = bounty;
        // this.occurrence = occurrence;
        this.hullStrength = hullStrength;
        // this.police = police;
        // this.pirate = pirate;
        // this.trader = trader;
        // this.repairCost = repairCost;
        // this.size = size;
        this.manufacturer = manufacturer;
        this.valueFactor = price / (100.0 * cargo + 5 * gadgetSlots * fuelCost + crew * 1000);
        this.attackFactor = price / (100.0 * weaponSlots + 1000.0 * gadgetSlots + hullStrength * 10 + crew * 1000);
        this.bestFactor = Math.min(this.attackFactor, this.valueFactor);

        // this.cargoList = new ArrayList<>(cargoBay);
        // this.weaponList = new ArrayList<>(weaponSlots);
        // this.shieldList = new ArrayList<>(shieldSlots);
        // this.gadgetList = new ArrayList<>(gadgetSlots);
    }
}
