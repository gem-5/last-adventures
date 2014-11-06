package edu.gatech.gem5.game;

import java.util.Map;
import java.util.Collection;

/**
 * Interface to define interactions between classes that can trader with the Character.
 * @author Sam Blumenthal
 */

public interface Traderable {

    /**
     * Get a map of goods and their prices sold by this trader.
     *
     * @return the map
     */
    Map<String, Integer> getSupply();

    /**
     * Get a map of goods and their prices bought by this trader.
     *
     * @param goods The goods to ask about.
     * @return the map
     */
    Map<String, Integer> getDemand(Collection<String> goods);

    /**
     * Get a map of goods and their quantities sold by this trader.
     *
     * @return the map
     */
    Map<String, Integer> getStock();
}
