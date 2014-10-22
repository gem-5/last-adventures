
package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Ship;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.data.CompanyType;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Creston Bunch
 */
public class PlanetController extends Controller {


    @FXML
    private Label title;

    @FXML
    private Label lblCompanies;
    @FXML
    private Label lblEnvironment;
    @FXML
    private Label lblGovernment;
    @FXML
    private Label lblTechnology;
    @FXML
    private Label lblCondition;
    @FXML
    private Button btnSave;
    @FXML
    private Button refuelButton;


    Planet planet;

    public static final String PLANET_VIEW_FILE = "/fxml/planet.fxml";

    /**
     * Construct the planet controller.
     */
    public PlanetController() {
        // load the view or throw an exception
        super(PLANET_VIEW_FILE);

        SaveFile save = LastAdventures.getCurrentSaveFile();
        planet = save.getPlanet();

        this.title.setText(planet.getName());
        this.lblCompanies.setText(buildCompanyString());
        this.lblEnvironment.setText(planet.getEnvironment().getName());
        this.lblGovernment.setText(planet.getGovernment().getName());
        this.lblCondition.setText(planet.getCondition().getName());
        this.lblTechnology.setText(planet.getTechLevel().getName());
        Ship ship = save.getCharacter().getShip();
        //sets the number to the amount of credits needed to fully refuel
        this.refuelButton.setText("Refuel " +(ship.getType().getRange() -
                ship.getFuel()) * ship.getType().getFuelCost());

        //set hotkey for saving to Control + S
        root.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (new KeyCodeCombination(KeyCode.S,
                    KeyCombination.CONTROL_DOWN).match(event)) {
                saveAndQuit();
            }
        });
    }

    private String buildCompanyString() {
        String companies = "";
        for (CompanyType c : planet.getCompanies()) {
            companies += c.getName() + "\n";
        }
        return companies;
    }

    /**
     * Changes the scene based on the button pressed.
     *
     * @param event a button press
     * @throws Exception propagates any JavaFX Exception
     */
    @FXML
    private void changeScenes(ActionEvent event) throws Exception {
        String id = ((Button) (event.getSource())).idProperty().get();
        //loads the create FXML file into root
        if (id.equals("market")) {
            LastAdventures.swap(new MarketController());
        } else if (id.equals("travel")) {
            LastAdventures.swap(new DisplayUniverseController());
        } else if (id.equals("shipyard")) {
            LastAdventures.swap(new ShipyardController());
        }
    }

    /**
     * Saves the game.
     *
     * @param event pressing the save button
     * @throws Exception exception with javafx
     */
    @FXML
    private void save() {
        LastAdventures.getCurrentSaveFile().save();
        btnSave.setDisable(true); // don't save again...
    }

    /**
     * Quits to the title screen, if the player has not saved yet, it prompts
     * them to.
     *
     */
    @FXML
    private void quit() {
        if(!btnSave.disabledProperty().get()) {
            Action response = Dialogs.create()
                        .owner(root)
                        .title("Warning")
                        .masthead("Warning")
                        .message("Save before quiting?")
                        .showConfirm();
            if (response == Dialog.ACTION_YES) {
                save();
                LastAdventures.swap(new TitleController());
            } else if (response == Dialog.ACTION_NO) {
                LastAdventures.swap(new TitleController());
            }
        } else {
            LastAdventures.swap(new TitleController());
        }
    }

    /**
     * Combines the saving and quiting method into one. Is invoked by pressing
     * Control + S
     */
    private void saveAndQuit() {
        save();
        quit();
    }

    @FXML
    /**
     * Max out the fuel in your ship.
     *
     * @param event A button press attempting to refuel
     * @throws Exception
     */
    public void refuel(ActionEvent event) throws Exception {
        if(LastAdventures.getCurrentSaveFile().getCharacter().refuel()) {
            refuelButton.setText("Refuel 0");
        } else {
            refuelButton.setText("Not enough credits");
        }
    }
}

