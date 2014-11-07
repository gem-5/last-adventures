package edu.gatech.gem5.game;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import edu.gatech.gem5.game.controllers.Controller;
import edu.gatech.gem5.game.controllers.TitleController;

/**
 *
 * @author Jack
 * @author Creston
 * @author James Jong Han Park
 */
public class LastAdventures extends Application {

    /**
     * The root Node for the applications current FXML file.
     */
    private static Parent root;
    /**
     * The FXML Stage that the application uses.
     */
    private static Stage stage;

    /**
     * Start the game.
     *
     * @param fxStage The stage to start with
     */
    @Override
    public void start(Stage fxStage) {
        stage = fxStage;
        stage.show();
        swap(new TitleController());
        // stage.setFullScreen(true);
    }

    /**
     * Changes the controller to something fresh.
     *
     * @param c The new controller to load.
     */
    public static void swap(Controller c) {

        Pane oldRoot = (Pane) getRoot();
        Scene scene = stage.getScene();
        root = c.getRoot();

        // make the new scene the same size
        if (oldRoot != null) {
            ((Pane) root).setPrefWidth(oldRoot.getWidth());
            ((Pane) root).setPrefHeight(oldRoot.getHeight());
        }

        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
        scene.getWindow().sizeToScene();

        c.finish(); // do something after the scene change
    }

    /**
     * Return the game root element.
     *
     * @return the root
     */
    public static Parent getRoot() {
        return root;
    }

    /**
     * Return the game stage.
     *
     * @return the stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Return the game scene.
     *
     * @return the scene
     */
    public static Scene getScene() {
        return stage.getScene();
    }

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Universe.main(new String[0]);
        //Encounter.main(new String[0]);
        launch(args);
    }

    /**
     * Sets a new view for the scene based on the contents of an FXML file.
     *
     * @param newRoot  the Parent FXML node to be shown on the stage
     */
    public static void setRoot(Parent newRoot) {
        LastAdventures.root = newRoot;
    }

    /**
     * Sets a new stage for the JavaFX application.
     *
     * @param newStage the new stage to be shown
     */
    public static void setStage(Stage newStage) {
        LastAdventures.stage = newStage;
    }
}
