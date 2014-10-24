package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.EventController;
import edu.gatech.gem5.game.controllers.Controller;
import edu.gatech.gem5.game.data.EventType;
import java.util.Map;

/**
 *
 * @author Jack Mueller
 */
public class Event implements Encounterable {

    EncounterManager manager;
    EventType type;

    public Event(EventType type) {
        this.type = type;
    }


    @Override
    public EncounterManager getManager() {
        return manager;
    }

    @Override
    public void setManager(EncounterManager manager) {
        this.manager = manager;
    }

    @Override
    public String getEncounterMessage() {
        return type.getInitialMessage();
    }

    @Override
    public Controller getEncounterController() {
        return new EventController(this);
    }

    @Override
    public String getViewFile() {
        return "/fxml/event.fxml";
    }

    public Map<String, Map> getEffects() {
        return type.getEffects();
    }

    public String getKey() {
        return type.getKey();
    }

    public String getTitle() {
        return type.getTitle();
    }

    public String getSuccessMessage() {
        return type.getSuccessMessage();
    }

}
