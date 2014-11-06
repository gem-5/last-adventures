package edu.gatech.gem5.game.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableStringValue;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Incrementor custom FXML element controller class
 *
 * @author Creston Bunch
 */
public class Incrementor extends HBox {

    @FXML
    private Label lblText;

    @FXML
    private Label lblValue;

    public WritableStringValue text;
    private IntegerProperty value;
    private IntegerProperty incrementAmount;
    private IntegerProperty max;
    private IntegerProperty min;
    private BooleanProperty allowIncrement;
    private BooleanProperty allowDecrement;

    /**
     * Construct and set the root of this custom control.
     */
    public Incrementor() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/incrementor.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // set the property defaults
        value = new SimpleIntegerProperty(0);
        incrementAmount = new SimpleIntegerProperty(1);
        max = new SimpleIntegerProperty(Integer.MAX_VALUE);
        min = new SimpleIntegerProperty(0);
        allowIncrement = new SimpleBooleanProperty(true);
        allowDecrement = new SimpleBooleanProperty(true);

        // bind the text property to the value property
        lblValue.textProperty().bind(value.asString());

        // force min and max values
        allowIncrement.bind(
            value.add(incrementAmount).lessThanOrEqualTo(max)
        );
        allowDecrement.bind(
            value.subtract(incrementAmount).greaterThanOrEqualTo(min)
        );

        text = new SimpleStringProperty();
    }

    /**
     * The value currently in this incrementor.
     *
     * @return the value.
     */
    public final IntegerProperty valueProperty() {
        return this.value;
    }

    /**
     * The max value in the incrementor.
     *
     * @return the max value property
     */
    public final IntegerProperty maxProperty() {
        return this.max;
    }

    /**
     * The min value in the incrementor.
     *
     * @return the min value property
     */
    public final IntegerProperty minProperty() {
        return this.min;
    }

    /**
     * Whether or not the incrementer can go up.
     *
     * @return true or false
     */
    public final BooleanProperty allowIncrementProperty() {
        return this.allowIncrement;
    }

    /**
     * Whether or not the incrementer can go down.
     *
     * @return true or false
     */
    public final BooleanProperty allowDecrementProperty() {
        return this.allowDecrement;
    }

    /**
     * The amount to increment by each click.
     *
     * @return the increment value
     */
    public final IntegerProperty incrementAmountProperty() {
        return this.incrementAmount;
    }

    /**
     * The text property.
     *
     * @return the text property.
     */
    public final StringProperty textProperty() {
        return this.lblText.textProperty();
    }

    /**
     * Get the integer value of the valueproperty.
     *
     * @return the value
     */
    public final int getValue() {
        return valueProperty().intValue();
    }

    /**
     * Get the maximum value of the incrementor.
     *
     * @return the maximum value
     */
    public int getMax() {
        return maxProperty().get();
    }

    /**
     * Get the minimum value of the incrementor.
     *
     * @return the minimum value
     */
    public int getMin() {
        return minProperty().get();
    }

    /**
     * Whether or not the incrementer can go up.
     *
     * @return true or false
     */
    public final boolean getAllowIncrement() {
        return allowIncrementProperty().get();
    }

    /**
     * Whether or not the incrementer can go down.
     *
     * @return true or false
     */
    public final boolean getAllowDecrement() {
        return allowDecrementProperty().get();
    }

    /**
     * Get the increment amount of this incrementor.
     *
     * @return the increment value.
     */
    public final int getIncrementAmount() {
        return incrementAmountProperty().intValue();
    }

    /**
     * Get the value of text property.
     *
     * @return The text.
     */
    public final String getText() {
        return textProperty().get();
    }

    /**
     * Set the value of this incrementor.
     *
     * @param value The value to set.
     */
    public final void setValue(int value) {
        valueProperty().setValue(value);
    }

    /**
     * Set the max value of this incrementor.
     *
     * @param max The max value to set.
     */
    public final void setMax(int max) {
        maxProperty().set(max);
        setValue(Math.min(max, getValue()));
    }

    /**
     * Set the min value of this incrementor.
     *
     * @param min The min value to set.
     */
    public final void setMin(int min) {
        minProperty().set(min);
        setValue(Math.max(min, getValue()));
    }

    /**
     * Set whether or not the incrementer can go up or down.
     *
     * @param b true or false
     */
    public final void setAllowIncrement(boolean b) {
        allowIncrementProperty().set(b);
    }

    /**
     * Set whether or not the decrement can go up or down.
     *
     * @param b true or false
     */
    public final void setAllowDecrement(boolean b) {
        allowDecrementProperty().set(b);
    }

    /**
     * Sets the amount the incrementor increases each tick.
     *
     * @param amt The value to set.
     */
    public final void setIncrementAmount(int amt) {
        incrementAmountProperty().setValue(amt);
    }

    /**
     * Set the text label of this incrementor.
     *
     * @param text The text to set.
     */
    public final void setText(String text) {
        this.lblText.setText(text);
    }

    /**
     * Increment this incrementor
     *
     * @param event a button press
     */
    @FXML
    public void increment(ActionEvent event) {
        if (getAllowIncrement()) {
            setValue(getValue() + getIncrementAmount());
        }
    }

    /**
     * Decrement the incrementor
     *
     * @param event a button press
     */
    @FXML
    public void decrement(ActionEvent event) {
        if (getAllowDecrement()) {
            setValue(getValue() - getIncrementAmount());
        }
    }

    /**
     * Listener for enforcing max values.
     *
     * @author Creston Bunch
     */
    private class MaxValueListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> obs,
                            Number oldValue,
                            Number newValue) {
            if (newValue.intValue() > getMax()) {
                setValue(oldValue.intValue());
            }
        }
    }

    /**
     * Listener for enforcing min values.
     *
     * @author Creston Bunch
     */
    private class MinValueListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> obs,
                            Number oldValue,
                            Number newValue) {
            if (newValue.intValue() < getMin()) {
                setValue(oldValue.intValue());
            }
        }
    }

}
