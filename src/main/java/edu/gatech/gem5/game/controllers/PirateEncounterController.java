package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Encounterable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * FXML Controller Class.
 *
 * Shows pirate encounter during flight.
 *
 * @author Sam Blumenthal
 */
public class PirateEncounterController extends EncounterController {

    // @FXML
    // Button attack;
    /**
     * Button used to flee encounter.
     */
    @FXML
    Button flee;

    /**
     * The path that the constructor will take.
     */
    public static final String PIRATE_VIEW_FILE = "/fxml/pirateencounter.fxml";

    /**
     * Constructor for PirateEncounterController.
     *
     * @param encounter Type of encounter
     */
    public PirateEncounterController(Encounterable encounter) {
        super(encounter);
    }

}
