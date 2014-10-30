package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.ConditionType;

import java.util.Map;

/**
 * A class for parsing condition data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class ConditionReader extends Reader<String, ConditionType> {

    public ConditionReader(String path) {
        super(path, new TypeToken<
            Map<String, ConditionType>
        >() {} .getType());
    }
}
