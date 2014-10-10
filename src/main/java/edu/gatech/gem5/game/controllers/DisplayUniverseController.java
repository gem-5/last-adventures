package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Encounter;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.Turn;
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
     * @throws Exception propogates any JavaFX Exception
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
            SolarSystem curSS = save.getSolarSystem();
            int x1 = curSS.getXCoordinate();
            int y1 = curSS.getYCoordinate();
            int x2 = system.getXCoordinate();
            int y2 = system.getYCoordinate();
            int distance = (int) sqrt(pow((x2 - x1) * widthRatio, 2) + pow((y2 - y1) * heightRatio, 2));
            Tooltip t = new Tooltip(
                system.getName() + "\n" +
                "Planets: " + system.getPlanets().size() + "\n" +
                "Travel Cost: " + distance + " fuel"
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
        int range = s.getFuel();
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
        Ship ship = save.getCharacter().getShip();
        int range = ship.getFuel();
        SolarSystem curSS = save.getSolarSystem();
        int x1 = curSS.getXCoordinate();
        int y1 = curSS.getYCoordinate();
        int x2 = sys.getXCoordinate();
        int y2 = sys.getYCoordinate();


        int distance = (int) sqrt(pow((x2 - x1) * widthRatio, 2) + pow((y2 - y1) * heightRatio, 2));
        if ( distance <= range && distance > 0.001) {
            save.setSolarSystem(sys);
            ship.setFuel(ship.getFuel() - distance);
            //Random planet from solar system selected
            //@TODO Player not actually travelling to this planet, implement later!!
            Planet p = sys.getPlanets().get(0);
            save.setCurrentPlanet(p);

            Encounter e = new Encounter();

            Turn turn = new Turn();
            turn.pass();
            e.getEncounter(p);
        } else if (distance <= 0.001) {
            // return to current planet, no turn passes.
            System.out.println("World");
            LastAdventures.swap(new PlanetController());
        } else {
            //@TODO printing to an error label should go here
            System.out.println("Not in range.");
        }

    }
}
