/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Jack Mueller
 */
public class DisplayUniverseController implements Initializable {

    @FXML
    AnchorPane root;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Universe universe = LastAdventures.getCurrentSaveFile().getUniverse();

        double widthRatio = root.getPrefWidth()/universe.getWidth();
        double heightRatio = root.getPrefHeight()/universe.getHeight();
        ObservableList<Node> children = root.getChildren();
        for (SolarSystem system : universe.getUniverse()) {
            Image img = new Image("img/solarSystemSmall.png");
            ImageView imgView = new ImageView(img);
            int xCoordinate = system.getXCoordinate();
            int yCoordinate = system.getYCoordinate();
            imgView.setLayoutX(xCoordinate * widthRatio);
            imgView.setLayoutY(yCoordinate * heightRatio);
            children.add(imgView);
        }
    }    
    
}
