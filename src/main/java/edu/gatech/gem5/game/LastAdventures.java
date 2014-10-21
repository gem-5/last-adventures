package edu.gatech.gem5.game;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import edu.gatech.gem5.game.readers.*;
import edu.gatech.gem5.game.data.*;

import edu.gatech.gem5.game.controllers.Controller;
import edu.gatech.gem5.game.controllers.TitleController;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
/**
 *
 * @author Jack
 * @author Creston
 * @author James Jong Han Park
 */
public class LastAdventures extends Application {


    private static SaveFile saveFile;

    public static Manager data;

    private static Parent root;
    private static Stage stage;

    /**
     * Start the game.
     *
     * @param stage The stage to start with
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.show();
        swap(new TitleController());
        // stage.setFullScreen(true);
    }

    /**
     * Changes the controller to something fresh.
     *
     * @param c The new controller to load.
     */
    public static void swap(Controller c) {
        Pane oldRoot = (Pane) getRoot();
        Scene scene = stage.getScene();
        root = c.getRoot();

        // make the new scene the same size
        if (oldRoot != null) {
            ((Pane) root).setPrefWidth(oldRoot.getWidth());
            ((Pane) root).setPrefHeight(oldRoot.getHeight());
        }

        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
        scene.getWindow().sizeToScene();

        c.finish(); // do something after the scene change
    }

    /**
     * Return the game root element.
     *
     * @return the root
     */
    public static Parent getRoot() {
        return root;
    }

    /**
     * Return the game stage.
     *
     * @return the stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Return the game scene.
     *
     * @return the scene
     */
    public static Scene getScene() {
        return stage.getScene();
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
        data.add(StoryText.KEY, new StoryReader().load("/data/Story.json"));
        //data.add(EventType.KEY, new EventReader().load("/data/Events.json"));
        data.add(StarType.KEY, new StarReader().load("/data/Stars.json"));


        launch(args);
   }

    public static void initializeGame(Character player, Universe uni) {
        final SaveFile currentSaveFile = LastAdventures.getCurrentSaveFile();
        currentSaveFile.addCharacter(player);
        currentSaveFile.addUniverse(uni);

        Random random = new Random();
        int randomX = random.nextInt(16) - 8;
        int randomY = random.nextInt(16) - 8;
        //for now, easiest to start near middle of the universe
        SolarSystem start = Universe.getSolarSystemNear(uni,
                randomX + uni.getWidth()/2, randomY + uni.getHeight() /2);
        currentSaveFile.setSolarSystem(start);
    }

    /**
     * Creates a new save file.
     */
    public static void createNewSaveFile() {
        saveFile = new SaveFile();
    }

    /**
     * Set the current save file.
     *
     * @param save The save file.
     */
    public static void setSaveFile(SaveFile save) {
        saveFile = save;
    }

    /**
     * Deletes a specified save file.
     *
     * @param file the save file to be deleted
     */
    public static void deleteSaveFile(SaveFile file) {
        try {
            Files.delete(Paths.get(SaveFile.SAVE_DIR + "/" +file.getCharacter() +".save.json"));
        } catch (IOException e) {
            System.err.println("Could not delete save file.");
        }
    }

    /**
     * Getter method for current save file.
     *
     * @return the currentFile
     */
    public static SaveFile getCurrentSaveFile() {
        return saveFile;
    }
}
