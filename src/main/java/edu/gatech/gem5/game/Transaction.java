package edu.gatech.gem5.game;

import java.util.Map;
import java.util.Stack;

import edu.gatech.gem5.game.data.GoodType;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * This class handles all exchanges between the player character and a planet's
 * stock. This includes goods, ships, weapons, gadgets, and shields.
 * 
 * @author Jack Mueller
 */
public class Transaction {
    
    private Character player;
    //the planet that this character is doing business on
    private Planet planet;
    private String errorMessage;
    
    public Transaction() {
        SaveFile saveFile = LastAdventures.getCurrentSaveFile();
        player = saveFile.getCharacter();
        planet = saveFile.getPlanet();
        errorMessage = "";
    }
    
    /**
     * 
     * @param quantity the number of good involved in this transaction.
     * This should always be a valid number that will not exceed the capacity of
     * the cargo hold
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     */
    public void buy(int[] quantity) {
        //player loses money, gains cargo
        int money = player.getMoney();
        Map<String, Integer> supply = planet.getSupply();
        Object[] prices = supply.values().toArray();
        int total = 0;
        for (int i = 0; i < quantity.length; i++) {
            for (int j = 0; j < quantity[i]; j++)
                total += (Integer)prices[i];
        }
        System.out.println("and the total is: " + total);
        player.setMoney(money - total);
        Ship ship = player.getShip();
        Good[] cargo = ship.getCargoList();
        Map<String, GoodType> goodInfo = LastAdventures.data.get(GoodType.KEY);
        Stack<Integer> open = ship.getOpenBays();
        for (int i = 0; i < quantity.length; i++)    
            for (int j = 0; j < quantity[i]; j++) {
                cargo[open.pop()] = new Good((GoodType)LastAdventures.data.
                        get("good").get(supply.keySet().toArray()[i]));
            }
            
        //don't know if this line is necessary
        ship.setOpenBays(open);
        ship.setCargoList(cargo);
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
        int value = planet.getDemand().get(good);
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
     * This should always be a valid number that will not exceed the capacity of
     * the cargo hold
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     * @return A string describing the validity the transaction.
     */
    public boolean validateBuy(int[] quantity) {
        int money = player.getMoney();
        Object[] prices = planet.getSupply().values().toArray();
        int total = 0;
        for (int i = 0; i < quantity.length; i++) {
            for (int j = 0; j < quantity[i]; j++)
                total += (Integer)prices[i];
        }
        
        if (money - total < 0) {
            errorMessage = "You are " + (total - money) + " credits short " +
                    "for this transaction.";
            return false;
        }
        Ship ship = player.getShip();
        Stack<Integer> open = ship.getOpenBays();
        int totalItems = IntStream.of(quantity).sum();
        if (open.size() < totalItems) {
            errorMessage = "You need " + (totalItems - open.size()) + 
                    " more cargo bays.";
            return false;
        }
        
        //transaction is valid
        return true;
    }
    
    /**
     * 
     * @param quantity the number of good involved in this transaction.
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     * @return A string describing the validity the transaction.
     */
    public boolean validateSell(int[] quantity) {
        //int wealth get the planets wealth here
        Object[] values = planet.getDemand().values().toArray();
        
        //TODO
        //check if the planet has enough wealth to buy these goods from you here
        
        Ship ship = player.getShip();
        Good[] cargo = ship.getCargoList();
        Map<String, GoodType> goodInfo = LastAdventures.data.get(GoodType.KEY);
        Stack<Integer> open = ship.getOpenBays();
        //loop through cargo
        int numOfGood = 0, cargoSlot = 0;
        Map<GoodType, Integer> cargoCounts = player.getShip().getCargoCounts();
        Map<String, Integer> missing = new TreeMap<>();
        String[] keys = (String[])cargoCounts.keySet().toArray();
        
        for (int i = 0; i < quantity.length; i++) {
            int surplus = cargoCounts.get(LastAdventures.data.get("good").get(keys[i])) - quantity[i];
            if(surplus < 0) {
                missing.put(keys[i], surplus * -1); //missing this many of this good
            }
        }
        
        int totalItems = IntStream.of(quantity).sum();
        if (numOfGood < totalItems) {
            errorMessage = "You need " + (totalItems - numOfGood) + " more bays.";// of " +
                    //"xxx" + " to sell this many.";
            return false;
        }
        
        //transaction is valid
        return true;
    }
    
    
    /**
     * @return the last error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
