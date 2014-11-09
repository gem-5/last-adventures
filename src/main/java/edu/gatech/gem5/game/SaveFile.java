package edu.gatech.gem5.game;

import java.util.Map;
import java.util.HashMap;
import java.io.Reader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * A class for keeping track of persistent game data.
 *
 * @author Jack
 * @author Creston Bunch
 */
public class SaveFile {

    /**
     * The player that the user controls in a save file.
     */
    @SerializedName("player")
    private Character player;
    /**
     * The universe the player in which the player exists.
     */
    @SerializedName("universe")
    private Universe universe;
    /**
     * The last planet the player landed on.
     */
    @SerializedName("planet_index")
    private int currentPlanetIndex;
    /**
     * The x coordinate of the current Solar System in its universe.
     */
    @SerializedName("sys_x")
    private int currentSystemX;
    /**
     * The y coordinate of the current Solar System in its universe.
     */
    @SerializedName("sys_y")
    private int currentSystemY;
    /**
     * The name of the the last Solar System the player entered.
     */
    @SerializedName("sys_name")
    private String currentSystemName;

    /**
     * The directory to put save files in.
     */
    public static final String SAVE_DIR = System.getProperty("user.dir")
            + "/saves";
    /**
     * The extension that save files for Last Adventures have.
     */
    public static final String SAVE_EXT = ".save.json";
    /**
     * The encoding that save files for Last Adventures uses.
     */
    public static final String SAVE_ENC = Charset.defaultCharset().displayName();

    /**
     * Construct the save file.
     *
     * @param uni The universe
     * @param sys The solar system
     * @param pnt The planet
     * @param pyr The player
     */
    public SaveFile(Universe uni, SolarSystem sys, Planet pnt, Character pyr) {
        this.setUniverse(uni);
        this.setSolarSystem(sys);
        this.setPlayer(pyr);
    }

    /**
     * Factory method for constructing a save file from a saved file.
     *
     * @param file The file.
     * @return The save file.
     */
    public static SaveFile load(File file) {
        try {
            Reader r = new FileReader(file);
            Gson gson = new Gson();
            return gson.fromJson(r, SaveFile.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a character to the save file.
     *
     * @param p The player to save.
     */
    public void setPlayer(Character p) {
        this.player = p;
    }

    /**
     * Add a universe to the save file.
     *
     * @param u The universe to save.
     */
    public void setUniverse(Universe u) {
        this.universe = u;
    }

    /**
     * Sets the currently visited solar system to the new solar system.
     *
     * @param sys The solar system
     */
    public void setSolarSystem(SolarSystem sys) {
        this.currentSystemX = sys.getXCoordinate();
        this.currentSystemY = sys.getYCoordinate();
        this.currentSystemName = sys.getName();
    }

    /**
     * Sets the currently visited planet in the solar system.
     *
     * @param planet The planet the player is docked at.
     */
    public void setPlanet(Planet planet) {
        currentPlanetIndex = planet.getOrbit();
    }

    /**
     * Get the saved character.
     *
     * @return the character
     */
    public Character getPlayer() {
        return player;
    }

    /**
     * Get the saved universe.
     *
     * @return the universe
     */
    public Universe getUniverse() {
        return universe;
    }

    /**
     * Return the currently visited planet.
     *
     * @return the planet currently being visited.
     */
    public Planet getPlanet() {
        SolarSystem solarSystem = getSolarSystem();
        return solarSystem.getPlanetAt(currentPlanetIndex);
    }

    /**
     * Return the currently visited solar system.
     *
     * @return the solar system currently being visited.
     */
    public SolarSystem getSolarSystem() {
        return universe.getSolarSystemByName(currentSystemName);
    }

    /**
     * Writes the save game to a file.
     */
    public void write() {
        Map<String, Object> dict = new HashMap<>();
        dict.put("player", this.player);
        dict.put("universe", this.universe);
        dict.put("planet_index", currentPlanetIndex);
        dict.put("sys_x", currentSystemX);
        dict.put("sys_y", currentSystemY);
        dict.put("sys_name", currentSystemName);
        Type collectionType = new TypeToken<Map<String, Object>>() {
        } .getType();
        Gson gson = new Gson();
        String json = gson.toJson(dict, collectionType);
        // The name of the file is the character name.
        // This will overwrite files with the same name.
        String name = getPlayer().getName() + SAVE_EXT;
        String path = SAVE_DIR + "/" + name;
        try {
            File saveDirectory = new File(SAVE_DIR);
            if (!saveDirectory.exists() && !saveDirectory.mkdir()) {
                throw new RuntimeException("Unable to create save directory.");
            }
            PrintWriter writer = new PrintWriter(path, SAVE_ENC);
            writer.write(json);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("Player:%n%1s%nPilot: %d%nFighter: %d%nEngineer: "
                + "%d%nTrader: %d%nInvestor: %d", player.getName(),
                player.getPilot(), player.getFighter(), player.getEngineer(),
                player.getTrader(), player.getInvestor()) + "%n" + universe.toString();
    }
}
