package edu.gatech.gem5.game.ui;

import java.io.IOException;

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

/**
 * SolarSystemDisplay custom FXML element controller class
 *
 * @author Creston Bunch
 */
public class SolarSystemDisplay extends ExplorableDisplay {

    /**
     * Construct and set the root of this custom control.
     *
     * @param w The width in arbitrary units
     * @param h The height in arbitrary units
     */
    public SolarSystemDisplay(int w, int h) {
        super();
        this.gridWidth = w;
        this.gridHeight = h;
        this.camera = new Camera(gridWidth / 2, gridHeight / 2, 1.0);
    }

    @Override
    public void addNode(int x, int y, Node n) {
        x += gridWidth / 2;
        y += gridHeight / 2;
        super.addNode(x, y, n);
    }
}
