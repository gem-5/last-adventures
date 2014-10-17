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
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

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
    private double range;
    private Shape marker;

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
        this.marker = marker(range);
        this.addNode(locX, locY, marker);
    }

    @Override
    protected void updatePositions() {
        marker.toBack();
        super.updatePositions();
    }

    @Override
    protected void updateSizes() {
        marker.toBack();
        // don't scale the stroke
        marker.setStrokeWidth(2.0 / camera.getZoom());
        super.updateSizes();
    }

    private Shape marker(double r) {
        Circle c = new Circle();
        c.setRadius(r);
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.GREEN);
        return c;
    }

}
