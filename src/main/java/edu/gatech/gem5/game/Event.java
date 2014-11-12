package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EventController;
import edu.gatech.gem5.game.controllers.AbstractController;
import edu.gatech.gem5.game.data.EventType;
import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class Event implements Encounterable {
    
    /**
     * This encounterable's manager.
     */
    EncounterManager manager;
    /**
     * The data of the nature of this event, including effects, related text,
     * and metadata.
     */
    EventType type;
    
    /**
     * 
     * @param eventType This event's type
     */
    public Event(EventType eventType) {
        this.type = eventType;
    }

    @Override
    public EncounterManager getManager() {
        return manager;
    }

    @Override
    public void setManager(EncounterManager encounterManager) {
        this.manager = encounterManager;
    }

    @Override
    public String getEncounterMessage() {
        return type.getInitialMessage();
    }

    @Override
    public AbstractController getEncounterController() {
        return new EventController(this);
    }

    @Override
    public String getViewFile() {
        return "/fxml/event.fxml";
    }

    /**
     * 
     * @return Get the GSON generated map of Effects for this Event.
     */
    public Map<String, Map> getEffects() {
        return type.getEffects();
    }
    
    /**
     * 
     * @return Get the associated EventType's JSON key
     */
    public String getKey() {
        return type.getKey();
    }
    
    /**
     * 
     * @return The text for the title screen of this Event.
     */
    public String getTitle() {
        return type.getTitle();
    }

    /**
     * 
     * @return the text to be shown after main path execution of this Event
     */
    public String getSuccessMessage() {
        return type.getSuccessMessage();
    }
}
