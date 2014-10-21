package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.ui.ShipBar;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Alex
 */
public class ShipyardController extends Controller {
    @FXML
    AnchorPane root;
    @FXML
    Button universe;
    @FXML
    private Label errorLabel;
    @FXML
    private Label lblCash;
    @FXML
    private Label shipInfo;
    @FXML
    private ListView<ShipBar> buyShips;

    private ToggleGroup shipGroup;

    private Planet planet;
    private Ship currentShip;

    public static final String SHIPYARD_VIEW_FILE = "/fxml/shipyard.fxml";

    public ShipyardController() {
        super(SHIPYARD_VIEW_FILE);

        SaveFile save = LastAdventures.getCurrentSaveFile();
        planet = save.getPlanet();
        currentShip = save.getCharacter().getShip();
        shipGroup = new ToggleGroup();

        fillLabels();
        buildShipsList();
    }

    @FXML
    public void makeSale(ActionEvent event) throws Exception {
        // TODO:
        // K, here's what needs to happen to beautify this:
        // ShipBar needs to extend javafx.scene.control.Toggle,
        // and then we can use ToggleGroup.getSelectedToggle()
        // rather than this shit
        SaveFile save = LastAdventures.getCurrentSaveFile();
        Map<String, ShipType> ships = LastAdventures.data.get(ShipType.KEY);
        for (ShipBar bar : buyShips.getItems()) {
            if (bar.isSelected()) {
                ShipType ship = ships.get(bar.getKey());
                int playerCash = save.getCharacter().getMoney();
                int price = ship.getPrice();
                if (playerCash < price) {
                    // TODO: warning: not enough money
                } else {
                    // TODO: use a dedicated class to facilitate ship upgrades
                    // the same way Transaction  is used for goods
                    Ship newShip = new Ship(ship);
                    save.getCharacter().setShip(newShip);
                    save.getCharacter().setMoney(playerCash - price);
                    // update labels
                    fillLabels();
                }
                // TODO: don't use a for loop here (see above)
                break;
            }
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        LastAdventures.swap(new PlanetController());
    }

    private void fillLabels() {
        SaveFile save = LastAdventures.getCurrentSaveFile();
        this.lblCash.setText(
                ((Integer) save.getCharacter().getMoney()).toString()
        );
        Ship s = save.getCharacter().getShip();
        this.shipInfo.setText(s.toString());
        errorLabel.setText("");
    }

    private void buildShipsList() {
        ObservableList<ShipBar> lstShips = FXCollections.observableArrayList();
        Map<String, ShipType> ships = LastAdventures.data.get(ShipType.KEY);
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
