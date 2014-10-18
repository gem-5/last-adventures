package edu.gatech.gem5.game.ui;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.data.ShipType;

import java.io.IOException;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * BuyBar custom FXML element controller class
 *
 * @author Creston Bunch
 */
public class UpgradeBar extends HBox {

    private String product;
    private int price;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblPrice;

    /**
     * Construct and set the root of this custom control.
     */
    public UpgradeBar() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/upgradebar.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Set the key for the item sold.
     *
     * @param key the key
     */
    public void setKey(String key) {
        this.product = key;
    }

    /**
     * Set the price of the item sold.
     *
     * @param p the price
     */
    public void setPrice(int p) {
        this.price = p;
        this.lblPrice.setText("($" + p + ")");
    }

    /**
     * Set the label text.
     *
     * @param text the label text
     */
    public void setText(String text) {
        this.lblItem.setText(text);
    }
    
    @FXML
    private void makeSale(ActionEvent event) throws Exception {
        SaveFile save = LastAdventures.getCurrentSaveFile();
        Map<String, ShipType> ships = LastAdventures.data.get(ShipType.KEY);
        ShipType ship = ships.get(product);
        int playerCash = save.getCharacter().getMoney();
        if (playerCash < price) {
            
        } else {
            Ship newShip = new Ship(ship);
            save.getCharacter().setShip(newShip);
            save.getCharacter().setMoney(playerCash - price);
        }
    }
}
