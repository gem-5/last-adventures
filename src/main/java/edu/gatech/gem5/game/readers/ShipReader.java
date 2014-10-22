package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.ShipType;

import java.util.Map;

/**
 * A class for parsing ship data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class ShipReader extends Reader<String, ShipType> {

    public ShipReader(String path) {
        super(path, new TypeToken<
            Map<String, ShipType>
        >(){}.getType());
    }
}
