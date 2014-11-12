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

    /**
     * Title of the planet.
     */
    @FXML
    private Label title;

    /**
     * Shows a list of companies for this planet.
     */
    @FXML
    private Label lblCompanies;
    /**
     * Shows the environment of this planet.
     */
    @FXML
    private Label lblEnvironment;
    /**
     * Shows the government of this planet.
     */
    @FXML
    private Label lblGovernment;
    /**
     * Shows the tech level of this planet.
     */
    @FXML
    private Label lblTechnology;
    /**
     * Shows the current condition of this planet.
     */
    @FXML
    private Label lblCondition;
    /**
     * The button for saving, once clicked, it becomes disabled until the next
     * PlantController.
     */
    @FXML
    private Button btnSave;
    /**
     * The button to refuel the ship which also shows how much that would cost.
     */
    @FXML
    private Button refuelButton;
    /**
     * A button to go to the shipyard, disabled if there are no ships.
     */
    @FXML
    private Button shipyard;
    /**
     * A button to go to the dock, disabled if there are not upgrades.
     */
    @FXML
    private Button dock;

    /**
     * The fxml view file associated with this controller.
     */
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

    /**
     * 
     * @return a formatted string containing the names of all the companies
     * on this planet.
     */
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
