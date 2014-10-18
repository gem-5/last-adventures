package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.PopupWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * An icon representing a planet in the universe map.
 *
 * @author Creston Bunch
 */
public class HoverBox extends PopupWindow {

    private Pane root;

    /**
     * No arg constructor
     */
    public HoverBox() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/hoverbox.fxml")
            );

            loader.setController(this);
            root = loader.load();
            getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Add a node to the contents.
     *
     * @param node The node.
     */
    public void addNode(Node node) {
        root.getChildren().add(node);
    }

    /**
     * Bind this hover box to a node.
     *
     * @param node The node to bind to.
     */
    public void bind(Node node) {
        HoverHandler h = new HoverHandler(node);
        node.setOnMouseEntered(h);
        node.setOnMouseMoved(h);
        node.setOnMouseExited(h);
    }

     /**
     * An event handler for showing and hiding a hover box
     *
     * @author Creston Bunch
     */
    public class HoverHandler implements EventHandler<MouseEvent> {

        private Node node;
        private static final int OFFSET_X = 10;
        private static final int OFFSET_Y = 10;

        /**
         * Construct the handler with a box to show.
         *
         * @param box The box to show.
         */
        public HoverHandler(Node n) {
            this.node = n;
        }

        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                show(node, e.getSceneX() + OFFSET_X, e.getSceneY() + OFFSET_Y);
            } else if (e.getEventType() == MouseEvent.MOUSE_EXITED) {
                hide();
            } else if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
                setAnchorX(e.getSceneX() + OFFSET_X);
                setAnchorY(e.getSceneY() + OFFSET_Y);
            }
        }
    }

}
