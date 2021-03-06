package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.data.GadgetType;

import java.util.Map;

/**
 * A class for parsing gadget data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class GadgetReader extends AbstractReader<String, GadgetType> {

    /**
     * Construct the GadgetReader given the path.
     * 
     * @param path 
     */
    public GadgetReader(String path) {
        super(path, new TypeToken<
            Map<String, GadgetType>>() {
        } .getType());
    }
}
