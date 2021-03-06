package edu.gatech.gem5.game.ui;


import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

/**
 * BuyBar custom FXML element controller class.
 *
 * @author Creston Bunch
 */
public class BuyBar extends HBox {

    /**
     * The key for the product being sold.
     */
    private String product;

    /**
     * The label for holding the product name.
     */
    @FXML
    private Label lblItem;

    /**
     * The label for holding the product price.
     */
    @FXML
    private Label lblPrice;

    /**
     * The label for holding the product quantitiy.
     */
    @FXML
    private Label lblQuantity;

    /**
     * The slider for selecting a quatitiy.
     */
    @FXML
    private Slider sldQuantity;

    /**
     * The label for holding the max number.
     */
    @FXML
    private Label lblMax;

    /**
     * Construct and set the root of this custom control.
     */
    public BuyBar() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/buybar.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        sldQuantity.setShowTickMarks(false);
        sldQuantity.setShowTickLabels(false);
        sldQuantity.setMajorTickUnit(1);
        sldQuantity.setBlockIncrement(1);
        sldQuantity.setSnapToTicks(true);

        sldQuantity.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                lblQuantity.setText(Integer.toString(newValue.intValue()));
            }
        });
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
        sldQuantity.setMin(0);
        sldQuantity.setMax(q);
        lblQuantity.setText("0");
        lblMax.setText(Integer.toString(q));
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

    /**
     *
     * @return the value of the slider
     */
    public int getSliderValue() {
        return (int) sldQuantity.valueProperty().get();
    }

    /**
     * @return the good key
     */
    public String getProduct() {
        return this.product;
    }
}
