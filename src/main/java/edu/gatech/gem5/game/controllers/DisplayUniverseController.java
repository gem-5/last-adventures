package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.ui.UniverseDisplay;
import edu.gatech.gem5.game.ui.ExplorableDisplay;
import edu.gatech.gem5.game.ui.StarIcon;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * FXML Controller class
 *
 * @author Jack Mueller
 * @author Alex Liu
 * @author Creston Bunch
 */
public class DisplayUniverseController extends Controller {

    @FXML
    TextField errorLabel;

    private final ExplorableDisplay map;
    private final UpdateListener updateListener;

    public static final String UNIVERSE_VIEW_FILE = "/fxml/travel.fxml";

    /**
     * Construct the universe display controller.
     */
    public DisplayUniverseController() {
        super(UNIVERSE_VIEW_FILE);

        // construct a universe display
        map = new UniverseDisplay(universe, system, player.getShip());
        // add the map to the scene
        ((BorderPane) root).setCenter(map);

        updateListener = new UpdateListener();
        addListeners();
    }

    @Override
    public void finish() {
        // populate the universe display
        for (SolarSystem s : universe.getUniverse().values()) {
            int x = s.getXCoordinate();
            int y = s.getYCoordinate();
            int size = s.getPlanets().size();
            // Load an image to represent the solar system
            StarIcon sys = new StarIcon(s);
            sys.setCursor(Cursor.HAND);
            sys.setOnMouseClicked(new TravelHandler(s));
            // add the system to the map
            map.addNode(x, y, sys);
            // TODO: custom tooltips
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
        transitionTo(new DisplaySystemController());
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

        private final SolarSystem sys;

        public TravelHandler(SolarSystem sys) {
            this.sys = sys;
        }

        @Override
        public void handle(MouseEvent e) {
            Ship ship = player.getShip();
            int range = ship.getFuel();
            if (distance() <= range) {
                transitionTo(new DisplaySystemController(sys));
            } else {
                errorLabel.setText("Out of Range");
            }
        }

        private double distance() {
            SolarSystem here = system;
            SolarSystem there = sys;
            int dx = there.getXCoordinate() - here.getXCoordinate();
            int dy = there.getYCoordinate() - here.getYCoordinate();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

}
