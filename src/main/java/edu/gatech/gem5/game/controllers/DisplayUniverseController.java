package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jack Mueller
 */
public class DisplayUniverseController implements Initializable {

    @FXML
    AnchorPane root;
    
    
        /**
     * Changes screens
     * 
     * @param event A button press attempting to change scenes
     * @throws Exception if the scene resource is not found
     */
    @FXML
    public void changeScenes(MouseEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("/market.fxml"));
     
        stage.setScene(new Scene((Pane) root));
    }
    
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
        
                    //this is to print out the character once it is made
            System.out.println(LastAdventures.getCurrentSaveFile());
    }    
    
}
