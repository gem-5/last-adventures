package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.animation.FadeHandler;
import edu.gatech.gem5.game.ui.SaveBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Button;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A controller for the load game screen.
 *
 * @author Creston Bunch
 * @author James Jong Han Park
 */
public class LoadGameController extends Controller {

    /**
     * A main layout for the corresponding FXML.
     */
    @FXML
    private TilePane tileGames;

    /**
     * A button used to turn on deleting options for existing saves.
     */
    @FXML
    private Button delete;
    /**
     * A back button used to return to the main menu.
     */
    @FXML
    private Button back;

    /**
     * A flag that mimics a deleting existing saves mode.
     */
    private boolean deleting;

    /**
     * List of saves to delete.
     */
    private final List<SaveBox> deleteList;

    /**
     * An FXML file path that is directly associated with this class.
     */
    public static final String LOAD_GAME_VIEW_FILE = "/fxml/load.fxml";

    /**
     * Construct the title controller.
     */
    public LoadGameController() {
        // load the view or throw an exception
        super(LOAD_GAME_VIEW_FILE);
        createList();
        deleting = false;
        deleteList = new ArrayList<>();
    }

    /**
     * Handles SaveBox's press events.
     *
     * @param box SaveBox for either loading or queuing deletion.
     */
    @FXML
    private void select(SaveBox box) {
        if (!deleting) {
            loadGame(box);
        } else {
            queueForDelete(box);
        }
    }

    /**
     * Load the selected game.
     *
     * @param box a button press
     */
    @FXML
    private void loadGame(SaveBox box) {
        File sel = new File(box.getPath());
        if (sel != null) {
            SaveFile save = SaveFile.load(sel);
            universe = save.getUniverse();
            system = save.getSolarSystem();
            planet = save.getPlanet();
            player = save.getPlayer();

            transitionTo(new PlanetController());
        }
    }

    /**
     * Return to the title screen.
     */
    @FXML
    private void goBack() {
        transitionTo(new TitleController());
    }

    /**
     * Create a list of saved games.
     */
    private void createList() {
        String dir = SaveFile.SAVE_DIR;
        Path path = Paths.get(dir);
        ObservableList<Node> obsList = tileGames.getChildren();
        obsList.clear();
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
                        select(box);
                    }
                });

                // Hide the boxe for animation
                box.setOpacity(0);

                obsList.add(box);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create fade-in animation
        int x = 0;
        for (Node n : tileGames.getChildren()) {
            FadeHandler fadeHandler = new FadeHandler(n, x / 8.0);
            x++;
        }
    }

    /**
     * Notifies the user that they are in deleting mode, not selecting mode, and
     * changes the functions of the buttons to accomplish this.
     */
    public void prepareDelete() {
        deleting = true;
        delete.setStyle("-fx-text-fill: red;");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                delete();
            }
        });
        back.setText("Cancel");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cancelDelete();
            }
        });
    }

    /**
     * Returns back, delete, and saveBox buttons to there normal function.
     */
    private void cancelDelete() {
        deleting = false;
        deleteList.clear();
        delete.setText("Delete");
        delete.setStyle(null);
        back.setText("Back");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                prepareDelete();
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goBack();
            }
        });
        createList();
    }

    /**
     * Queue a save to remove.
     *
     * @param box An existing save to delete
     */
    private void queueForDelete(SaveBox box) {
        deleteList.add(box);
        box.setStyle("-fx-background-color: red;");
    }

    /**
     * Remove all queued saves for deletion.
     */
    private void delete() {
        for (SaveBox box : deleteList) {
            try {
                String path = box.getPath();
                Files.delete(Paths.get(path));
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        cancelDelete();
    }

}
