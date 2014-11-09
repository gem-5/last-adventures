package edu.gatech.gem5.game.data;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import javafx.scene.image.Image;

/**
 * A container class for data of the various star types.
 *
 * @author Creston Bunch
 */
public class StarType extends DataType {


    /**
     * The Image name of the Star in Universe display.
     */
    @SerializedName("universe_image")
    private String universeImage;
    /**
     * The Image name of the Star in Solar System display.
     */
    @SerializedName("system_image")
    private String systemImage;
    /**
     * The array of planet probabilities.
     */
    @SerializedName("planets")
    private double[] planetProbabilities;
    /**
     * The occurence probability of this StarType.
     */
    @SerializedName("occurrence")
    private double occurrence;
    /**
     * The size of the StarType.
     */
    @SerializedName("size")
    private double size;

    /**
     * Image of Star in Universe Display.
     */
    private transient Image uniImgCache;
    /**
     * Image of Star in SolarSystem Display.
     */
    private transient Image sysImgCache;

    /**
     * The Key of StarType, used for accessing StarTypes.
     */
    public static final String KEY = "star";

    /**
     * Return the image for this star in a universe display.
     *
     * @return the image
     */
    public Image getUniverseImage() {
        if (uniImgCache == null) {
            uniImgCache = new Image(universeImage);
        }
        return uniImgCache;
    }

    /**
     * Return the image for this star in a solar system display.
     *
     * @return the image
     */
    public Image getSystemImage() {
        if (sysImgCache == null) {
            sysImgCache = new Image(systemImage);
        }
        return sysImgCache;
    }

    /**
     * Get the probability list of planets.
     *
     * @return the probability list
     */
    public double[] getPlanetProbabilities() {
        return Arrays.copyOf(planetProbabilities, planetProbabilities.length);
    }

    /**
     * Get the occurrence probability of this star type.
     *
     * @return the probability.
     */
    public double getOccurrence() {
        return occurrence;
    }

    /**
     * Get the size of this star in the universe display.
     *
     * @return the size
     */
    public double getSize() {
        return size;
    }

}
