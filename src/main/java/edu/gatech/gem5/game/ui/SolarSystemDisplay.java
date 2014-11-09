package edu.gatech.gem5.game.ui;

import javafx.scene.Node;

/**
 * SolarSystemDisplay custom FXML element controller class.
 *
 * @author Creston Bunch
 */
public class SolarSystemDisplay extends AbstractExplorableDisplay {

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
        this.camera = new Camera(gridWidth / 2.0, gridHeight / 2.0, 1.0);
    }

    @Override
    public void addNode(int xCoord, int yCoord, Node n) {
        int x = xCoord + gridWidth / 2;
        int y = yCoord + gridHeight / 2;
        super.addNode(x, y, n);
    }
}
