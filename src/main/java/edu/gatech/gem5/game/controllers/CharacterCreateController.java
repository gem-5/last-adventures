package edu.gatech.gem5.game.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Ship;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James Jong Han Park
 * @author Jack
 */
public class CharacterCreateController implements Initializable {

    Parent root;
    @FXML
    Button pilotInc;
    @FXML
    Button fighterInc;
    @FXML
    Button traderInc;
    @FXML
    Button engineerInc;
    @FXML
    Button investorInc;
    @FXML
    Button pilotDec;
    @FXML
    Button fighterDec;
    @FXML
    Button traderDec;
    @FXML
    Button engineerDec;
    @FXML
    Button investorDec;
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
    Label remainingValue;
    @FXML
    TextField name;

    private Button[] incButtons, decButtons;
    private Label[] values;

    /**
     *
     * @param event a button press
     * @throws Exception
     */
    @FXML
    public void changeScenes(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String id = ((Button) (event.getSource())).idProperty().get();
        if (id.equals("confirm")) {
            Character player = new Character(name.getText(),
                    Integer.parseInt(pilotValue.getText()),
                    Integer.parseInt(fighterValue.getText()),
                    Integer.parseInt(traderValue.getText()),
                    Integer.parseInt(engineerValue.getText()),
                    Integer.parseInt(investorValue.getText()), (Ship) null);
            LastAdventures.getCurrentSaveFile().addCharacter(player);
            //this is to print out the character once it is made
            System.out.println(LastAdventures.getCurrentSaveFile());
            root = FXMLLoader.load(getClass().getResource("/status.fxml"));
        } else if (id.equals("back")) {
            root = FXMLLoader.load(getClass().getResource("/title.fxml"));
        }

        stage.setScene(new Scene((Pane) root));
    }

    /**
     *
     * @param event a incrementor button press
     * @throws Exception
     */
    @FXML
    public void increment(ActionEvent event) throws Exception {
        Button buttonName = (Button) event.getSource();

        if (Integer.parseInt(remainingValue.getText()) != 0) {

            for (int count = 0; count < incButtons.length; count++) {
                if (incButtons[count] == buttonName) {
                    values[count].setText("" + (Integer.parseInt(values[count].getText()) + 1));
                }
            }
            remainingValue.setText("" + (Integer.parseInt(remainingValue.getText()) - 1));
        }

    }

    /**
     *
     * @param event a decrementor button press
     * @throws Exception
     */
    @FXML
    public void decrement(ActionEvent event) throws Exception {
        Button buttonName = (Button) event.getSource();

        for (int count = 0; count < decButtons.length; count++) {
            if (decButtons[count] == buttonName && Integer.parseInt(values[count].getText()) != 1) {
                values[count].setText("" + (Integer.parseInt(values[count].getText()) - 1));
                remainingValue.setText("" + (Integer.parseInt(remainingValue.getText()) + 1));
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Note: Make sure that values, incButtons, and decButtons' objects allign in order together.
        // Ex. values[0] = pilotValue, incButtons[0] = pillotInc, decbuttons[0] = pilotDec
        // ... values[3] = engineerValue, incButtons[3] = engineerInc, decbuttons[3] = engineerDec
        values = new Label[]{pilotValue, fighterValue, traderValue, engineerValue, investorValue};
        incButtons = new Button[]{pilotInc, fighterInc, traderInc, engineerInc, investorInc};
        decButtons = new Button[]{pilotDec, fighterDec, traderDec, engineerDec, investorDec};
    }
}
