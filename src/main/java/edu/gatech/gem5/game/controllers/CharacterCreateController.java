package edu.gatech.gem5.game.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.data.ShipType;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author James Jong Han Park
 * @author Jack
 */
public class CharacterCreateController extends Controller {

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

    @FXML
    Label pilot;
    @FXML
    Label fighter;
    @FXML
    Label trader;
    @FXML
    Label engineer;
    @FXML
    Label investor;
    @FXML
    Button confirm;
    @FXML
    Label remaining;
    @FXML
    Label errorMessage;

    private Button[] incButtons, decButtons;
    private Label[] values, skillNames;

    public static final String CREATE_VIEW_FILE = "/fxml/create.fxml";
    public static final int MAX_NAME_LENGTH = 8;

    /**
     * Construct the character creation controller.
     */
    public CharacterCreateController() {
        // load the view or throw an exception
        super(CREATE_VIEW_FILE);
        // Create a new save file
        game.createNewSaveFile();
        addTextLimiter(name, MAX_NAME_LENGTH);
        values = new Label[]{pilotValue, fighterValue, traderValue, engineerValue, investorValue};
        incButtons = new Button[]{pilotInc, fighterInc, traderInc, engineerInc, investorInc};
        decButtons = new Button[]{pilotDec, fighterDec, traderDec, engineerDec, investorDec};
        skillNames = new Label[]{pilot, fighter, trader, engineer, investor, remaining};
    }

    /**
     * Move to the confirm screen.
     *
     * @param event a button press
     */
    @FXML
    public void confirmCharacter(ActionEvent event) throws Exception {


        // If not all points are allotcated, show warning message.
        if (Integer.parseInt(remainingValue.getText()) > 0) {
            Action response = Dialogs.create()
                    .owner(root)
                    .title("Warning")
                    .masthead("Warning")
                    .message("Are you sure that you want to continue without allocating all points?")
                    .showConfirm();
            if (response == Dialog.ACTION_YES) {
                // System.out.println("I got a success.");
                if (validate(name.getText().trim())) {
                    beginNewGame(createCharacter(), createUniverse());
                    LastAdventures.swap(new CharacterStatusController());
                }
            }
        } else {
            // System.out.println("All points allocated.");
            if (validate(name.getText().trim())) {
                beginNewGame(createCharacter(), createUniverse());
                LastAdventures.swap(new CharacterStatusController());
            }
        }
    }

    /**
     * Go back to the title screen.
     *
     * @param event a button press
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        LastAdventures.deleteSaveFile(LastAdventures.getCurrentSaveFile());
        LastAdventures.swap(new TitleController());
    }

    private boolean validate(String str) {
        if (str.isEmpty()) {
            errorMessage.setText("Please enter a name.");
            return false;
        }
        return true;
    }

    private Character createCharacter() {
        Map<String, ShipType> ships = LastAdventures.data.get(ShipType.KEY);
        return new Character(
                name.getText().trim(),
                Integer.parseInt(pilotValue.getText()),
                Integer.parseInt(fighterValue.getText()),
                Integer.parseInt(traderValue.getText()),
                Integer.parseInt(engineerValue.getText()),
                Integer.parseInt(investorValue.getText()),
                // default ship
                new Ship(ships.get("vagabond")));
    }

    private Universe createUniverse() {
        return new Universe(120, 4, 13);
    }

    private void beginNewGame(Character player, Universe uni) {
        final SaveFile currentSaveFile = LastAdventures.getCurrentSaveFile();
        currentSaveFile.addCharacter(player);
        currentSaveFile.addUniverse(uni);
        List<SolarSystem> systems = uni.getUniverse();
        SolarSystem start = systems.get(new Random().nextInt(systems.size()));
        currentSaveFile.setSolarSystem(start);
        currentSaveFile.setCurrentPlanet(start.getPlanets().get(0));
    }

    /**
     *
     * @param event a incrementor button press
     */
    @FXML
    public void increment(ActionEvent event) {
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
     */
    @FXML
    public void decrement(ActionEvent event) {
        Button buttonName = (Button) event.getSource();

        for (int count = 0; count < decButtons.length; count++) {
            if (decButtons[count] == buttonName && Integer.parseInt(values[count].getText()) != 1) {
                values[count].setText("" + (Integer.parseInt(values[count].getText()) - 1));
                remainingValue.setText("" + (Integer.parseInt(remainingValue.getText()) + 1));
            }
        }
    }

    /**
     * Limits the length of a text field
     *
     * @param tf A text field
     * @param maxLength The maximum number of characters allowed
     */
    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                    final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

}
