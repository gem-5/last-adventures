/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.gem5.game.controllers;

import com.sun.prism.paint.Color;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SaveFile;
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
 */
public class LoadGameController extends Controller {

    @FXML
    private TilePane tileGames;
    
    @FXML
    private Button delete, back;
    
    private boolean deleting;
    private List<SaveBox> deleteList;

    public static final String LOAD_GAME_VIEW_FILE = "/fxml/load.fxml" ;

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
    private void goBack() {
        LastAdventures.swap(new TitleController());
    }

    // create a list of saved games
    private void createList() {
        String dir = SaveFile.SAVE_DIR;
        Path path = Paths.get(dir);
        ObservableList<Node> obsList = tileGames.getChildren();
        obsList.clear();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            for (Path child : ds) {
                SaveBox box = new SaveBox();
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
                obsList.add(box);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Notifies the user that they are in deleting mode, not selecting mode, 
     * and changes the functions of the buttons to accomplish this.
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
     *  Returns back, delete, and saveBox buttons to there normal function
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
    
    private void queueForDelete(SaveBox box) {
        deleteList.add(box);
        box.setStyle("-fx-background-color: red;");
    }
    
    private void delete() {
        for(SaveBox box : deleteList) {
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
