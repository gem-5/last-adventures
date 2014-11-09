package edu.gatech.gem5.game.ui;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * ExplorableDisplay abstract custom FXML element controller class.
 *
 * Allows custom elements to pan and zoom.
 *
 * @author Creston Bunch
 */
public abstract class AbstractExplorableDisplay extends Pane {

    /**
     * The width of the grid being shown.
     */
    protected int gridWidth;
    /**
     * The height of thi grid being shown.
     */
    protected int gridHeight;
    /**
     * The camera for panning/zooming the display.
     */
    protected Camera camera;
    /**
     * A handler for zooming on scroll.
     */
    protected ScrollHandler scrollHandler;
    /**
     * A handler for panning on drag.
     */
    protected DragHandler dragHandler;
    /**
     * A handler for moving while dragging.
     */
    protected MoveHandler moveHandler;

    /**
     * Construct and set the root of this custom control.
     */
    public AbstractExplorableDisplay() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/explorable.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.camera = new Camera();

        this.scrollHandler = new ScrollHandler();
        this.dragHandler = new DragHandler();
        this.moveHandler = new MoveHandler();
        // attach event handlers
        setOnScroll(this.scrollHandler);
        setOnMousePressed(this.dragHandler);
        setOnMouseDragged(this.dragHandler);
        setOnMouseReleased(this.dragHandler);
        setOnMouseMoved(this.moveHandler);
        // clip things outside the bounds
        Rectangle clipper = new Rectangle();
        clipper.setX(0);
        clipper.setY(0);
        clipper.widthProperty().bind(this.widthProperty());
        clipper.heightProperty().bind(this.heightProperty());
        this.setClip(clipper);
    }

    /**
     * Add a node to the explorable display.
     *
     * @param nx The x coordinate of this node.
     * @param ny The y coordinate of this node.
     * @param node The node to add.
     */
    public void addNode(int nx, int ny, Node node) {
        this.getChildren().add(new Point(nx, ny, node));
    }

    /**
     * Updates the view of the explorable display.
     */
    public void update() {
        updatePositions();
        updateSizes();
    }

    /** Update node positions only. **/
    protected void updatePositions() {
        for (Node child : this.getChildren()) {
            Point p = (Point) child;
            Node n = p.getNode();
            int x = this.camera.translateX(p.x);
            int y = this.camera.translateY(p.y);
            n.setLayoutX(x);
            n.setLayoutY(y);
        }
    }

    /** Update node sizes only. **/
    protected void updateSizes() {
        for (Node child : this.getChildren()) {
            Point p = (Point) child;
            Node n = p.getNode();
            double zoom = this.camera.getZoom();
            n.setScaleX(zoom);
            n.setScaleY(zoom);
        }
    }

    /**
     * A virtual camera looking at a small portion of the explorable area.
     *
     * @author Creston Bunch
     */
    public class Camera {

        /**
         * x position of the camera.
         */
        private double x;
        /**
         * y position of the camera.
         */
        private double y;
        /**
         * zoom factor of the camera.
         */
        private double zoom;

        /**
         * Maximum zoom a camera can achieve.
         */
        private static final double MAX_ZOOM = 20.0;
        /**
         * Minimum zoom a camera can achieve.
         */
        private static final double MIN_ZOOM = 1.0;

        /**
         * Construct the camera with initial x, y coordinates and zoom factor.
         *
         * @param startX The x coordinate
         * @param startY The y coordinate
         * @param startZ The amount to scale nodes.
         */
        public Camera(double startX, double startY, double startZ) {
            this.x = startX;
            this.y = startY;
            this.zoom = startZ;
        }

        /**
         * Default constructor.
         */
        public Camera() {
            this(0.0, 0.0, 1.0);
        }

        /**
         * Move the camera to an absolute position.
         *
         * @param newX The new x coordinate.
         * @param newY The new y coordinate.
         */
        public void move(double newX, double newY) {
            this.x = Math.min(Math.max(newX, 0), gridWidth);
            this.y = Math.min(Math.max(newY, 0), gridHeight);
        }

        /**
         * Move the camera to a relative position.
         *
         * @param dx The relative x distance.
         * @param dy The relative y distance.
         */
        public void pan(double dx, double dy) {
            move(this.x + dx, this.y + dy);
        }

        /**
         * Zoom the camera to an absolute number.
         *
         * @param newZ The zoom factor to set.
         */
        public void setZoom(double newZ) {
            this.zoom = Math.max(Math.min(newZ, MAX_ZOOM), MIN_ZOOM);
        }

        /**
         * Get the zoom value.
         *
         * @return the zoom value.
         */
        public double getZoom() {
            return this.zoom * pixelsPerUnit();
        }

        /**
         * Zoom the camera by a relative amount.
         *
         * @param dzoom The amount to increase.
         */
        public void zoom(double dzoom) {
            setZoom(this.zoom + dzoom);
        }

        /**
         * Calculate the current width of the camera based on its zoom factor.
         *
         * @return the width in arbitrary units.
         */
        public double getWidth() {
            return AbstractExplorableDisplay.this.getWidth() / pixelsPerUnit() / zoom;
        }

        /**
         * Calculate the current height of the camera based on its zoom factor.
         *
         * @return the height in arbitrary units.
         */
        public double getHeight() {
            return AbstractExplorableDisplay.this.getHeight() / pixelsPerUnit() / zoom;
        }

        /**
         * Check if a point is within the viewport of the camera.
         *
         * @param p The point to check.
         * @return true if it is, false otherwise.
         */
        public boolean inView(Point p) {
            Node n = p.getNode();
            double lX = p.x; // leftmost x point on the node
            double rX = p.x + n.getBoundsInLocal().getWidth(); // rightmost x point
            double tY = p.y; // topmost y point
            double bY = p.y + n.getBoundsInLocal().getHeight(); // bottommost y point
            return rX > this.x - getWidth() / 2
                    && bY > this.y - getHeight() / 2
                    && lX < this.x + getWidth() / 2
                    && tY < this.y + getHeight() / 2;
        }

        /**
         * Convert a grid x-coordinate to an x-coordinate in the camera.
         *
         * @param gridX the x coordinate to convert
         * @return The x coordinate in pixels relative to the camera origin
         */
        public int translateX(double gridX) {
            double centerX = getWidth() / 2;
            double newX = gridX - this.x + centerX;
            return (int) Math.round(newX * pixelsPerUnit() * zoom);
        }

        /**
         * Convert a grid y-coordinate to a y-coordinate in the camera.
         *
         * @param gridY the y coordinate to convert
         * @return The y coordinate in pixels relative to the camera origin
         */
        public int translateY(double gridY) {
            double centerY = getHeight() / 2;
            double newY = gridY - this.y + centerY;
            return (int) Math.round(newY * pixelsPerUnit() * zoom);
        }

        /**
         * Convert an x-coordinate in the camera to an x-coordinate in the grid.
         *
         * @param cameraX the x coordinate to convert
         * @return The x coordinate in arbitrary units
         */
        public double unTranslateX(double cameraX) {
            double centerX = getWidth() / 2;
            double newX = cameraX / pixelsPerUnit() / zoom;
            return newX + this.x - centerX;
        }

        /**
         * Convert a y-coordinate in the camera to a y-coordinate in the grid.
         *
         * @param cameraY the y coordinate to convert
         * @return The y coordinate in arbitrary units
         */
        public double unTranslateY(double cameraY) {
            double centerY = getHeight() / 2;
            double newY = cameraY / pixelsPerUnit() / zoom;
            return newY + this.y - centerY;
        }

        /**
         * The ratio of pixels per unit at zoom = 1.0.
         *
         * @return The ratio of pixels/units.
         */
        public double pixelsPerUnit() {
            double ratio = Math.min(
                AbstractExplorableDisplay.this.getWidth() / gridWidth,
                AbstractExplorableDisplay.this.getHeight() / gridHeight
            );
            return ratio;
        }
    }

    /**
     * Represents an x-y point in Cartesian coordinates.
     *
     * Used for assigning a location to nodes in an ExplorableDisplay.
     *
     * @author Creston Bunch
     */
    private static class Point extends Parent {

        /**
         * The x position of this point in the grid.
         */
        public final int x;
        /**
         * The y position of this point in the grid.
         */
        public final int y;

        /**
         * The node at this point.
         */
        private Node n;

        /**
         * Construct the point with a given x and y.
         *
         * @param nx The x coordinate
         * @param ny The y coordinate
         * @param node The node at this coordinate.
         */
        public Point(int nx, int ny, Node node) {
            this.x = nx;
            this.y = ny;
            this.n = node;
            this.getChildren().add(node);
        }

        /**
         * Get the node at this point.
         *
         * @return the node
         */
        public Node getNode() {
            return this.n;
        }

    }

    /**
     * An event handler for zooming in on this explorable display.
     *
     * @author Creston Bunch
     */
    private class ScrollHandler implements EventHandler<ScrollEvent> {

        /**
         * Amount to zoom each tick.
         */
        private static final double ZOOM_FACTOR = 0.5;

        /**
         * Handle the scroll event.
         *
         * @param e The scroll event.
         */
        @Override
        public void handle(ScrollEvent e) {
            // TODO: zoom on cursor?
            double sign = e.getDeltaY() / e.getMultiplierY();
            camera.zoom(sign * ZOOM_FACTOR);
            update();
        }
    }

    /**
     * Keep track of the mouse position relative to the display.
     *
     * @author Creston Bunch
     */
    private class MoveHandler implements EventHandler<MouseEvent> {

        /**
         * The x position of the mouse.
         */
        private double xPos;
        /**
         * The y position of the mouse.
         */
        private double yPos;

        /**
         * Handle the move event.
         *
         * @param e The mouse event.
         */
        @Override
        public void handle(MouseEvent e) {
            xPos = e.getSceneX() - getLayoutX();
            yPos = e.getSceneY() - getLayoutY();
        }

        /**
         * Get the x position of the mouse.
         *
         * @return the x position.
         */
        public double getX() {
            return xPos;
        }

        /**
         * Get the y position of the mouse.
         *
         * @return the y position.
         */
        public double getY() {
            return yPos;
        }
    }

    /**
     * An event handler for panning around on this explorable display.
     *
     * @author Creston Bunch
     */
    private class DragHandler implements EventHandler<MouseEvent> {

        /**
         * Toggle between dragging/not dragging.
         */
        private boolean dragging = false;
        /**
         * Last x location of the mouse cursor.
         */
        private double prevX = -1;
        /**
         * Last y location of the mouse cursor.
         */
        private double prevY = -1;
        /**
         * Timeline for updating less often.
         */
        private Timeline t;

        /**
         * How often to update the screen.
         */
        private static final int RATE = 10; //ms

        /**
         * Construct the drag handler.
         */
        public DragHandler() {
            // update the display on a timer to reduce lag
            t = new Timeline(new KeyFrame(new Duration(RATE),
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        updatePositions();
                    }
                }
            ));
            t.setCycleCount(Timeline.INDEFINITE);
            t.play();
        }

        /**
         * Handle the drag event.
         *
         * @param e The mouse event.
         */
        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                dragging = true;
                setCursor(Cursor.NONE);
            } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
                dragging = false;
                // reset previous values
                prevX = -1;
                prevY = -1;
                setCursor(Cursor.DEFAULT);
            } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED && dragging) {

                if (prevX >= 0 && prevY >= 0) {
                    double dx = (e.getX() - prevX) / camera.getZoom();
                    double dy = (e.getY() - prevY) / camera.getZoom();
                    camera.pan(-dx, -dy);
                }
                this.prevX = e.getX();
                this.prevY = e.getY();
            }

        }
    }
}
