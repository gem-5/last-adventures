package edu.gatech.gem5.game.controllers;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Let's make a pretty translate animation! Creates animation for object
 * movements.
 *
 * @author James Jong Han Park
 */
public class TranslateHandler {

    private TranslateTransition translateTransition;

    /**
     * Default constructor for TranslateHandler. Sets default animation duration
     * to 0.5 seconds. Applies translate animation.
     *
     * @param object Object to apply animation
     */
    public TranslateHandler(Node object) {
        this(object, 0);
    }

    /**
     * Constructor for TranslateHandler with delay parameter. Sets default
     * animation duration to 0.5 seconds. Applies translate animation to right.
     *
     * @param object Object to apply animation
     * @param delay The delay before playing the animation in seconds
     */
    public TranslateHandler(Node object, double delay) {
        this(object, delay, 0, 10, 0);
    }

    /**
     * Customizable constructor for translate animation.
     *
     * @param object Object to apply animation
     * @param delay The delay before playing the animation in seconds
     * @param duration The duration of the animation in seconds
     * @param setToX (Transition amount) Sets the value of the property toX
     * @param setToY (Transition amount) Sets the value of the property toY
     */
    public TranslateHandler(Node object, double delay, double duration, double setToX, double setToY) {

        this(object, delay, duration, setToX, setToY, 0, 0);
    }

    /**
     * Customizable constructor for translate animation.
     *
     * @param object Object to apply animation
     * @param delay The delay before playing the animation in seconds
     * @param duration The duration of the animation in seconds
     * @param setToX (Transition amount) Sets the value of the property toX
     * @param setToY (Transition amount) Sets the value of the property toY
     * @param setFromX "start from" x-value
     * @param setFromY "start from" y-value
     */
    public TranslateHandler(Node object, double delay, double duration, double setToX, double setToY, double setFromX, double setFromY) {
        
        translateTransition = new TranslateTransition(Duration.seconds(duration), object);
        translateTransition.setDelay(Duration.seconds(delay));
        translateTransition.setFromX(setFromX);
        translateTransition.setFromY(setFromY);
        translateTransition.setToX(setToX);
        translateTransition.setToY(setToY);
        translateTransition.play();
    }

}
