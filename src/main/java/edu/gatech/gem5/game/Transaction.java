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
    
    public void buy(int quantity, String good) {
        exchange(true, quantity, good);
    }
    
    public void sell(int quantity, String good) {
        exchange(false, quantity, good);
    }
    
    /**
     * 
     * @param direction whether or not the player is losing or gaining money
     * @param quantity the number of good involved in this transaction.
     * This should always be a valid number that will not exceed the capacity of
     * the cargo hold
     * @param good the good involved in this transaction. This should always be
     * a good that you can buy at one of the companies of this planet.
     * @return the success of the exchange
     */
    public boolean exchange(boolean direction, int quantity, String good) {
        if(direction) {
            //player loses money, gains cargo
            int money = player.getMoney();
            int price = planet.getDemand().get(good);
            if(money >= quantity * price) {
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
                return true; //transaction was sucessful
            }
            
        }
        else {
            //player gains money, loses cargo
        }
        
        return false;
    }
}
