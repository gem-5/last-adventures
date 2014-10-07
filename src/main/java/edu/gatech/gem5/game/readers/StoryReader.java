package edu.gatech.gem5.game.readers;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.StoryText;

/**
 * A class for parsing story data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.0.0
 */

public class StoryReader extends Reader<StoryText> {

    /**
     * Load the JSON dictionary into a Map object.
     *
     * @param path The path of the data file to load.
     * @return A map with the ids and corresponding objects.
     */
    @Override
    public Map<String, StoryText> load(String path) {
        Map<String, StoryText> map = new HashMap<>();
        InputStream stream = getClass().getResourceAsStream(path);
        String json = readStream(stream);

        Type collectionType = new TypeToken<
            Map<String, StoryText>
        >(){}.getType();

        map = new Gson().fromJson(json, collectionType);

        return map;
    }
}
