package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.LastAdventures;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * FXML Controller Class
 *
 * Shows character stats after creation.
 *
 * @author Alex
 * @author James Jong Han Park
 */
public class CharacterStatusController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        Character player = LastAdventures.getCurrentSaveFile().getCharacter();

        Label[] labels = {playerName, pilotValue, fighterValue, traderValue, engineerValue, investorValue};

        playerName.setText("Name: " + player.getName());
        pilotValue.setText("Pilot: " + player.getPilot());
        fighterValue.setText("Fighter: " + player.getFighter());
        traderValue.setText("Trader: " + player.getTrader());
        engineerValue.setText("Engineer: " + player.getEngineer());
        investorValue.setText("Investor: " + player.getInvestor());

        FadeHandler fh;

        // Sets all labels to transparent/invisible (for fade effect below)
        for (Label label : labels) {
            label.setOpacity(0);
        }

        // Adds fade in effects. Labels appear in order, .2 seconds per label.
        for (int x = 0; x < labels.length; x++) {
            new FadeHandler(labels[x], x / 5.0);
        }
    }
}
