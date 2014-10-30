package edu.gatech.gem5.game.ui;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import javafx.scene.shape.Rectangle;

/**
 * ExplorableDisplay abstract custom FXML element controller class.
 *
 * Allows custom elements to pan and zoom.
 *
 * @author Creston Bunch
 */
public abstract class ExplorableDisplay extends Pane {

    protected int gridWidth;
    protected int gridHeight;
    protected Camera camera;
    protected List<Point> nodes;
    protected ScrollHandler scrollHandler;
    protected DragHandler dragHandler;
    protected MoveHandler moveHandler;

    /**
     * Construct and set the root of this custom control.
     */
    public ExplorableDisplay() {
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

        this.nodes = new ArrayList<>();
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
     * @param x The x coordinate of this node.
     * @param y The y coordinate of this node.
     * @param n The node to add.
     */
    public void addNode(int x, int y, Node n) {
        this.getChildren().add(new Point(x, y, n));
    }

    /**
     * Updates the view of the explorable display.
     */
    public void update() {
        updatePositions();
        updateSizes();
    }

    /** Update node positions only **/
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

    /** Update node sizes only **/
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
        private double x;
        private double y;
        private double zoom;


        private static final double MAX_ZOOM = 20.0;
        private static final double MIN_ZOOM = 1.0;

        /**
         * Construct the camera with initial x, y coordinates and zoom factor.
         *
         * @param x The x coordinate
         * @param y The y coordinate
         * @param zoom The amount to scale nodes.
         */
        public Camera(double x, double y, double zoom) {
            this.x = x;
            this.y = y;
            this.zoom = zoom;
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
         * @param x The new x coordinate.
         * @param y The new y coordinate.
         */
        public void move(double x, double y) {
            this.x = Math.min(Math.max(x, 0), gridWidth);
            this.y = Math.min(Math.max(y, 0), gridHeight);
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
         * @param zoom The zoom factor to set.
         */
        public void setZoom(double zoom) {
            this.zoom = Math.max(Math.min(zoom, MAX_ZOOM), MIN_ZOOM);
        }

        /**
         * Get the zoom value.
         *
         * @param zoom the zoom value
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
         * @return the width in arbitrary units
         */
        public double getWidth() {
            return ExplorableDisplay.this.getWidth() / pixelsPerUnit() / zoom;
        }

        /**
         * Calculate the current height of the camera based on its zoom factor.
         *
         * @return the height in arbitrary units
         */
        public double getHeight() {
            return ExplorableDisplay.this.getHeight() / pixelsPerUnit() / zoom;
        }

        /**
         * Check if a point is within the viewport of the camera.
         *
         * @return true if it is, false otherwise
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
         * @param x the x coordinate to convert
         * @return The x coordinate in pixels relative to the camera origin
         */
        public int translateX(double x) {
            double centerX = getWidth() / 2;
            double newX = x - this.x + centerX;
            return (int) Math.round(newX * pixelsPerUnit() * zoom);
        }

        /**
         * Convert a grid y-coordinate to a y-coordinate in the camera.
         *
         * @param y the y coordinate to convert
         * @return The y coordinate in pixels relative to the camera origin
         */
        public int translateY(double y) {
            double centerY = getHeight() / 2;
            double newY = y - this.y + centerY;
            return (int) Math.round(newY * pixelsPerUnit() * zoom);
        }

        /**
         * Convert an x-coordinate in the camera to an x-coordinate in the grid.
         *
         * @param x the x coordinate to convert
         * @param The x coordinate in arbitrary units
         */
        public double unTranslateX(double x) {
            double centerX = getWidth() / 2;
            double newX = x / pixelsPerUnit() / zoom;
            return newX + this.x - centerX;
        }

        /**
         * Convert a y-coordinate in the camera to a y-coordinate in the grid.
         *
         * @param y the y coordinate to convert
         * @param The y coordinate in arbitrary units
         */
        public double unTranslateY(double y) {
            double centerY = getHeight() / 2;
            double newY = y / pixelsPerUnit() / zoom;
            return newY + this.y - centerY;
        }

        /**
         * The ratio of pixels per unit at zoom = 1.0
         *
         * @return The ratio of pixels/units.
         */
        public double pixelsPerUnit() {
            double ratio = Math.min(
                ExplorableDisplay.this.getWidth() / gridWidth,
                ExplorableDisplay.this.getHeight() / gridHeight
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
    public class Point extends Parent {
        public final int x;
        public final int y;
        private Node n;

        /**
         * Construct the point with a given x and y.
         *
         * @param x The x coordinate
         * @param y The y coordinate
         * @param n The node at this coordinate.
         */
        public Point(int x, int y, Node n) {
            this.x = x;
            this.y = y;
            this.n = n;
            this.getChildren().add(n);
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
    public class ScrollHandler implements EventHandler<ScrollEvent> {
        // amount to zoom each click
        private static final double ZOOM_FACTOR = 0.5;

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
    public class MoveHandler implements EventHandler<MouseEvent> {
        private double xPos;
        private double yPos;

        @Override
        public void handle(MouseEvent e) {
            xPos = e.getSceneX() - getLayoutX();
            yPos = e.getSceneY() - getLayoutY();
        }

        public double getX() {
            return xPos;
        }

        public double getY() {
            return yPos;
        }
    }

    /**
     * An event handler for panning around on this explorable display.
     *
     * @author Creston Bunch
     */
    public class DragHandler implements EventHandler<MouseEvent> {
        private boolean dragging = false;
        private double prevX = -1;
        private double prevY = -1;
        private Timeline t;
        // how often to update the screen
        private static final int RATE = 10; //ms

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
