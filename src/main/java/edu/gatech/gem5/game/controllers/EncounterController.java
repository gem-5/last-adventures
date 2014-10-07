package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.NPC;
import edu.gatech.gem5.game.data.StoryText;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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

    private NPC enemy;

    @FXML
    Text dialog;

    public static final String STATUS_VIEW_FILE = "/fxml/encounter.fxml";

    /**
     * Construct the encounter controller.
     * @param encounter the NPC that the Character has encountered.
     */
    public EncounterController(NPC encounter) {
        // load the view or throw an exception
        super(STATUS_VIEW_FILE);
        this.enemy = enemy;
        // System.out.println("EncounterController created!");

        String msg = enemy.getEncounterMessage();
        dialog.setText(msg);
    }

    /**
     * Continue to the new planet screen.
     *
     * @param event a button press

     */
    @FXML
    public void continueToPlanet(ActionEvent event) throws Exception {
        LastAdventures.swap(new PlanetController());
    }


}
