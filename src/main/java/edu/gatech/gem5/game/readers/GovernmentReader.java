package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.data.GovernmentType;

import java.util.Map;

/**
 * A class for parsing government data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class GovernmentReader extends AbstractReader<String, GovernmentType> {

    /**
     * Construct the GovernmentReader given the path.
     * 
     * @param path 
     */
    public GovernmentReader(String path) {
        super(path, new TypeToken<
            Map<String, GovernmentType>>() {
        } .getType());
    }
}
