package edu.gatech.gem5.game.ui;


import javafx.scene.image.ImageView;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.SolarSystem;
import edu.gatech.gem5.game.Ship;

/**
 * UniverseDisplay custom FXML element controller class.
 *
 * @author Creston Bunch
 */
public class UniverseDisplay extends ExplorableDisplay {

    /**
     * The x location of the player.
     */
    private int locX;
    /**
     * The y location of the player.
     */
    private int locY;
    /**
     * The range of the player's ship.
     */
    private int range;
    /**
     * The marker marking the player's location and range.
     */
    private ImageView marker;

    /**
     * Path to the marker image file.
     */
    public static final String MARKER_PATH = "/img/marker.png";

    /**
     * Update the universe display.
     *
     * @param uni The universe to display.
     * @param sys The solar system the player is at.
     * @param ship The player's ship.
     */
    public UniverseDisplay(Universe uni, SolarSystem sys, Ship ship) {
        super();
        // the display grid is limited by the size of the universe
        this.gridWidth = uni.getWidth();
        this.gridHeight = uni.getHeight();
        // set the default x and y coordinates
        locX = sys.getXCoordinate();
        locY = sys.getYCoordinate();
        range = ship.getFuel();
        this.camera = new Camera(locX, locY, 10.0);
        this.marker = new ImageView(MARKER_PATH);
        this.marker.setFitWidth(range * 2);
        this.marker.setFitHeight(range * 2);
        this.marker.setMouseTransparent(true);
        this.addNode(locX, locY, marker);
    }
}
