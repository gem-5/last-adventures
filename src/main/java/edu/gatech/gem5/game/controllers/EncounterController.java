package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Encounterable;
import edu.gatech.gem5.game.AbstractNPC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller Class.
 *
 * Shows encounter during flight
 *
 * @author Sam Blumenthal
 * @author James Jong Han Park
 */
public class EncounterController extends AbstractController {

    /**
     * A continue button for a new planet.
     */
    @FXML
    Button cont;

    /**
     * The NPC that the Character has encountered.
     */
    protected Encounterable encounter;
    /**
     * A message that is shown to the user.
     */
    @FXML
    Text dialog;
    /**
     * An attack button for combat.
     */
    @FXML
    Button attack;

    // public static final String ENCOUNTER_VIEW_FILE = "/fxml/encounter.fxml";
    /**
     * Construct the encounter controller.
     *
     * @param e the NPC that the Character has encountered.
     */
    public EncounterController(Encounterable e) {
        // load the view or throw an exception
        super(e.getViewFile());
        this.encounter = e;
        String msg = e.getEncounterMessage();
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

    /**
     * Continue to the new combat screen.
     *
     * @param event a button press
     * @throws Exception propogates JavaFX exceptions
     */
    public void attackEnemy(ActionEvent event) throws Exception {
        transitionTo(new CombatController(player, (AbstractNPC) encounter));
    }

}
