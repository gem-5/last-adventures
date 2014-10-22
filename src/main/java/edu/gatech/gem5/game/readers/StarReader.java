package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.StarType;

import java.util.Map;

/**
 * A class for parsing star system data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class StarReader extends Reader<String, StarType> {

    public StarReader(String path) {
        super(path, new TypeToken<
            Map<String, StarType>
        >(){}.getType());
    }
}
