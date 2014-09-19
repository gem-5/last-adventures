package edu.gatech.gem5.game;
/**
 * Super class for Ships in Last Adventures
 * @author Sam Blumenthal
 */
public class Ship{

    // List of all parameters given by the table in Project Description
    protected String name;
    protected int cargoBay;
    protected int weaponSlots;
    protected int shieldSlots;
    protected int gadgetSlots;
    protected int crew;
    protected int fuelMax;
    protected int currentFuel;
    protected int minTechLevel;
    protected int fuelCost;
    protected int price;
    protected int bounty;
    protected int hullStrength;
    protected String manufacturer;
    protected double valueFactor;
    protected double attackFactor;
    protected double bestFactor;
    protected double balance;

    // // Lists for storing cargo & ship upgrades, limited by ship parameters
    // protected List<Cargo>  cargoList;
    // protected List<Weapon> weaponList;
    // protected List<Shield> shieldList;
    // protected List<Gadget> gadgetList;

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
        this.hullStrength = hullStrength;
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
