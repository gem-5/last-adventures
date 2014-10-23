package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Event;
import edu.gatech.gem5.game.EventEffectParser;
import edu.gatech.gem5.game.LastAdventures;
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
        EventEffectParser parser = new EventEffectParser();
        for(Entry<String, Map> entry : effects.entrySet()) {
            if(parser.isSupportedEffect(entry.getKey())) {
                parser.doEffect(entry.getKey(), entry.getValue());
            } else {
                System.err.println("Unsupported Effect in " + this.event.getKey());
            }
            //System.out.println("hello" + entry.getKey());
            message.setText(event.getSuccessMessage());
            if(entry.getKey().equals("money")) {
                Character character = LastAdventures.getCurrentSaveFile().getCharacter();
                double themoney = (double) (Double) (entry.getValue().get("min"));
                character.setMoney(character.getMoney() + (int) themoney);
            }
        }
    }
    
}
