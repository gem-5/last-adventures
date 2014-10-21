package edu.gatech.gem5.game.data;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import javafx.scene.image.Image;

/**
 * A container class for data of the various star types.
 *
 * @author Creston Bunch
 */
public class StarType extends DataType {


    @SerializedName("universe_image")
    private String universeImage;
    @SerializedName("system_image")
    private String systemImage;
    @SerializedName("planets")
    private double[] planetProbabilities;
    @SerializedName("occurrence")
    private double occurrence;
    @SerializedName("size")
    private double size;

    private transient Image uniImgCache;
    private transient Image sysImgCache;

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
     * Get the probability list of planets
     *
     * @return the probability list
     */
    public double[] getPlanetProbabilities() {
        return planetProbabilities;
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
