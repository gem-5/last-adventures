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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.Tooltip;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Jack Mueller
 * @author Alex Liu
 */
public class DisplayUniverseController extends Controller {

    @FXML
    AnchorPane map;
    @FXML
    TextField errorLabel;
    @FXML
    GridPane overlay, planetsInfo;

    private Pane root;
    private Universe universe;
    private ObservableList<Node> nodes;
    private SaveFile save;
    private double widthRatio;
    private double heightRatio;
    private int xCoordinate;
    private int yCoordinate;;
    
    private SolarSystem selected;

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
        xCoordinate = save.getSolarSystem().getXCoordinate();
        yCoordinate = save.getSolarSystem().getYCoordinate();
        drawUniverse();
        drawSystemMarker();
        drawShipRange();
        hidePlanetsInfo();
    }
    /**
     * Returns to the planet screen.
     *
     * @param event A button press attempting to change scenes
     * @throws Exception propagates any JavaFX Exception
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
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
                @Override
                public void handle(MouseEvent event) {
                    showPlanetsInfo(system);
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
     *
     */
    @FXML
    private void travelTo() {
        Ship ship = save.getCharacter().getShip();
        int range = ship.getFuel();
        SolarSystem curSS = save.getSolarSystem();
        int x1 = curSS.getXCoordinate();
        int y1 = curSS.getYCoordinate();
        int x2 = selected.getXCoordinate();
        int y2 = selected.getYCoordinate();


        int distance = (int) sqrt(pow((x2 - x1) * widthRatio, 2) + pow((y2 - y1) * heightRatio, 2));
        if ( distance <= range && selected != curSS) {
            save.setSolarSystem(selected);
            ship.setFuel(ship.getFuel() - distance);
            // PSA: save.setSolarSystem() updates the current planet to the
            // first one in the solar system

            Encounter e = new Encounter();

            Turn turn = new Turn();
            turn.pass();
            
            e.getEncounter(save.getPlanet());
            
        } else if (curSS == selected) {
            //no need to take a turn, we're already here
            LastAdventures.swap(new PlanetController());
        } else {
           errorLabel.setText("Out of Range");
        }
    }
    
    /**
     * Makes the overlay GridPane visible which has the planets info GridPane
     * inside of it.
     * 
     * @param system the system whose planets to show information about
     */
    private void showPlanetsInfo(SolarSystem system) {
        hidePlanetsInfo();//so two sets of planets are not on top of eachother
        selected = system;
        double screenWidth = root.getPrefWidth();
        if(system.getXCoordinate() * widthRatio > screenWidth / 2 ) {
            //set to left side of the screen
            AnchorPane.setLeftAnchor(overlay, 0.0);
            AnchorPane.setRightAnchor(overlay, null);
        } else {
            //set to right side of the screen
            AnchorPane.setLeftAnchor(overlay, null);
            AnchorPane.setRightAnchor(overlay, 0.0);
        }
        List<Planet> planets = system.getPlanets();
        Circle circleNode;
        
        for (int i = 0; i < planets.size(); i++) {
            
            //planetName in first row
            addLabelToInfo(planets.get(i).getName(), i, 0);
            
            circleNode = new Circle(30);
            circleNode.setFill(Color.RED);
            //planeImage in second row
            addPlanetToInfo(circleNode, i, 1);
            
            //environment in third row
            addLabelToInfo(planets.get(i).getEnvironment().getName(), i, 2);            
            
            //government in forth row
            addLabelToInfo(planets.get(i).getGovernment().getName(), i, 3);
            
            //tech level in fifth row
            addLabelToInfo(planets.get(i).getTechLevel().getName(), i, 4);

        }
        overlay.setVisible(true);   //shows overlay
        overlay.toFront();
    }
    
    @FXML
    private void hidePlanetsInfo() {
        overlay.setVisible(false);  //hides hide/go buttons as well
        planetsInfo.getChildren().retainAll();  //retain nothing
        errorLabel.setText(""); //reset error message
    }

    private void addLabelToInfo(String text, int col, int row) {
        Label labelNode;
        labelNode = new Label(text);
        labelNode.getStyleClass().add("overlayLabel");
        GridPane.setConstraints(labelNode, col, row, 1, 1, HPos.CENTER, VPos.CENTER);
        planetsInfo.add(labelNode, col, row);             
    }
    
    private void addPlanetToInfo(Node circle, int col, int row) {
        GridPane.setConstraints(circle, col, row, 1, 1, HPos.CENTER, VPos.CENTER);
        planetsInfo.add(circle, col, row);             
    }
            
}
