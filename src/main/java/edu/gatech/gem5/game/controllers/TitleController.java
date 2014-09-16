/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Creston Bunch
 */
public class TitleController implements Initializable {

    @FXML
    Parent root;
    
    @FXML
    private void changeScenes(ActionEvent event) throws Exception {
        //gets this scene's stage
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	String id = ((Button)(event.getSource())).idProperty().get();
        //loads the create FXML file into root
        if (id.equals("new")) {
            LastAdventures.createNewSaveFile();
            root = FXMLLoader.load(getClass().getResource("/create.fxml"));                     
        } else if (id.equals("continue")) {
            //TODO
            root = FXMLLoader.load(getClass().getResource("/title.fxml"));
        } else if (id.equals("load")) {
            //TODO
            root = FXMLLoader.load(getClass().getResource("/title.fxml"));
        }
        //sets the stage to root scene
        stage.setScene(new Scene((Pane) root));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // do nothing
    }    
    
}
