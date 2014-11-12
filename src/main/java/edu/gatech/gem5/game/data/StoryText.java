package edu.gatech.gem5.game.data;

/**
 * A container class for a story text.
 *
 * @author Creston Bunch
 */

public class StoryText extends AbstractDataType {

    /**
     * The text of the Story.
     */
    private String text;

    /**
     * They key used for accessing the StoryText class.
     */
    public static final String KEY = "story";

    /**
     * Get the text.
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Get the text.
     *
     * @return the text
     */
    @Override
    public String toString() {
        return getText();
    }
}
