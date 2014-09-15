package edu.gatech.gem5.game;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import java.net.URI;

/**
 *
 * @author Jack
 * @author Creston
 * @author James Park
 */
public class LastAdventures extends Application {

    @Override
    public void start(Stage stage) throws Exception {       

		Parent root = FXMLLoader.load(getClass().getResource("/title.fxml"));
		Scene scene = new Scene(root);


		// Listen for moments when the scene changes on the stage, then
		// re-attach the size change listeners new the new scene
		stage.sceneProperty().addListener(new ChangeListener<Scene>() {
			public void changed(
				ObservableValue<? extends Scene> observable,
				Scene oldValue,
				Scene newValue
			) {
				Pane root = (Pane) newValue.getRoot();
				letterbox(newValue, root);
			}
		});

		// For some reason you have to show the stage before letterboxing it
		// for the scene change listener to work.
        stage.show();
        stage.setScene(scene);

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
