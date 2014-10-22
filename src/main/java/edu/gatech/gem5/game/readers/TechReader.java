package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.TechType;

import java.util.Map;

/**
 * A class for parsing tech level data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class TechReader extends Reader<Integer, TechType> {

    public TechReader(String path) {
        super(path, new TypeToken<
            Map<Integer, TechType>
        >(){}.getType());
    }
}
