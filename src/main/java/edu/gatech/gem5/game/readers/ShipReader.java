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

    public  static final String SHIP_DATA_PATH = "/data/Ships.json";

    public ShipReader() {
        this(SHIP_DATA_PATH);
    }

    public ShipReader(String path) {
        super(path, new TypeToken<
            Map<String, ShipType>>() {
        } .getType());
    }
}
