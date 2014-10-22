package edu.gatech.gem5.game.data;

import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class EventType {

    private String key;
    private String title;
    private Map<String, Map> effects;
    private Map<String, String> description;
    private double occurrence;
    private boolean forced;
    
    public static final String KEY = "event";
    
    public boolean isForced() {
        return forced;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getInitialMessage() {
        return description.get("general");
    }
    
    public String getSuccessMessage() {
        return description.get("success");
    }

    public Map<String, Map> getEffects() {
        return effects;
    }
    
    public double getOccurrence() {
        return occurrence;
    }

    public String getKey() {
        return key;
    }


}
