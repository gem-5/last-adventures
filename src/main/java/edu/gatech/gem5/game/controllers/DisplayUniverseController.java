package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.Ship;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
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

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.Tooltip;
import javafx.scene.Cursor;

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
        ArrayList<SolarSystem> systems = universe.getUniverse();
        for (SolarSystem system : systems) {
            Circle circle = new Circle();
            int xCoordinate = system.getXCoordinate();
            int yCoordinate = system.getYCoordinate();
            circle.setCenterX(xCoordinate * widthRatio);
            circle.setCenterY(yCoordinate * heightRatio);
            circle.setRadius(2.0 * system.getPlanets().size());
            circle.setFill(Color.WHITE);
            circle.setCursor(Cursor.HAND);
            Tooltip t = new Tooltip("Planets: " + system.getPlanets().size());
            Tooltip.install(circle, t);

            children.add(circle);
        }
        
        //Randomly choose a planet to start on
        Random rand = new Random();
        SolarSystem startSystem = systems.get(rand.nextInt(systems.size()));
        SaveFile save = LastAdventures.getCurrentSaveFile();
        
        Image img = new Image("img/currentSystem.png");
        int dx = (int) img.getWidth() / 2;
        int dy = (int) img.getHeight() / 2;
            ImageView imgView = new ImageView(img);
            int xCoordinate = startSystem.getXCoordinate();
            int yCoordinate = startSystem.getYCoordinate();
            imgView.setLayoutX(xCoordinate * widthRatio - dx);
            imgView.setLayoutY(yCoordinate * heightRatio - dy);
            children.add(imgView);

        // show the ship range
        Ship s = save.getCharacter().getShip();
        double range = s.getType().getRange();
        Circle circle = new Circle();
        circle.setCenterX(xCoordinate * widthRatio);
        circle.setCenterY(yCoordinate * heightRatio);
        circle.setRadius(range);
        circle.setStroke(Color.GREEN);
        circle.setFill(Color.TRANSPARENT);
        children.add(circle);
        
        Planet startPlanet = startSystem.getPlanets().get(0);

        save.setCurrentPlanet(startPlanet);
        //this is to print out the character once it is made
        //System.out.println(LastAdventures.getCurrentSaveFile());
    }    

}
