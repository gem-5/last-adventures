package edu.gatech.gem5.game;

import java.util.Map;
import java.util.Stack;

/**
 * This class handles all exchanges between the player character and a planet's
 * stock. This includes goods, ships, weapons, gadgets, and shields.
 * 
 * @author Jack Mueller
 */
public class Transaction {
    
    Character player;
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
        Map goodInfo = LastAdventures.manager.getInfo("good");
        Stack<Integer> open = ship.getOpenBays();
        for (int i = 0; i < quantity; i++) {
            cargo[open.pop()] = (Good) goodInfo.get(good);
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
        int value = planet.getSupply().get(good);
        player.setMoney(money - quantity * value);
        Ship ship = player.getShip();
        Good[] cargo = ship.getCargoList();
        String goodInfo = ((Good) LastAdventures.manager.getInfo("good")
                .get(good)).getType().getName();
        Stack<Integer> open = ship.getOpenBays();
        int i = 0, j = 0;
        while (i < quantity) {
            if (goodInfo.equals(good)) {
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
     * @return A string describing what was wrong with the transaction, or 
     * an Optional object if nothing was wrong.
     */
    public String validateBuy(int quantity, String good) {
        return "";
    }
    
}
