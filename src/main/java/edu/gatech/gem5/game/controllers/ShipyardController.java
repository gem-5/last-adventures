package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Data;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.ui.ShipBar;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Alex
 */
public class ShipyardController extends Controller {
    /**
     * Label that will display text if player attempts an illegal action.
     */
    @FXML
    private Label errorLabel;

    /**
     * The amount of credits the player currently has.
     */
    @FXML
    private Label lblCash;

    /**
     * Info about the player's current ship.
     */
    @FXML
    private Label shipInfo;

    /**
     * The list of ships that the player can buy.
     */
    @FXML
    private ListView<ShipBar> buyShips;

    /**
     * The toggle group for the controller's radio buttons.
     */
    private final ToggleGroup shipGroup;

    /**
     * The player's current ship.
     */
    private Ship currentShip;

    /**
     * The path that the constructor will take.
     */
    public static final String SHIPYARD_VIEW_FILE = "/fxml/shipyard.fxml";

    /**
     * Constructor for the ShipyardController.
     */
    public ShipyardController() {
        super(SHIPYARD_VIEW_FILE);

        currentShip = player.getShip();
        shipGroup = new ToggleGroup();

        fillLabels();
        buildShipsList();
    }

    /**
     * Buys the selected ship, replaces the player's current ship, adds/deducts
     * the correct amount of funds.
     *
     * @param event A button press
     * @throws Exception 
     */
    @FXML
    public void makeSale(ActionEvent event) throws Exception {
        // TODO:
        // K, here's what needs to happen to beautify this:
        // ShipBar needs to extend javafx.scene.control.Toggle,
        // and then we can use ToggleGroup.getSelectedToggle()
        // rather than this shit
        Map<String, ShipType> ships = Data.SHIPS.get();
        for (ShipBar bar : buyShips.getItems()) {
            if (bar.isSelected()) {
                ShipType ship = ships.get(bar.getKey());
                int playerCash = player.getMoney();
                int price = ship.getPrice() - currentShip.getNetWorth();
                if (!(playerCash < price)) {
                    // TODO: use a dedicated class to facilitate ship upgrades
                    // the same way Transaction  is used for goods
                    Ship newShip = new Ship(ship);
                    player.setShip(newShip);
                    player.setMoney(playerCash - price);
                    // update labels
                    fillLabels();
                    // rebuild ships list (?)
                    currentShip = player.getShip();
                    buildShipsList();
                }
                /*else {
                // TODO: warning: not enough money

                }*/
                // TODO: don't use a for loop here (see above)
                break;
            }
        }
    }

    /**
     * Returns to the planet info screen.
     *
     * @param event A button press
     * @throws Exception 
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        transitionTo(new PlanetController());
    }

    /**
     * Fills labels containing info about the player's funds, current ship, and
     * error info.
     */
    private void fillLabels() {
        this.lblCash.setText(Integer.toString(player.getMoney()));
        Ship s = player.getShip();
        this.shipInfo.setText(s.toString());
        errorLabel.setText("");
    }

    /**
     * Builds the list of ships that the player can buy.
     */
    private void buildShipsList() {
        ObservableList<ShipBar> lstShips = FXCollections.observableArrayList();
        Map<String, ShipType> ships = Data.SHIPS.get();
        for (String s : planet.getShips()) {
            ShipType ship = ships.get(s);
            ShipBar u = new ShipBar();
            u.setKey(s);
            u.setText(ship.getName());
            u.setPrice(ship.getPrice() - currentShip.getNetWorth());
            u.setToggleGroup(shipGroup);
            lstShips.add(u);
        }
        buyShips.setItems(lstShips);
    }
}
