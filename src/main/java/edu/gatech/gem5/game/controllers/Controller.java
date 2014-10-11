package edu.gatech.gem5.game.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;

import edu.gatech.gem5.game.LastAdventures;

/**
 * An abstract class that all controllers must inherit from.
 *
 * @author Creston Bunch
 */

public abstract class Controller implements Initializable {

    LastAdventures game;
    Parent root;

    /**
     * Base constructor that loads the view from an FXML file.
     *
     * @param file The fxml file to load.
     */
    public Controller (String file) {

        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(file)
            );

            loader.setController(this);
            this.root = loader.load();
            this.game = game;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the view loaded by this controller.
     *
     * @return the view
     */
    public Scene getScene() {
        return new Scene(this.root);
    }

    /**
     * Override this piece of junk from the Initializable interface.
     *
     * @param url The location to resolve all relative paths for the root
     * object.
     * @param rb The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // do nothing
    }

}
