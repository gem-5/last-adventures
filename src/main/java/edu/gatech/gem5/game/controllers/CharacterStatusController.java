package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Universe;
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

/**
 * FXML Controller Class
 *
 * Shows character stats after creation.
 *
 * @author Alex
 * @author James Jong Han Park
 * @author Jack Mueller
 */
public class CharacterStatusController extends Controller {

    Parent root;
    @FXML
    Label playerName;
    @FXML
    Label pilotValue;
    @FXML
    Label fighterValue;
    @FXML
    Label traderValue;
    @FXML
    Label engineerValue;
    @FXML
    Label investorValue;
    @FXML
    Button confirm;
    @FXML
    Button returnToCreate;

    public static final String STATUS_VIEW_FILE = "/fxml/status.fxml";

    /**
     * Construct the character status controller.
     */
    public CharacterStatusController() {
        // load the view or throw an exception
        super(STATUS_VIEW_FILE);

        Character player = LastAdventures.getCurrentSaveFile().getCharacter();

        Label[] labels = {playerName, pilotValue, fighterValue, traderValue, engineerValue, investorValue};

        playerName.setText("Name: " + player.getName());
        pilotValue.setText("Pilot: " + player.getPilot());
        fighterValue.setText("Fighter: " + player.getFighter());
        traderValue.setText("Trader: " + player.getTrader());
        engineerValue.setText("Engineer: " + player.getEngineer());
        investorValue.setText("Investor: " + player.getInvestor());
    }

    /**
     * Continue to the planet screen.
     *
     * @param event a button press
     */
    @FXML
    public void startGame(ActionEvent event) throws Exception {
        LastAdventures.swap(new PlanetController());
    }

    /**
     * Go back to create a new character.
     *
     * @param event a button press
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        LastAdventures.swap(new CharacterCreateController());
    }

}
