package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * An icon representing a planet in the universe map.
 *
 * @author Creston Bunch
 */
public class PlanetIcon extends Region {

    private Ellipse shape;

    /**
     * Construct and set the root of this custom control.
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

}
