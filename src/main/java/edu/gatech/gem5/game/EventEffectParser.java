package edu.gatech.gem5.game;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class EventEffectParser {
    private Map<String, Effect> supported = new HashMap<>();

    public boolean isSupportedEffect(String effect) {
        return supported.containsKey(effect);
    }

    public void doEffect(String key, Map value) {
        supported.get(key).apply();//map value of min:34, max:35
    }

}
