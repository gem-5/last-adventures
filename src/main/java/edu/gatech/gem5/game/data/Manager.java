package edu.gatech.gem5.game.data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class Manager {
    
    Map<String, Map> dataSets;
    
    public Manager() {
        dataSets = new HashMap<>();
    }
    
    public void manage(String name, Map dataSet) {
        dataSets.put(name, dataSet);
    }
    
    public void manageAll(String[] dataNames , Map[] data ) {
        for (int i = 0; i < data.length; i++) {
            manage(dataNames[i], data[i]);
        }
    }
    
    public Map getInfo(String name) {
        return dataSets.get(name);
    }
    
}
