package edu.gatech.gem5.game.ui;


import edu.gatech.gem5.game.SolarSystem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * An icon representing a solar system in the universe map.
 *
 * @author Creston Bunch
 */
public class SolarIcon extends Region {

    /**
     * The image representing this icon.
     */
    private ImageView image;

    /**
     * Construct and set the root of this custom control.
     *
     * @param sys The solar system this icon represents.
     */
    public SolarIcon(SolarSystem sys) {
        image = new ImageView(sys.getType().getSystemImage());
        image.fitWidthProperty().bind(this.widthProperty());
        image.fitHeightProperty().bind(this.heightProperty());
        setPrefWidth(2 * sys.getPlanets().size());
        setPrefHeight(2 * sys.getPlanets().size());
        getChildren().add(image);
    }

}
