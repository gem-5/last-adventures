package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.animation.FadeHandler;
import edu.gatech.gem5.game.ui.SaveBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;

/**
 * A controller for the load game screen.
 *
 * @author Creston Bunch
 * @author James Jong Han Park
 */
public class LoadGameController extends Controller {

    @FXML
    private TilePane tileGames;

    public static final String LOAD_GAME_VIEW_FILE = "/fxml/load.fxml" ;

    /**
     * Construct the title controller.
     */
    public LoadGameController() {
        // load the view or throw an exception
        super(LOAD_GAME_VIEW_FILE);
        createList();
        
        // Create fade-in animation
        int x = 0;
        for (Node n: tileGames.getChildren()) {
            new FadeHandler(n, x / 8.0);
            x++;
        }
        
    }

    /**
     * Load the selected game.
     *
     * @param event a button press
     */
    @FXML
    private void loadGame(SaveBox box) {
        // TODO: move this logic to the SaveBox class.
        File sel = new File(box.getPath());
        if (sel != null) {
            SaveFile save = SaveFile.load(sel);
            LastAdventures.setSaveFile(save);
            LastAdventures.swap(new PlanetController());
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        LastAdventures.swap(new TitleController());
    }

    // create a list of saved games
    private void createList() {
        String dir = SaveFile.SAVE_DIR;
        Path path = Paths.get(dir);
        ObservableList<Node> obsList = tileGames.getChildren();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            for (Path child : ds) {
                final SaveBox box = new SaveBox();
                String name = child.getFileName().toString();
                String nameNoExt = name.substring(0, name.indexOf('.'));
                box.setLabel(nameNoExt);
                box.setPath(child.toString());
                // load the save game on click
                box.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        loadGame(box);
                    }
                });
                
                // Hide the boxe for animation
                box.setOpacity(0);
                
                obsList.add(box);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
