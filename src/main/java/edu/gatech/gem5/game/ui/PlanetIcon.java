package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import edu.gatech.gem5.game.Planet;

/**
 * An icon representing a planet in the universe map.
 *
 * @author Creston Bunch
 */
public class PlanetIcon extends Region {

    private Ellipse shape;

    /**
     * No arg constructor
     */
    public PlanetIcon() {
        shape = new Ellipse();
        shape.radiusXProperty().bind(this.widthProperty().divide(2));
        shape.radiusYProperty().bind(this.heightProperty().divide(2));
        shape.centerXProperty().bind(this.widthProperty().divide(2));
        shape.centerYProperty().bind(this.heightProperty().divide(2));
        shape.setFill(Color.GREEN);
        getChildren().add(shape);
    }

    /**
     * Construct and set the root of this custom control.
     */
    public PlanetIcon(Planet p) {
        this();
        setCursor(Cursor.HAND);
        setPrefWidth(10);
        setPrefHeight(10);
        Text t = new Text(0, -5, p.getName());
        t.setFont(new Font(10));
        t.setFill(Color.WHITE);
        getChildren().add(t);
    }

}
