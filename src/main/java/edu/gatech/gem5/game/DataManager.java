package edu.gatech.gem5.game;

import java.util.Map;

/**
 * A class for holding all the global game data.
 *
 * @author  Creston Bunch
 * @version 1.0.0
 */

public class DataManager<T> {


    private final Map<String, T> data;

    /**
     * Construct the data manager with a given type of data.
     *
     * @param data The map of data to use.
     */
    public DataManager(Map<String, T> data) {
        this.data = data;
    }

    /**
     * Return a data in the data map.
     *
     * This is useful, for example, when you need to know information about a
     * government system, tradable, good, or other data that does not require
     * object instances.
     *
     * @param id The id of the data.
     * @return The data.
     */
    public T get(String id) {
        // not implemented
        return null;
    }

    /**
     * Create a copy of a given data object.
     *
     * This is useful, for example, when the player purchases a new ship and you
     * want to clone the base ship stats.
     *
     * @param id The id of the data.
     * @return A cloned data object.
     */
    public T spawn(String id) {
        // not implemented
        return null;
    }


}
