package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.EnvironmentType;

import java.util.Map;

/**
 * A class for parsing environment data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class EnvironmentReader extends Reader<String, EnvironmentType> {

    public EnvironmentReader(String path) {
        super(path, new TypeToken<
            Map<String, EnvironmentType>
        >() {} .getType());
    }
}
