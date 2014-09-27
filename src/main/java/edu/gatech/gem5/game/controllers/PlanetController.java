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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.data.CompanyType;

/**
 *
 * @author Creston Bunch
 */
public class PlanetController implements Initializable {

    @FXML
    private Parent root;

    @FXML
    private Label title;
    @FXML
    private Label lblCompanies;
    @FXML
    private Label lblEnvironment;
    @FXML
    private Label lblGovernment;
    @FXML
    private Label lblCondition;

    /**
     * Changes the scene based on the button pressed.
     *
     * @param event a button press
     * @throws Exception if the scene resource is not found
     */
    @FXML
    private void changeScenes(ActionEvent event) throws Exception {
        //gets this scene's stage

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String id = ((Button) (event.getSource())).idProperty().get();
        Node root = this.root;
        //loads the create FXML file into root
        if (id.equals("market")) {
            root = FXMLLoader.load(getClass().getResource("/market.fxml"));
        } else if (id.equals("travel")) {
            root = FXMLLoader.load(getClass().getResource("/displayUniverse.fxml"));
        }
        //sets the stage to root scene
        stage.setScene(new Scene((Pane) root));

    }

    /**
     * Initialize the controller.
     *
     * @param url The location to resolve all relative paths for the root
     * object.
     * @param rb The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SaveFile save = LastAdventures.getCurrentSaveFile();
        Planet planet = save.getPlanet();

        String companies = "";
        for (CompanyType c : planet.getCompanies()) {
            companies += c.getName() + "\n";
        }

        this.title.setText("Planets should get names.");
        this.lblCompanies.setText(companies);
        this.lblEnvironment.setText(planet.getEnvironment().getName());
        this.lblGovernment.setText(planet.getGovernment().getName());
        //this.txtCondition.setText(planet.getCondition().getName());
    }
}
