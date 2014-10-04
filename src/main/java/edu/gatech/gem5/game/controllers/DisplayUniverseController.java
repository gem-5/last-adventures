package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.Ship;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.List;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.Tooltip;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jack Mueller
 * @author Alex Liu
 */
public class DisplayUniverseController extends Controller {

    @FXML
    AnchorPane map;

    private Pane root;
    private Universe universe;
    private ObservableList<Node> nodes;
    private SaveFile save;
    private double widthRatio;
    private double heightRatio;
    private int xCoordinate;
    private int yCoordinate;;

    public static final String UNIVERSE_VIEW_FILE = "/fxml/displayUniverse.fxml";

    /**
     * Construct the universe display controller.
     */
    public DisplayUniverseController() {
        super(UNIVERSE_VIEW_FILE);
        universe = LastAdventures.getCurrentSaveFile().getUniverse();
        root = (Pane) LastAdventures.getRoot();
        nodes = map.getChildren();
        widthRatio = root.getPrefWidth() / universe.getWidth();
        heightRatio = root.getPrefHeight() / universe.getHeight();
        save = LastAdventures.getCurrentSaveFile();
        xCoordinate = save.getPlanet().getSolarySystem().getXCoordinate();
        yCoordinate = save.getPlanet().getSolarySystem().getYCoordinate();
        drawUniverse();
        drawSystemMarker();
        drawShipRange();
    }
    /**
     * Returns to the planet screen.
     *
     * @param event A button press attempting to change scenes
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        System.out.println("World");
        LastAdventures.swap(new PlanetController());
    }

    private void drawUniverse() {
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

            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    travelTo(system);
                }
            });
            nodes.add(circle);
        }
    }

    private void drawSystemMarker() {
        Image img = new Image("img/currentSystem.png");
        int dx = (int) img.getWidth() / 2;
        int dy = (int) img.getHeight() / 2;
            ImageView imgView = new ImageView(img);
            imgView.setLayoutX(xCoordinate * widthRatio - dx);
            imgView.setLayoutY(yCoordinate * heightRatio - dy);
            nodes.add(imgView);
            imgView.toBack();
    }

    private void drawShipRange() {
        // show the ship range
        Ship s = save.getCharacter().getShip();
        double range = s.getType().getRange();
        Circle circle = new Circle();
        circle.setCenterX(xCoordinate * widthRatio);
        circle.setCenterY(yCoordinate * heightRatio);
        circle.setRadius(range);
        circle.setStroke(Color.GREEN);
        circle.setFill(Color.TRANSPARENT);
        nodes.add(circle);
        circle.toBack();
    }

    /**
     * Sets the current planet and solar system to the save file, then changes to
     * the PlanetController scene
     * @param sys
     */
    private void travelTo(SolarSystem sys) {
        double range = save.getCharacter().getShip().getType().getRange();
        SolarSystem curSS = save.getSolarSystem();
        int x1 = curSS.getXCoordinate();
        int y1 = curSS.getYCoordinate();
        int x2 = sys.getXCoordinate();
        int y2 = sys.getXCoordinate();

        //Test code, remove later
        System.out.println("Range: " + Double.toString(range));
        System.out.println("x1 = " + Integer.toString(x1) + ", y1 = " + Integer.toString(y1));
        System.out.println("x2 = " + Integer.toString(x2) + ", y2 = " + Integer.toString(y2));
        System.out.println("Distance: " + Double.toString(sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2))));
        System.out.println();

        if (sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2)) <= range) {
            SaveFile current = LastAdventures.getCurrentSaveFile();
            current.setSolarSystem(sys);

            //Random planet from solar system selected
            //@TODO Player not actually travelling to this planet, implement later!!
            current.setCurrentPlanet(sys.getPlanets().get(0));

            LastAdventures.swap(new PlanetController());
        } else {
            System.out.println("Not in range.");
        }
    }
}
