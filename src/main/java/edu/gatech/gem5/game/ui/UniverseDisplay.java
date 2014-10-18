package edu.gatech.gem5.game.ui;

import java.io.IOException;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;

import edu.gatech.gem5.game.SaveFile;

/**
 * UniverseDisplay custom FXML element controller class
 *
 * @author Creston Bunch
 */
public class UniverseDisplay extends ExplorableDisplay {

    private SaveFile save;
    private int locX;
    private int locY;
    private int range;
    private ImageView marker;

    public static final String MARKER_PATH = "/img/marker.png";

    /**
     * Update the universe display with a save file.
     *
     * @param save The save file with the universe to display.
     */
    public UniverseDisplay(SaveFile save) {
        super();
        this.save = save;
        // the display grid is limited by the size of the universe
        this.gridWidth = this.save.getUniverse().getWidth();
        this.gridHeight = this.save.getUniverse().getHeight();
        // set the default x and y coordinates
        locX = save.getSolarSystem().getXCoordinate();
        locY = save.getSolarSystem().getYCoordinate();
        range = save.getCharacter().getShip().getFuel();
        this.camera = new Camera(locX, locY, 10.0);
        this.marker = new ImageView(MARKER_PATH);
        this.marker.setFitWidth(range * 2);
        this.marker.setFitHeight(range * 2);
        this.marker.setMouseTransparent(true);
        this.addNode(locX, locY, marker);
    }
}
