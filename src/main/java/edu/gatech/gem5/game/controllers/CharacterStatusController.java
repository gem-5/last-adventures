package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller Class
 *
 * Shows character stats after creation.
 *
 * @author Alex
 * @author James Jong Han Park
 * @author Jack Mueller
 */
public class CharacterStatusController extends AbstractController {

    /**
     * Interacting player's name.
     */
    @FXML
    Label playerName;
    /**
     * Interacting player's pilot skill level.
     */
    @FXML
    Label pilotValue;
    /**
     * Interacting player's fighter skill level.
     */
    @FXML
    Label fighterValue;
    /**
     * Interacting player's trader skill level.
     */
    @FXML
    Label traderValue;
    /**
     * Interacting player's engineer skill level.
     */
    @FXML
    Label engineerValue;
    /**
     * Interacting player's investor skill level.
     */
    @FXML
    Label investorValue;
    /**
     * Confirm button for moving onto the next scene.
     */
    @FXML
    Button confirm;
    /**
     * Return button for moving to the previous scene.
     */
    @FXML
    Button returnToCreate;
    /**
     * A message for player in this scene.
     */
    @FXML
    Text dialog;
    /**
     * FXML file that is directly associated with this class.
     */
    public static final String STATUS_VIEW_FILE = "/fxml/status.fxml";

    /**
     * Construct the character status controller.
     */
    public CharacterStatusController() {
        // load the view or throw an exception
        super(STATUS_VIEW_FILE);

        playerName.setText("Name: " + player.getName());
        pilotValue.setText("Pilot: " + player.getPilot());
        fighterValue.setText("Fighter: " + player.getFighter());
        traderValue.setText("Trader: " + player.getTrader());
        engineerValue.setText("Engineer: " + player.getEngineer());
        investorValue.setText("Investor: " + player.getInvestor());

        String msg = Data.STORY.get("intro").toString();
        dialog.setText(msg);
    }

    /**
     * Continue to the planet screen.
     *
     * @param event a button press
     * @throws Exception propogates any JavaFX Exception
     */
    @FXML
    public void startGame(ActionEvent event) throws Exception {
        // LastAdventures.swap(new PlanetController());
        transitionTo(new PlanetController());
    }

    /**
     * Go back to create a new character.
     *
     * @param event a button press
     * @throws Exception propogates any JavaFX Exception
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        // LastAdventures.swap(new CharacterCreateController());
        transitionTo(new CharacterCreateController());
    }

}
