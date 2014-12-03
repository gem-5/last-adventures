package edu.gatech.gem5.game.controllers;

import java.net.URL;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * Nyan Cat Easter Egg.
 *
 * @author James Jong Han Park
 */
public class EasterNyanController extends AbstractController {

    /**
     * FXML's root AnchorPane link.
     */
    @FXML
    AnchorPane root;
    
    /**
     *  Audio link for the screen associated with the controller.
     */
    private AudioClip audioClip;
    /**
     * Animation of Nyan cats associated with this controller.
     */
    private Timeline timeline;
    /**
     * FXML file that is directly associated with this class.
     */
    public static final String CREATE_VIEW_FILE = "/fxml/easterNyan.fxml";
    /**
     * Constructor for EasterNyanController.
     */
    public EasterNyanController() {
        super(CREATE_VIEW_FILE);
        this.initialize();
    }

    /**
     * Initializes Nyan easter egg..
     */
    public void initialize() {

        // Add Sound
        final URL soundURL = getClass().getResource("/sound/nyan.wav");
        audioClip = new AudioClip(soundURL.toString());
        audioClip.setCycleCount(500);
        audioClip.play(1.0);

        // Taken and modified from the animation tutorial from Oracle
        Group nyanCats = new Group();
        Image nyanCatImage = new Image("/img/nyanCat.png");
        for (int i = 0; i < 50; i++) {
            ImageView nyanCat = new ImageView(nyanCatImage);
            nyanCat.setScaleX(1.0 / i);
            nyanCat.setScaleY(1.0 / i);
            nyanCats.getChildren().add(nyanCat);
        }
        root.getChildren().add(nyanCats);
        timeline = new Timeline();
        Random r = new Random();
        for (Node nyans : nyanCats.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(1000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(2000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(3000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(4000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(5000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(6000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(7000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(8000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(9000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                ),
                    new KeyFrame(new Duration(10000),
                            new KeyValue(nyans.translateXProperty(), r.nextInt(640 + 640 + 1) - 640),
                            new KeyValue(nyans.translateYProperty(), r.nextInt(360 + 360 + 1) - 360)
                )
            );
        }
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Integer.MAX_VALUE);
        timeline.play();
    }
    
    /**
     * Move to the confirm screen.
     *
     * @param event The button press.
     */
    @FXML
    public void confirm(ActionEvent event) {
        audioClip.stop();
        timeline.stop();
        transitionTo(new CharacterStatusController());
    }
}
