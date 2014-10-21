package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.WeaponType;

import java.util.Map;

/**
 * A class for parsing weapon data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class WeaponReader extends Reader<String, WeaponType> {

    public WeaponReader(String path) {
        super(path, new TypeToken<
            Map<String, WeaponType>
        >(){}.getType());
    }
}
