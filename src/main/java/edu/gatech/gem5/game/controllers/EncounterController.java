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
    // @FXML
    // Label playerName;
    // @FXML
    // Label pilotValue;
    // @FXML
    // Label fighterValue;
    // @FXML
    // Label traderValue;
    // @FXML
    // Label engineerValue;
    // @FXML
    // Label investorValue;
    @FXML
    Button cont;
    // @FXML
    // Button returnToCreate;

    private NPC enemy;

    @FXML
    Text dialog;

    public static final String STATUS_VIEW_FILE = "/fxml/encounter.fxml";

    /**
     * Construct the encounter controller.
     */
    public EncounterController(NPC enemy) {
        // load the view or throw an exception
        super(STATUS_VIEW_FILE);
        this.enemy = enemy;
        System.out.println("EncounterController created!");

        // Character player = LastAdventures.getCurrentSaveFile().getCharacter();

        // Label[] labels = {playerName, pilotValue, fighterValue, traderValue, engineerValue, investorValue};

        // playerName.setText("Name: " + player.getName());
        // pilotValue.setText("Pilot: " + player.getPilot());
        // fighterValue.setText("Fighter: " + player.getFighter());
        // traderValue.setText("Trader: " + player.getTrader());
        // engineerValue.setText("Engineer: " + player.getEngineer());
        // investorValue.setText("Investor: " + player.getInvestor());

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

    // /**
    //  * Go back to create a new character.
    //  *
    //  * @param event a button press
    //  */
    // @FXML
    // public void goBack(ActionEvent event) throws Exception {
    //     LastAdventures.swap(new CharacterCreateController());
    // }

}
