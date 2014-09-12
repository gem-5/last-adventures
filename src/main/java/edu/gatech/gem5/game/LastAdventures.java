/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.gem5.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import java.net.URI;

/**
 *
 * @author Jack
 * @author Creston
 */
public class LastAdventures extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {       
        Parent root = FXMLLoader.load(
            getClass().getResource("/Title.fxml")
            // add resource bundle or something...
        );

        Scene scene = new Scene(root);
        scene.getStylesheets().add("title.css");
                

        stage.setScene(scene);
        stage.show();
        

        letterbox(scene, (Pane) root);
        //stage.setFullScreen(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio      = initWidth / initHeight;

        SizeChangeListener sizeListener =
            new SizeChangeListener(
                scene, ratio, initHeight, initWidth, contentPane
            );
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    /**
     * Allows all elements to scale equally to the screen size.
     *
     * @author jewelsea from StackOverflow and Creston Bunch
     */
    private static class SizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        /**
         * Initialize the listener.
         *
         * @param scene The scene being listened to.
         * @param ratio The ratio of width / height.
         * @param initHeight The initial height.
         * @param initWidth The initial width.
         * @param contentPane The content pane.
         */
        public SizeChangeListener(
            Scene scene,
            double ratio,
            double initHeight,
            double initWidth,
            Pane contentPane
        ) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        /**
         * Scale the scene to a new size.
         */
        @Override
        public void changed(
            ObservableValue<? extends Number> observableValue,
            Number oldValue,
            Number newValue
        ) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor =
                (newWidth / newHeight > ratio)
                ? newHeight / initHeight
                : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }

    }
    
}
