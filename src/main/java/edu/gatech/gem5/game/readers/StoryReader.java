package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.data.StoryText;

import java.util.Map;

/**
 * A class for parsing story data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class StoryReader extends AbstractReader<String, StoryText> {

    /**
     * Construct a StoryReader given the path.
     * 
     * @param path 
     */
    public StoryReader(String path) {
        super(path, new TypeToken<
            Map<String, StoryText>>() {
        } .getType());
    }
}
