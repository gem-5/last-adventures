package edu.gatech.gem5.game;

import java.util.Map;

/**
 * Interface to define interactions between classes that can trader w/ the Character
 * @author Sam Blumenthal
 */

public interface Traderable {

    /**
     * Get a map of goods and their prices sold by this trader.
     *
     * @return the map
     */
    public Map<String, Integer> getSupply();

    /**
     * Get a map of goods and their prices bought by this trader.
     *
     * @return the map
     */
    public Map<String, Integer> getDemand();

    /**
     * Get a map of goods and their quantities sold by this trader.
     *
     * @return the map
     */
    public Map<String, Integer> getStock();
}