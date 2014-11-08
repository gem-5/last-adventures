package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.data.ShipType;

import java.util.Map;

/**
 * A class for parsing ship data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class ShipReader extends AbstractReader<String, ShipType> {

    /**
     * The default path for the ShipReader constructor to take if no other
     * path is given.
     */
    public static final String SHIP_DATA_PATH = "/data/Ships.json";

    /**
     * Construct a ShipReader with the default path.
     */
    public ShipReader() {
        this(SHIP_DATA_PATH);
    }

    /**
     * Construct a ShipReader given the path.
     * 
     * @param path 
     */
    public ShipReader(String path) {
        super(path, new TypeToken<
            Map<String, ShipType>>() {
        } .getType());
    }
}
