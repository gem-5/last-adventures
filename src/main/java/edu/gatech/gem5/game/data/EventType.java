package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class EventType {

    /**
     * The key of the EventType.
     */
    private String key;
    /**
     * The title of the EventType.
     */
    private String title;
    /**
     * The effects of the EventType.
     */
    private Map<String, Map> effects;
    /**
     * The description of the EventType.
     */
    private Map<String, String> description;
    /**
     * The occurrence of the EventType.
     */
    private double occurrence;
    /**
     * The whether the EventType is forced or optional.
     */
    private boolean forced;

    /**
     * The EventType's Key, used for accessing EventTypes.
     */
    public static final String KEY = "event";

    /**
     * Returns whether or not the Event is forced on the player.
     *
     * @return true if forced, false otherwise
     */
    public boolean isForced() {
        return forced;
    }

    /**
     * Gets the title of the Event.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the initial message of the event.
     *
     * @return the initial message
     */
    public String getInitialMessage() {
        return description.get("general");
    }

    /**
     * Gets the success message of the event.
     *
     * @return the success message
     */
    public String getSuccessMessage() {
        return description.get("success");
    }

    /**
     * Gets the effects of the event.
     *
     * @return the effects
     */
    public Map<String, Map> getEffects() {
        return effects;
    }

    /**
     * Gets the occurrence of the event.
     *
     * @return the occurrence
     */
    public double getOccurrence() {
        return occurrence;
    }
    /**
     * Gets the key of the event.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }


}
