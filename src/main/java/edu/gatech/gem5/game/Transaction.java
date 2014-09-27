package edu.gatech.gem5.game;

import java.util.Map;
import java.util.Stack;

import edu.gatech.gem5.game.data.GoodType;

/**
 * This class handles all exchanges between the player character and a planet's
 * stock. This includes goods, ships, weapons, gadgets, and shields.
 * 
 * @author Jack Mueller
 */
public class Transaction {
    
    public Character player;
    //the planet that this character is doing business on
    Planet planet;
    
    public Transaction() {
        SaveFile saveFile = LastAdventures.getCurrentSaveFile();
        player = saveFile.getCharacter();
        planet = saveFile.getPlanet();
    }
    
    /**
     * 
     * @param quantity the number of good involved in this transaction.
     * This should always be a valid number that will not exceed the capacity of
     * the cargo hold
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     */
    public void buy(int quantity, String good) {
        //player loses money, gains cargo
        int money = player.getMoney();
        int price = planet.getDemand().get(good);
        player.setMoney(money - quantity * price);
        Ship ship = player.getShip();
        Good[] cargo = ship.getCargoList();
        Map<String, GoodType> goodInfo = LastAdventures.data.get(GoodType.KEY);
        Stack<Integer> open = ship.getOpenBays();
        for (int i = 0; i < quantity; i++) {
            cargo[open.pop()] = new Good(goodInfo.get(good));
        }
        //don't know if this line is necessary
        ship.setOpenBays(open);
        ship.setCargoList(cargo);
    }
    
    /**
     * 
     * @param quantity the number of good involved in this transaction.
     * This should always be a valid number that will not exceed the capacity of
     * the cargo hold
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     * @return A string describing the validity the transaction.
     */
    public String validateBuy(int quantity, String good) {
        int money = player.getMoney();
        int price = planet.getDemand().get(good);
        if (money - quantity * price < 0) {
            return "You are " + (quantity * price - money) + " credits short " +
                    "for this transaction.";
        }
        Ship ship = player.getShip();
        Stack<Integer> open = ship.getOpenBays();
        if (open.size() < quantity) {
            return "You need " + (quantity - open.size()) + " more cargo bays "+
                    "to hold this much " + good + ".";
        }
        
        //transaction is valid, confirm with question
        return "Are you sure you want to buy " + quantity + " cargo bay" + 
                (quantity == 1 ? "" : "s") + " of " + good + " for " +
                (quantity * price) + " credits?";
    }
    
    /**
     * 
     * @param quantity the number of good involved in this transaction.
     * This should always be a valid number that will not exceed the number
     * the player has of this good
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     */
    public void sell(int quantity, String good) {
        //player gains money, loses cargo
        int money = player.getMoney();
        int value = planet.getSupply().get(good);
        player.setMoney(money - quantity * value);
        Ship ship = player.getShip();
        Good[] cargo = ship.getCargoList();
        Map<String, GoodType> goodInfo = LastAdventures.data.get(GoodType.KEY);
        String goodName = goodInfo.get(good).getName();
        Stack<Integer> open = ship.getOpenBays();
        int i = 0, j = 0;
        while (i < quantity) {
            if (goodName.equals(good)) {
                cargo[j] = null;
                open.push(j);
                i++;
            }
            j++;
        }
        //don't know if this line is necessary
        ship.setOpenBays(open);
        ship.setCargoList(cargo);
    }
    
    /**
     * 
     * @param quantity the number of good involved in this transaction.
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     * @return A string describing the validity the transaction.
     */
    public String validateSell(int quantity, String good) {
        //int wealth get the planets wealth here
        int value = planet.getSupply().get(good);
        
        //TODO
        //check if the planet has enough wealth to buy these goods from you here
        
        Ship ship = player.getShip();
        Good[] cargo = ship.getCargoList();
        Map<String, GoodType> goodInfo = LastAdventures.data.get(GoodType.KEY);
        Stack<Integer> open = ship.getOpenBays();
        //loop through cargo
        int numOfGood = 0, cargoSlot = 0;
        while (cargoSlot < cargo.length) {
            if (cargo[cargoSlot].getType().getName().equals(good)) {
                numOfGood++;
            }
            cargoSlot++;
        }
        if (numOfGood < quantity) {
            return "You need " + (quantity - numOfGood) + " more bays of " +
                    good + " to sell this many.";
        }
        
        //transaction is valid, confirm with question
        return "Are you sure you want to well " + quantity + " cargo bay" + 
                (quantity == 1 ? "" : "s") + " of " + good + " for " +
                (quantity * value) + " credits?";
    }
}
