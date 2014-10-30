package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Save box custom UI element for the load game screen.
 *
 * @author Creston Bunch
 */
public class SaveBox extends HBox {

    /**
     * The name of the savebox
     */
    @FXML
    private Label name;

    /**
     * The path of a SaveGame file.
     */
    private String path;

    /**
     * Construct and set the root of this custom control.
     */
    public SaveBox() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/savebox.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);
        this.getStyleClass().add("save-box");

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Set the label of this save box.
     *
     * @param text The label
     */
    public void setLabel(String text) {
        this.name.setText(text);
    }

    /**
     * Set the path of the save game file.
     *
     * @param path The path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get the path of this save box.
     *
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

}
