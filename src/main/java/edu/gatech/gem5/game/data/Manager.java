package edu.gatech.gem5.game.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper class for data maps that are globally acessible in the game.
 *
 * @author Jack Mueller
 * @author Creston Bunch
 */
public class Manager {
    
    private Map<String, Map> dataSets;
    
    /**
     * Construct the manager with no data sets.
     */
    public Manager() {
        dataSets = new HashMap<>();
    }

    /**
     * Add a data set.
     *
     * @param key The key to use.
     * @param data The map to use.
     */
    public void add(String key, Map data) {
        dataSets.put(key, data);
    }
       
    /**
     * 
     * @param key the key of the thing you want information about
     * @return The data set
     */
    public Map get(String key) {
        return dataSets.get(key);
    }
    
}
