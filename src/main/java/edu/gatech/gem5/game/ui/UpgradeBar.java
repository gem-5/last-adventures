package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import edu.gatech.gem5.game.data.DataType;

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
     * @param the label text
     */
    public void setText(String text) {
        this.lblItem.setText(text);
    }


}
