package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Event;
import java.util.Map;
import java.util.Map.Entry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class.
 *
 * @author Jack Mueller
 * @author James Jong Han Park
 */
public class EventController extends Controller {

    /**
     * Label for title of the event.
     */
    @FXML
    Label title;
    /**
     * Label for message of the encounter event.
     */
    @FXML
    Label message;

    /**
     * A button to continue to the next encounter event.
     */
    @FXML
    Button continueButton;

    /**
     * An FXML file path that is directly associated with this class.
     */
    public static final String RANDOM_EVENT_FILE = "/fxml/event.fxml";

    /**
     * An Event object reference to the event that is passed in from the
     * parameter.
     */
    Event event;

    /**
     * Parameter for EventController.
     * @param event An event object
     */
    public EventController(Event event) {
        super(RANDOM_EVENT_FILE);
        this.event = event;
        title.setText(event.getTitle());
        message.setText(event.getEncounterMessage());
    }

    /**
     * Handles necessary cases for next encounter, then passes the next
     * encounter (if continue button is pressed).
     */
    @FXML
    public void resolve() {
        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                event.getManager().nextEncounter();
            }
        });

        Map<String, Map> effects = event.getEffects();
        for (Entry<String, Map> entry : effects.entrySet()) {
            /*
             * if(supportedEffect(entry)) { doEffect(entry.getKey(),
             * entry.getValue()); } else {
             * System.err.println("Unsupported Effect in " +
             * this.event.getKey()); }
             */
            // System.out.println("hello" + entry.getKey());
            message.setText(event.getSuccessMessage());
            if (entry.getKey().equals("money")) {
                double themoney = (double) (Double) (entry.getValue()
                        .get("min"));
                player.setMoney(Math.max(0, player.getMoney() + (int) themoney));
            }
        }
    }

}
