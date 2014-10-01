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
        for (int i = 0; i < quantity.length; i++) {
            for (int j = 0; j < quantity[i]; j++) {
                cargo[open.pop()] = new Good((GoodType)LastAdventures.data.
                        get("good").get(supply.keySet().toArray()[i]));
            }
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
    public void sell(int quantity[]) {
        //player gains money, loses cargo
        int money = player.getMoney();
        Map<String, Integer> demand = planet.getDemand();
        Object[] prices = demand.values().toArray();
        int total = 0;
        for (int i = 0; i < quantity.length; i++) {
            for (int j = 0; j < quantity[i]; j++)
                total += (Integer)prices[i];
        }
        player.setMoney(money + total);
        Ship ship = player.getShip();
        Good[] cargo = ship.getCargoList();
        Map<String, GoodType> goodInfo = LastAdventures.data.get(GoodType.KEY);
        Stack<Integer> open = ship.getOpenBays();
        //TODO this triple loop is silly, let's implement the cargo as a map
        //exactly like how getCargoCounts works, not as an array. However,
        //getCargoCounts should still be the same, it should just make a new
        //Map<GoodType, Integer> from the ship's Map<Good, Integer>
        for (int i = 0; i < quantity.length; i++) {
            for (int j = 0; j < quantity[i];) {
                int k = 0;
                while (j < quantity[i] && k < ship.getCargoList().length) {
                    if( ship.getCargoList()[k]!= null && ship.getCargoList()[k].getType().getKey().equals(demand.keySet().toArray()[i])) {
                        cargo[open.push(k)] = null;
                        j++;
                    }
                        
                k++;
                
                }
            }
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
        
        
        //TODO
        //check if the planet has enough wealth to buy these goods from you here
        
        
        
        
        
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
