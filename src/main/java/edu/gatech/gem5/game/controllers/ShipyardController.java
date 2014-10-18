package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.ui.UpgradeBar;
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
    private ListView<UpgradeBar> buyShips;
    @FXML
    private ToggleGroup shipBuy;
    
    private Planet planet;
    private Ship currentShip;
    
    public static final String SHIPYARD_VIEW_FILE = "/fxml/shipyard.fxml";
    
    public ShipyardController() {
        super(SHIPYARD_VIEW_FILE);
        
        SaveFile save = LastAdventures.getCurrentSaveFile();
        planet = save.getPlanet();
        currentShip = save.getCharacter().getShip();

        fillLabels();
        buildShipsList();
    }
    
    public void makePurchase(ActionEvent event) throws Exception {
        for (UpgradeBar ships : buyShips.getItems()) {
            
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
        this.shipInfo.setText(currentShip.toString());
        errorLabel.setText("");
    }
    
    private void buildShipsList() {
        ObservableList<UpgradeBar> lstShips = FXCollections.observableArrayList();
        Map<String, ShipType> ships = LastAdventures.data.get(ShipType.KEY);
        for (String s : planet.getShips()) {
            ShipType ship = ships.get(s);
            UpgradeBar u = new UpgradeBar();
            u.setKey(s);
            u.setText(ship.getName());
            u.setPrice(ship.getPrice() - currentShip.getNetWorth());
            lstShips.add(u);
        }
        buyShips.setItems(lstShips);
    }
}
