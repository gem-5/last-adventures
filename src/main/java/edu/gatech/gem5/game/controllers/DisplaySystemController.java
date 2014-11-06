package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.EncounterManager;
import edu.gatech.gem5.game.Turn;
import edu.gatech.gem5.game.ui.SolarSystemDisplay;
import edu.gatech.gem5.game.ui.ExplorableDisplay;
import edu.gatech.gem5.game.ui.SolarIcon;
import edu.gatech.gem5.game.ui.PlanetIcon;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Label;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * FXML Controller class
 *
 * @author Creston Bunch
 */
public class DisplaySystemController extends Controller {

    private SolarSystem peek;
    private ExplorableDisplay map;
    private UpdateListener updateListener;

    @FXML
    Pane content;

    @FXML
    Label title;

    public static final String SYSTEM_VIEW_FILE = "/fxml/system.fxml";

    /**
     * No arg constructor
     */
    public DisplaySystemController() {
        this(system);
    }

    /**
     * Construct the solar system display controller.
     *
     * @param sys The system to display.
     */
    public DisplaySystemController(SolarSystem sys) {
        super(SYSTEM_VIEW_FILE);
        this.peek = sys;
        // construct a solar system display
        map = new SolarSystemDisplay(200, 200);
        // add the map to the scene
        ((BorderPane) root).setCenter(map);

        title.setText(this.peek.getName());

        updateListener = new UpdateListener();
        addListeners();
    }

    public void finish() {
        // add a sun
        SolarIcon sun = new SolarIcon(peek);
        sun.setPrefWidth(100);
        sun.setPrefHeight(100);
        map.addNode(0, 0, sun);
        // add planets
        double theta = 0;
        double r = 60;
        int i = 0;
        for (Planet s : peek.getPlanets()) {
            // add the planets in a circle
            theta += 2 * Math.PI / peek.getPlanets().size();
            r += 15;
            int x = (int) Math.round(Math.cos(theta) * r);
            int y = (int) Math.round(Math.sin(theta) * r);
            // create the visual planet representation
            PlanetIcon p = new PlanetIcon(s);
            p.setOnMouseClicked(new TravelHandler(peek, i));

            // add it to the map
            map.addNode(x, y, p);
            i++;
        }
        map.update();
    }

    /**
     * Returns to the planet screen.
     *
     * @param event A button press attempting to change scenes
     * @throws Exception propagates any JavaFX Exception
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        removeListeners();
        transitionTo(new DisplayUniverseController());
    }

    /**
     * Sets the current planet and solar system to the save file, then changes
     * to the PlanetController scene
     *
     */
    @FXML
    private void travelTo() {
        /*
         Ship ship = save.getCharacter().getShip();
         int range = ship.getFuel();
         SolarSystem curSS = save.getSolarSystem();
         int x1 = curSS.getXCoordinate();
         int y1 = curSS.getYCoordinate();
         int x2 = selected.getXCoordinate();
         int y2 = selected.getYCoordinate();


         int distance = distance(x1, y1, x2, y2);
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
         */
    }

    private void addListeners() {
        LastAdventures.getScene().widthProperty().addListener(updateListener);
        LastAdventures.getScene().heightProperty().addListener(updateListener);
    }

    private void removeListeners() {
        LastAdventures.getScene().widthProperty().addListener(updateListener);
        LastAdventures.getScene().heightProperty().addListener(updateListener);
    }

    private class UpdateListener implements ChangeListener<Object> {

        @Override
        public void changed(ObservableValue<? extends Object> obs,
                Object oldValue,
                Object newValue) {
            map.update();
        }
    };

    private class TravelHandler implements EventHandler<MouseEvent> {

        private SolarSystem sys;
        private int p;

        public TravelHandler(SolarSystem sys, int p) {
            this.sys = sys;
            this.p = p;
        }

        @Override
        public void handle(MouseEvent e) {
            Ship ship = player.getShip();
            // deduct fuel
            int cost = (int) Math.floor(distance());
            ship.setFuel(ship.getFuel() - cost);

            // cache current solar system in this snazzy variable
            SolarSystem here = system;

            // update global controller vars
            system = peek;
            planet = system.getPlanetAt(p);

            // this is our new solar system location
            SolarSystem there = system;
            // only make a turn when switching solar systems
            if (here != there) {
                Turn turn = new Turn(universe);
                turn.pass();
                EncounterManager trip = new EncounterManager(player, planet);
                trip.nextEncounter();
            } else {
                transitionTo(new PlanetController());
            }
        }

        private double distance() {
            SolarSystem here = DisplaySystemController.this.peek;
            SolarSystem there = system;
            int dx = there.getXCoordinate() - here.getXCoordinate();
            int dy = there.getYCoordinate() - here.getYCoordinate();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

}
