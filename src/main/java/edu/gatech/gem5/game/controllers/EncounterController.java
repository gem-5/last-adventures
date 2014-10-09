package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.NPC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


/**
 * FXML Controller Class
 *
 * Shows encounter during flight
 *
 * @author Sam Blumenthal
 */
public class EncounterController extends Controller {

    Parent root;

    @FXML
    Button cont;

    private NPC encounter;

    @FXML
    Text dialog;

    public static final String ENCOUNTER_VIEW_FILE = "/fxml/encounter.fxml";

    /**
     * Construct the encounter controller.
     * @param encounter the NPC that the Character has encountered.
     */
    public EncounterController(NPC encounter) {
        // load the view or throw an exception
        super(ENCOUNTER_VIEW_FILE);
        this.encounter = encounter;
        // System.out.println("EncounterController created!");

        String msg = encounter.getEncounterMessage();
        dialog.setText(msg);
    }

    /**
     * Continue to the new planet screen.
     *
     * @param event a button press
     * @throws Exception propogates JavaFX exceptions.
     */
    @FXML
    public void continueToPlanet(ActionEvent event) throws Exception {
        LastAdventures.swap(new PlanetController());
    }


}
