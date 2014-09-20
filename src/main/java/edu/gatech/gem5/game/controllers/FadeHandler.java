package edu.gatech.gem5.game.controllers;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Let's make a pretty fade animation!
 *
 * @author James Jong Han Park
 */
public class FadeHandler {

    private FadeTransition fadeTransition;

    /**
     * Default constructor for FadeHandler. Sets default fade duration to 0.5
     * seconds. Applies a fade in effect (Can be fade out if a different
     * constructor is used).
     *
     * @param object Object to apply fade animation
     */
    public FadeHandler(Node object) {
        this(object, 0);
    }

    /**
     * Default constructor for FadeHandler. Sets default fade duration to 0.5
     * seconds. Applies a fade in effect (Can be fade out if a different
     * constructor is used). Delay parameter is added to this constructor.
     *
     * @param object Object to apply fade animation
     * @param delay The delay before playing the animation in seconds
     */
    public FadeHandler(Node object, double delay) {
        this(object, 0.5, delay, 0, 1);
    }

    /**
     * Customizable constructor for fade animation.
     *
     * @param object Object to apply fade animation
     * @param duration The duration of the fade animation in seconds
     * @param delay The delay before playing the animation in seconds
     * @param fromValue 0.0 means transparent & 1.0 means opaque
     * @param toValue 0.0 means transparent & 1.0 means opaque
     */
    public FadeHandler(Node object, double duration, double delay, double fromValue, double toValue) {

        fadeTransition = new FadeTransition(Duration.seconds(duration), object);
        fadeTransition.setDelay(Duration.seconds(delay));
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.play();
    }

}
