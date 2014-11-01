package edu.gatech.gem5.game.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * BuyBar custom FXML element controller class
 *
 * @author Creston Bunch
 * @author James Park
 */
public class UpgradeBar extends HBox {


    @FXML
    private Label lblItem;
    @FXML
    private Label lblPrice;
    @FXML
    private Button btnUpgrade;

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
     * Adds an event when a button is pressed.
     * @param h 
     */
    public void setOnAction(EventHandler<ActionEvent> h) {
        btnUpgrade.setOnAction(h);
    }

    /**
     * Set the price of the item sold.
     *
     * @param p the price
     */
    public void setPrice(int p) {
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
}
