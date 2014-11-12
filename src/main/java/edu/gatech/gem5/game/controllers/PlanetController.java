package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Ship;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import edu.gatech.gem5.game.SaveFile;
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
 * @author James Park
 */
public class PlanetController extends AbstractController {

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
    @FXML
    private Button shipyard;
    @FXML
    private Button dock;

    public static final String PLANET_VIEW_FILE = "/fxml/planet.fxml";

    /**
     * Construct the planet controller.
     */
    public PlanetController() {
        // load the view or throw an exception
        super(PLANET_VIEW_FILE);

        this.title.setText(planet.getName());
        this.lblCompanies.setText(buildCompanyString());
        this.lblEnvironment.setText(planet.getEnvironment().getName());
        this.lblGovernment.setText(planet.getGovernment().getName());
        this.lblCondition.setText(planet.getCondition().getName());
        this.lblTechnology.setText(planet.getTechLevel().getName());
        Ship ship = player.getShip();
        //sets the number to the amount of credits needed to fully refuel
        this.refuelButton.setText("Refuel " + (ship.getType().getRange()
                - ship.getFuel()) * ship.getType().getFuelCost());

        if (planet.getShips().isEmpty()) {
            this.shipyard.setDisable(true);
        }

        if (planet.getShields().isEmpty() && planet.getWeapons().isEmpty()) {
            // Note: planet.getGadgets() returns null, so nothing here for now
            this.dock.setDisable(true);
        }
        //set hotkey for saving to Control + S
        root.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (new KeyCodeCombination(KeyCode.S,
                        KeyCombination.CONTROL_DOWN).match(event)) {
                    saveAndQuit();
                }
            }
        });
    }

    private String buildCompanyString() {
        StringBuilder companies = new StringBuilder();
        for (CompanyType c : planet.getCompanies()) {
            companies.append(c.getName()).append("\n");
        }
        return companies.toString();
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
        switch (id) {
            case "market":
                // LastAdventures.swap(new MarketController());
                transitionTo(new MarketController());
                break;
            case "travel":
                // LastAdventures.swap(new DisplayUniverseController());
                transitionTo(new DisplayUniverseController());
                break;
            case "shipyard":
                // LastAdventures.swap(new ShipyardController());
                transitionTo(new ShipyardController());
                break;
            case "dock":
                // LastAdventures.swap(new DockController());
                transitionTo(new DockController());
                break;
            default: break;
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
        SaveFile save = new SaveFile(
            universe,
            system,
            planet,
            player
        );
        save.write();
        btnSave.setDisable(true); // don't save again...
    }

    /**
     * Quits to the title screen, if the player has not saved yet, it prompts
     * them to.
     *
     */
    @FXML
    private void quit() {
        if (!btnSave.disabledProperty().get()) {
            Action response = Dialogs.create()
                    .owner(root)
                    .title("Warning")
                    .masthead("Warning")
                    .message("Save before quiting?")
                    .showConfirm();
            if (response == Dialog.ACTION_YES) {
                save();
                transitionTo(new TitleController());
            } else if (response == Dialog.ACTION_NO) {
                transitionTo(new TitleController());
            }
        } else {
            transitionTo(new TitleController());
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
        if (player.refuel()) {
            refuelButton.setText("Refuel 0");
        } else {
            refuelButton.setText("Not enough credits");
        }
    }
}
