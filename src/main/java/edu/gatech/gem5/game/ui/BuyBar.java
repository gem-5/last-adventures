package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import edu.gatech.gem5.game.data.DataType;

/**
 * BuyBar custom FXML element controller class
 *
 * @author Creston Bunch
 */
public class BuyBar extends HBox {

    private String product;
    private int quantity;
    private int price;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblPrice;

    @FXML
    private TextField amount;

    /**
     * Construct and set the root fo this custom control.
     *
     * @param product The product of this buybar
     * @param quantity The maximum number of items that can be traded.
     */
    public BuyBar() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/buybar.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void decrement(MouseEvent event) throws Exception {
        // not implemented
    }

    @FXML
    public void increment(MouseEvent event) throws Exception {
        // not implemented
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
     * Set the quantity of the item sold.
     *
     * @param q the quantity
     */
    public void setQuantity(int q) {
        this.quantity = q;
    }

    /**
     * Set the price of the item sold.
     *
     * @param p the price
     */
    public void setPrice(int p) {
        this.price = p;
        this.lblPrice.setText("(" + p + ")");
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
