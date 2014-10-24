package edu.gatech.gem5.game;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class EventEffectParser {
    /**
     * The map containing JSON effect keys mapped to their Effect classes.
     */
    private Map<String, AbstractEffect> supported = new HashMap<>();

    /**
     * 
     * @param effect the key that will be checked against supported effects
     * @return whether or not the effect is supported
     */
    public boolean isSupportedEffect(String effect) {
        return supported.containsKey(effect);
    }

    /**
     * Gets the effect from the map of supported effects, then applies that
     * effect to the game.
     * 
     * @param key the key of the desired effect
     * @param value a map of Effect properties and their values to be properly
     * applied to the game
     */
    public void doEffect(String key, Map value) {
        supported.get(key).apply(); //map value of min:34, max:35
    }
}
