package edu.gatech.gem5.game.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * ShipBar custom FXML element controller class.
 *
 * @author Creston Bunch
 */
public class ShipBar extends HBox {

    /**
     * The key of the ship this bar is holding.
     */
    private String product;

    /**
     * The label for the ship name.
     */
    @FXML
    private Label lblItem;

    /**
     * The label for the ship price.
     */
    @FXML
    private Label lblPrice;

    /**
     * A radio button to select this ship.
     */
    @FXML
    private RadioButton rdbSelect;

    /**
     * Construct and set the root of this custom control.
     */
    public ShipBar() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/shipbar.fxml")
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
     * Return the key for the item sold.
     *
     * @return the key
     */
    public String getKey() {
        return this.product;
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
     * Set the toggle group for the radio button.
     *
     * @param group the group
     */
    public void setToggleGroup(ToggleGroup group) {
        this.rdbSelect.setToggleGroup(group);
    }

    /**
     * Check if the radiobutton is selected.
     *
     * @return true or false.
     */
    public boolean isSelected() {
        return this.rdbSelect.isSelected();
    }
}
