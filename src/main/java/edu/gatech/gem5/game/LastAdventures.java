package edu.gatech.gem5.game;

import java.util.LinkedList;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import edu.gatech.gem5.game.readers.*;
import edu.gatech.gem5.game.data.*;

/**
 *
 * @author Jack
 * @author Creston
 * @author James Jong Han Park
 */
public class LastAdventures extends Application {

    private static LinkedList<SaveFile> saveFiles;
    private static Integer currentFile;
    private final static Integer NONE = -1;

    public static Manager data;

    /**
     * Default constructor for LastAdventures. Initializes an empty holder for
     * save files.
     */
    public LastAdventures() {
        saveFiles = new LinkedList<>();
        currentFile = NONE;
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/title.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // stage.setFullScreen(true);
    }

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        data = new Manager();

        data.add(ShipType.KEY, new ShipReader().load("/data/Ships.json"));
        data.add(GadgetType.KEY, new GadgetReader().load("/data/Gadgets.json"));
        data.add(GoodType.KEY, new GoodReader().load("/data/Goods.json"));
        data.add(ShieldType.KEY, new ShieldReader().load("/data/Shields.json"));
        data.add(WeaponType.KEY, new WeaponReader().load("/data/Weapons.json"));
        data.add(CompanyType.KEY, new
        CompanyReader().load("/data/Companies.json"));
        data.add(GovernmentType.KEY, new
        GovernmentReader().load("/data/Governments.json"));
        data.add(ConditionType.KEY, new
        ConditionReader().load("/data/Conditions.json"));
        data.add(EnvironmentType.KEY, new
        EnvironmentReader().load("/data/Environments.json"));
        data.add(TechType.KEY, new TechReader().load("/data/TechLevels.json"));

        launch(args);
    }

    /**
     * Creates a new save file.
     */
    public static void createNewSaveFile() {
        // puts a new save file in the table at the next "index"
        saveFiles.add(new SaveFile());
        currentFile = saveFiles.size() - 1;
    }

    /**
     * Deletes a specified save file.
     *
     * @param file the save file to be deleted
     */
    public static void deleteSaveFile(SaveFile file) {
        int index = saveFiles.indexOf(file);
        if (index == currentFile) {
            //if we delete the current file, disable continue on title screen
            currentFile = NONE;
        } else if (index < currentFile) {
            //if we delete a file before the current file, update the index
            currentFile--;
        }
        saveFiles.remove(file);
    }

    /**
     * Getter method for current save file.
     *
     * @return the currentFile
     */
    public static SaveFile getCurrentSaveFile() {
        return saveFiles.get(currentFile);
    }
}
