package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.data.EventType;

import java.util.Map;

/**
 * A class for random event data files into a Java map.
 *
 * @author  Jack Mueller
 */

public class EventReader extends AbstractReader<String, EventType> {

    public EventReader(String path) {
        super(path, new TypeToken<
            Map<String, EventType>>() {
        } .getType());
    }
}
