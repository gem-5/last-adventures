/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Ship;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.data.CompanyType;

/**
 *
 * @author Creston Bunch
 */
public class PlanetController extends Controller {

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
    private Label lblTechnology;
    @FXML
    private Label lblCondition;
    @FXML
    private Button btnSave;
    @FXML
    private Button refuelButton;


    Planet planet;

    public static final String PLANET_VIEW_FILE = "/fxml/planet.fxml";

    /**
     * Construct the planet controller.
     */
    public PlanetController() {
        // load the view or throw an exception
        super(PLANET_VIEW_FILE);

        SaveFile save = LastAdventures.getCurrentSaveFile();
        planet = save.getPlanet();

        this.title.setText(planet.getName());
        this.lblCompanies.setText(buildCompanyString());
        this.lblEnvironment.setText(planet.getEnvironment().getName());
        this.lblGovernment.setText(planet.getGovernment().getName());
        this.lblCondition.setText(planet.getCondition().getName());
        this.lblTechnology.setText(planet.getTechLevel().getName());

        updateLabels();
    }

    private String buildCompanyString() {
        String companies = "";
        for (CompanyType c : planet.getCompanies()) {
            companies += c.getName() + "\n";
        }
        return companies;
    }

    /**
     * Changes the scene based on the button pressed.
     *
     * @param event a button press
     * @throws Exception propogates any JavaFX Exception
     */
    @FXML
    private void changeScenes(ActionEvent event) throws Exception {
        String id = ((Button) (event.getSource())).idProperty().get();
        //loads the create FXML file into root
        if (id.equals("market")) {
            LastAdventures.swap(new MarketController());
        } else if (id.equals("travel")) {
            LastAdventures.swap(new DisplayUniverseController());
        }
    }

    /**
     * Saves the game.
     *
     * @param event a button press
     */
    @FXML
    private void save(ActionEvent event) throws Exception {
        LastAdventures.getCurrentSaveFile().save();
        btnSave.setDisable(true); // don't save again...
    }

    @FXML
    /**
     * Max out the fuel in your ship.
     *
     * @param event A button press attempting to refuel
     * @throws Exception
     */
    public void refuel(ActionEvent event) throws Exception {
        // TODO: Don't let player money go negative
        Character player = LastAdventures.getCurrentSaveFile().getCharacter();
        Ship s = player.getShip();
        player.setMoney(player.getMoney() -  (s.getType().getRange() -
                s.getFuel()) * s.getType().getFuelCost() );
        s.setFuel(s.getType().getRange());
        updateLabels();
    }

    private void updateLabels() {
        Character player = LastAdventures.getCurrentSaveFile().getCharacter();
        Ship s = player.getShip();
        // update cost to refuel
        refuelButton.setText("Refuel " + (s.getType().getRange() - s.getFuel()) * s.getType().getFuelCost());
    }

}
