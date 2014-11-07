package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.data.ShieldType;

import java.util.Map;

/**
 * A class for parsing shield data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class ShieldReader extends AbstractReader<String, ShieldType> {

    public ShieldReader(String path) {
        super(path, new TypeToken<
            Map<String, ShieldType>>() {
        } .getType());
    }
}
