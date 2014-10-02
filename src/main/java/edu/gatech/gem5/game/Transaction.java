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

    /**
     * This transaction is between a player and a planet
     *
     * @param player The player making the transaction.
     * @param planet The planet making the transaction.
     */
    public Transaction(Character player, Planet planet) {
        this.player = player;
        this.planet = planet;
        this.errorMessage = "";
    }

    /**
     * Purchase a number of goods
     *
     * @param purchases A map of goods and their quantities to purchase.
     */
    public void buy(Map<String, Integer> purchases) {
        for (Map.Entry<String, Integer> p : purchases.entrySet()) {
            int unitPrice = planet.getSupply().get(p.getKey());
            int sumTotal = unitPrice * p.getValue();
            // player loses money, gains cargo
            player.setMoney(player.getMoney() - sumTotal);
            player.getShip().addCargo(p.getKey(), p.getValue());
            // planet loses stock
            Map<String, Integer> stock = planet.getStock();
            stock.put(p.getKey(), stock.get(p.getKey()) - p.getValue());
        }
    }

    /**
     * Sell a number of goods
     *
     * @param sales A map of goods and their quantities to sell.
     */
    public void sell(Map<String, Integer> sales) {
        for(Map.Entry<String, Integer> s : sales.entrySet()) {
            int unitPrice = planet.getDemand().get(s.getKey());
            int sumTotal = unitPrice * s.getValue();
            // planet loses money, player gains money
            // TODO: take money from the planet
            player.setMoney(player.getMoney() + sumTotal);
            // player loses cargo
            player.getShip().takeCargo(s.getKey(), s.getValue());
        }
    }

    /**
     * Check if a purchase is possible.
     *
     * @param purchases A map of goods and their quantities to purchase.
     */
    public boolean validateBuy(Map<String, Integer> purchases) {
        int total = 0;
        int count = 0;
        for (Map.Entry<String, Integer> p : purchases.entrySet()) {
            int unitPrice = planet.getSupply().get(p.getKey());
            total += p.getValue() * unitPrice;
            count += p.getValue();
            // planet does not have enough stock
            if (p.getValue() > planet.getStock().get(p.getKey())) {
                errorMessage = "The planet does not have that much in stock.";
                return false;
            }
        }
        // player does not have enough money
        if (player.getMoney() < total) {
            errorMessage = "You are " + (total - player.getMoney())
                + " credits short "
                + "for this transaction.";
            return false;
        }
        // player does not have enough cargo slots
        if (count > player.getShip().getOpenBays()) {
            errorMessage = "You need "
                + (count - player.getShip().getOpenBays())
                + " more cargo bays.";
            return false;
        }
        return true;
    }

    /**
     * Check if the sale is possible.
     *
     * @param sales A map of goods and their quantities to sell.
     */
    public boolean validateSell(Map<String, Integer> sales) {
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
