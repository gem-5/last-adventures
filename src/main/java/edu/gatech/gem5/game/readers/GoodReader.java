package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.GoodType;

import java.util.Map;

/**
 * A class for parsing goods data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class GoodReader extends Reader<String, GoodType> {

    public GoodReader(String path) {
        super(path, new TypeToken<
            Map<String, GoodType>
        >() {} .getType());
    }
}
