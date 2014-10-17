package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;

import edu.gatech.gem5.game.SolarSystem;

/**
 * An icon representing a solar system in the universe map.
 *
 * @author Creston Bunch
 */
public class SolarIcon extends Region {

    private Ellipse shape;

    /**
     * No arg constructor
     */
    public SolarIcon() {
        shape = new Ellipse();
        shape.radiusXProperty().bind(this.widthProperty().divide(2));
        shape.radiusYProperty().bind(this.heightProperty().divide(2));
        shape.centerXProperty().bind(this.widthProperty().divide(2));
        shape.centerYProperty().bind(this.heightProperty().divide(2));
        shape.setFill(Color.WHITE);
        getChildren().add(shape);

    }

    /**
     * Construct and set the root of this custom control.
     */
    public SolarIcon(SolarSystem sys) {
        this();
        setCursor(Cursor.HAND);
        Tooltip t = new Tooltip(sys.getName());
        Tooltip.install(shape, t);
        setPrefWidth(0.5 * sys.getPlanets().size());
        setPrefHeight(0.5 * sys.getPlanets().size());
    }

}
