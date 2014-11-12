package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.EncounterManager;
import edu.gatech.gem5.game.Turn;
import edu.gatech.gem5.game.ui.SolarSystemDisplay;
import edu.gatech.gem5.game.ui.AbstractExplorableDisplay;
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
 * FXML Controller class.
 *
 * @author Creston Bunch
 */
public class DisplaySystemController extends AbstractController {

    /**
     * The solar system the user is peeking at before they commit to traveling
     * there.
     */
    private SolarSystem peek;
    /**
     * The explorable UI element being used.
     */
    private AbstractExplorableDisplay map;
    /**
     * Does absolutely nothing. TODO get rid of.
     */
    private UpdateListener updateListener;

    /**
     * Contains every node in the display besides the title. This includes
     * the sun, planets, and the planets' names.
     */
    @FXML
    Pane content;

    /**
     * This label contains the title of the SolarSystem being peeked at.
     */
    @FXML
    Label title;

    /**
     * The fxml view file associated with this controller.
     */
    public static final String SYSTEM_VIEW_FILE = "/fxml/system.fxml";

    /**
     * No arg constructor.
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

    @Override
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
     * Adds the 2 necessary listeners to the scene, width and height of the
     * scene.
     */
    private void addListeners() {
        LastAdventures.getScene().widthProperty().addListener(updateListener);
        LastAdventures.getScene().heightProperty().addListener(updateListener);
    }

    /**
     * Used to remove the listeners when the scene is left, so there will be no
     * listeners in the scene the next time the scene is shown.
     */
    private void removeListeners() {
        LastAdventures.getScene().widthProperty().addListener(updateListener);
        LastAdventures.getScene().heightProperty().addListener(updateListener);
    }

    /**
     * An update listener that does absolutely nothing. TODO remove.
     */
    private class UpdateListener implements ChangeListener<Object> {

        @Override
        public void changed(ObservableValue<? extends Object> obs,
                Object oldValue,
                Object newValue) {
            map.update();
        }
    };

    /**
     * Handles the mouse click of a planet that indicates the desire to travel 
     * to that planet.
     */
    private class TravelHandler implements EventHandler<MouseEvent> {

        /**
         * The solar system being peeked at in the controller.
         */
        private SolarSystem sys;
        /**
         * The index of the planet that was picked in the SolarSystem.
         */
        private int p;

        /**
         * 
         * @param s the solar system being traveled in
         * @param index the index of the planet that was clicked in sys
         */
        public TravelHandler(SolarSystem s, int index) {
            this.sys = s;
            this.p = index;
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

        /**
         * Calculates the Euclidean distance between the solar system being
         * traveled to and the one currently docked in.
         * 
         * @return the distance between the player's location and the solar
         * system being peeked at.
         */
        private double distance() {
            SolarSystem here = DisplaySystemController.this.peek;
            SolarSystem there = system;
            int dx = there.getXCoordinate() - here.getXCoordinate();
            int dy = there.getYCoordinate() - here.getYCoordinate();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

}
