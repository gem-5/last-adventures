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
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;

/**
 * A class for keeping track of persistent game data.
 *
 * @author Jack
 * @author Creston Bunch
 */
public class SaveFile {

    @SerializedName("player")
    private Character player;
    @SerializedName("universe")
    private Universe universe;
    @SerializedName("planet_index")
    private int currentPlanetIndex;
    @SerializedName("sys_x")
    private int currentSystemX;
    @SerializedName("sys_y")
    private int currentSystemY;

    public static final String SAVE_DIR = System.getProperty("user.dir") +
                                          "/saves";
    public static final String SAVE_EXT = ".save.json";
    public static final String SAVE_ENC = "UTF-8";

    /**
     * Construct the save file with nothing.
     */
    public SaveFile() {
        this.player = null;
        this.universe = null;
        this.currentPlanetIndex = 0;
        this.currentSystemX = 0;
        this.currentSystemY = 0;
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
     * @param player The player to save.
     */
    public void addCharacter(Character player) {
        this.player = player;
    }

    /**
     * Add a universe to the save file.
     *
     * @param universe The universe to save.
     */
    public void addUniverse(Universe universe) {
        this.universe = universe;
    }

    /**
     * Get the saved character.
     *
     * @return the character
     */
    public Character getCharacter() {
        return player;
    }

    /**
     * Get the saved universe
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
        return getSolarSystem().getPlanetAt(currentPlanetIndex);
    }

    /**
     * Return the currently visited solar system.
     *
     * @return the solar system currently being visited.
     */
    public SolarSystem getSolarSystem() {
        return universe.getSolarSystemAt(currentSystemX, currentSystemY);
    }

    /**
     * Sets the currently visited planet in the solar system.
     *
     * @param index The planet's index in the solar system.
     */
    public void setCurrentPlanet(int index) {
        currentPlanetIndex = index;
    }

    /**
     * Sets the currently visited solar system to the new solar system,
     * and makes the currently visited planet the first one in the
     * solar system.
     *
     * @param sys The solar system
     */
    public void setSolarSystem(SolarSystem sys) {
        this.currentSystemX = sys.getXCoordinate();
        this.currentSystemY = sys.getYCoordinate();
        setCurrentPlanet(0);
    }

    /**
     * Writes the save game to a file.
     */
    public void save() {
        Map<String, Object> dict = new HashMap<>();
        dict.put("player", this.player);
        dict.put("universe", this.universe);
        dict.put("planet_index", currentPlanetIndex);
        dict.put("sys_x", currentSystemX);
        dict.put("sys_y", currentSystemY);
        Type collectionType = new TypeToken<
            Map<String, Object>
        >(){}.getType();
        Gson gson = new Gson();
        String json = gson.toJson(dict, collectionType);	
        // The name of the file is the character name.
        // This will overwrite files with the same name.
        String name = getCharacter().getName() + SAVE_EXT;
        String path = SAVE_DIR + "/" + name;
        try {
            File saveDirectory = new File(SAVE_DIR);
            if (!saveDirectory.exists()) {
                if (!saveDirectory.mkdir()) {
                    throw new RuntimeException("Unable to create save directory.");
                }
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
        return String.format("Player:%n%1s\nPilot: %d%nFighter: %d%nEngineer: "+
                "%d%nTrader: %d%nInvestor: %d", player.getName(),
                player.getPilot(), player.getFighter(), player.getEngineer(),
                player.getTrader(), player.getInvestor()) + "\n" +  universe.toString();
    }
}
