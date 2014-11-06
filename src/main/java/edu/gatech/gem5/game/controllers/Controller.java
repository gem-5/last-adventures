package edu.gatech.gem5.game.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Planet;

/**
 * An abstract class that all controllers must inherit from.
 *
 * @author Creston Bunch
 */
public abstract class Controller implements Initializable {

    protected Parent root;
    protected static Universe universe;
    protected static Character player;
    protected static SolarSystem system;
    protected static Planet planet;

    /**
     * Base constructor that loads the view from an FXML file.
     *
     * @param file The fxml file to load.
     */
    public Controller(String file) {
        try {
            FXMLLoader loader = new FXMLLoader(
                Controller.class.getResource(file)
            );

            loader.setController(this);
            this.root = (Parent) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the view loaded by this controller.
     *
     * @return the view
     */
    public Parent getRoot() {
        return this.root;
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

    /**
     * Run this after the scene has been updated. Useful when you need to know
     * node sizes.
     */
    public void finish() {
    }

    /**
     * Transition to a new Controller
     *
     * @param c The new Controller to load.
     */
    protected void transitionTo(Controller c) {

        Pane oldRoot = (Pane) LastAdventures.getRoot();
        Stage stage = LastAdventures.getStage();
        Scene scene = stage.getScene();

        // LastAdventures.setRoot(c.getRoot());
        root = c.getRoot();

        // make the new scene the same size
        if (oldRoot != null) {
            ((Pane) root).setPrefWidth(oldRoot.getWidth());
            ((Pane) root).setPrefHeight(oldRoot.getHeight());
        }

        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
        scene.getWindow().sizeToScene();

        // LastAdventures.setScene(scene);
        LastAdventures.setRoot(root);
        LastAdventures.setStage(stage);

        c.finish(); // do something after the scene change
    }

}
