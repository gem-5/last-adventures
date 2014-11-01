package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Encounterable;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.AbstractNPC;
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

    @FXML
    Button cont;

    protected Encounterable encounter;

    @FXML
    Text dialog;

    @FXML
    Button attack;

    // public static final String ENCOUNTER_VIEW_FILE = "/fxml/encounter.fxml";
    /**
     * Construct the encounter controller.
     *
     * @param encounter the NPC that the Character has encountered.
     */
    public EncounterController(Encounterable encounter) {
        // load the view or throw an exception
        super(encounter.getViewFile());
        this.encounter = encounter;
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
        //"continue to planet" in sense of get further along in your trip
        encounter.getManager().nextEncounter();

    }

    public void attackEnemy(ActionEvent event) throws Exception {
        // LastAdventures.swap(new CombatController(LastAdventures.getCurrentSaveFile().getCharacter(),
        //                                          (NPC) encounter));
        transitionTo(new CombatController(LastAdventures.getCurrentSaveFile().getCharacter(),
                (AbstractNPC) encounter));
    }

}
