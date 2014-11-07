package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Event;
import java.util.Map;
import java.util.Map.Entry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jack Mueller
 */
public class EventController extends Controller {

    @FXML
    Label title, message;
    @FXML
    Button continueButton;

    public static final String RANDOM_EVENT_FILE = "/fxml/event.fxml";

    Event event;

    public EventController(Event event) {
        super(RANDOM_EVENT_FILE);
        this.event = event;
        title.setText(event.getTitle());
        message.setText(event.getEncounterMessage());
    }

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
            /*if(supportedEffect(entry)) {
             doEffect(entry.getKey(), entry.getValue());
             } else {
             System.err.println("Unsupported Effect in " + this.event.getKey());
             }*/
            // System.out.println("hello" + entry.getKey());
            message.setText(event.getSuccessMessage());
            if (entry.getKey().equals("money")) {
                double themoney = (double) (Double) (entry.getValue().get("min"));
                player.setMoney(Math.max(0, player.getMoney() + (int) themoney));
            }
        }
    }

}
