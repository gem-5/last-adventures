package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.Ship;
import java.util.List;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
public class DisplayUniverseController extends Controller {

    @FXML
    AnchorPane root;

    private Universe universe;

    public static final String UNIVERSE_VIEW_FILE = "displayUniverse.fxml";

    /**
     * Construct the universe display controller.
     */
    public DisplayUniverseController() {
        super(UNIVERSE_VIEW_FILE);
        universe = LastAdventures.getCurrentSaveFile().getUniverse();
        drawUniverse();
        drawSystemMarker();
        drawShipRange();
    }

    private void drawUniverse() {
        Pane root = (Pane) LastAdventures.getRoot();
        double widthRatio = root.getPrefWidth()/universe.getWidth();
        double heightRatio = root.getPrefHeight()/universe.getHeight();
        ObservableList<Node> children = root.getChildren();
        List<SolarSystem> systems = universe.getUniverse();
        for (SolarSystem system : systems) {
            Circle circle = new Circle();
            int xCoordinate = system.getXCoordinate();
            int yCoordinate = system.getYCoordinate();
            circle.setCenterX(xCoordinate * widthRatio);
            circle.setCenterY(yCoordinate * heightRatio);
            circle.setRadius(2.0 * system.getPlanets().size());
            circle.setFill(Color.WHITE);
            circle.setCursor(Cursor.HAND);
            Tooltip t = new Tooltip(
                system.getName() + "\n" +
                "Planets: " + system.getPlanets().size()
            );
            Tooltip.install(circle, t);

            children.add(circle);
        }
    }

    private void drawSystemMarker() {
        Pane root = (Pane) LastAdventures.getRoot();
        double widthRatio = root.getPrefWidth()/universe.getWidth();
        double heightRatio = root.getPrefHeight()/universe.getHeight();
        ObservableList<Node> children = root.getChildren();
        SaveFile save = LastAdventures.getCurrentSaveFile();
        int xCoordinate = save.getPlanet().getSolarySystem().getXCoordinate();
        int yCoordinate = save.getPlanet().getSolarySystem().getYCoordinate();
        Image img = new Image("img/currentSystem.png");
        int dx = (int) img.getWidth() / 2;
        int dy = (int) img.getHeight() / 2;
            ImageView imgView = new ImageView(img);
            imgView.setLayoutX(xCoordinate * widthRatio - dx);
            imgView.setLayoutY(yCoordinate * heightRatio - dy);
            children.add(imgView);
            imgView.toBack();
    }

    private void drawShipRange() {
        Pane root = (Pane) LastAdventures.getRoot();
        double widthRatio = root.getPrefWidth()/universe.getWidth();
        double heightRatio = root.getPrefHeight()/universe.getHeight();
        SaveFile save = LastAdventures.getCurrentSaveFile();
        int xCoordinate = save.getPlanet().getSolarySystem().getXCoordinate();
        int yCoordinate = save.getPlanet().getSolarySystem().getYCoordinate();
        ObservableList<Node> children = root.getChildren();
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
        circle.toBack();
    }

    /**
     * Changes screens
     *
     * @param event A button press attempting to change scenes
     * @throws Exception if the scene resource is not found
     */
    @FXML
    public void changeScenes(MouseEvent event) throws Exception {
        LastAdventures.swap(new MarketController());
    }
}
