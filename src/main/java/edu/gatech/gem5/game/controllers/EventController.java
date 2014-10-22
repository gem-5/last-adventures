package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Event;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jack Mueller
 */
public class EventController extends Controller {
    
    
    @FXML
    Label title;
    @FXML
    Button continueButton;
    
    
    public static final String RANDOM_EVENT_FILE = "/fxml/event.fxml";

    Event event;

    public EventController(Event event) {
        super(RANDOM_EVENT_FILE);
        this.event = event;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        for(Entry<String, Map> entry : effects.entrySet()) {
            /*if(supportedEffect(entry)) {
                doEffect(entry.getKey(), entry.getValue());
            } else {
                System.err.println("Unsupported Effect in " + this.event.getKey());
            }*/
            System.out.println("hello" + entry.getKey());
        }
    }
    
}
