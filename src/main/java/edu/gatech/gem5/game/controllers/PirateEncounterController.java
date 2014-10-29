package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Encounterable;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Pirate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller Class
 *
 * Shows pirate encounter during flight
 *
 * @author Sam Blumenthal
 */
public class PirateEncounterController extends EncounterController {

    // @FXML
    // Button attack;

    @FXML
    Button flee;


    public static final String PIRATE_VIEW_FILE = "/fxml/pirateencounter.fxml";

    public PirateEncounterController(Encounterable encounter) {
        super(encounter);
    }


}
