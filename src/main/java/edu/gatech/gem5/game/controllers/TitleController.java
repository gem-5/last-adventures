/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Creston Bunch
 * @author James Jong Han Park
 */
public class TitleController implements Initializable {

    @FXML
    private Parent root;

    @FXML
    private BorderPane defaultScene;

    @FXML
    private GridPane gridPane;

    /**
     * Changes the scene based on the button pressed.
     *
     * @param event a button press
     * @throws Exception if the scene resource is not found
     */
    @FXML
    private void changeScenes(ActionEvent event) throws Exception {
        //gets this scene's stage

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String id = ((Button) (event.getSource())).idProperty().get();
        Node root = this.root;
        //loads the create FXML file into root
        if (id.equals("newButton")) {
            LastAdventures.createNewSaveFile();
            root = FXMLLoader.load(getClass().getResource("/create.fxml"));
        } else if (id.equals("continueButton")) {
            //TODO
            root = FXMLLoader.load(getClass().getResource("/title.fxml"));
        } else if (id.equals("loadButton")) {
            //TODO
            root = FXMLLoader.load(getClass().getResource("/title.fxml"));
        }

        //sets the stage to root scene
        stage.setScene(new Scene((Pane) root));

    }

    /**
     * Initialize the controller.
     *
     * @param url The location to resolve all relative paths for the root
     * object.
     * @param rb The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        gridPane.setOpacity(0);

        // Adds fade-in animation when mouse enters the screen.
        defaultScene.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                new FadeHandler(gridPane);
            }
        });

        // Adds fade-out animation when mouse leaves the screen.
        defaultScene.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                new FadeHandler(gridPane, 0, .5, 1, 0);
            }
        });
    }

}
