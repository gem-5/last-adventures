package edu.gatech.gem5.game.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Map;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Good;
import edu.gatech.gem5.game.ui.BuyBar;
import edu.gatech.gem5.game.ui.UpgradeBar;
import edu.gatech.gem5.game.data.DataType;
import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;

/**
 * FXML Controller class
 *
 * @author James
 */
public class MarketController implements Initializable {

    @FXML
    private Label lblCash;

    @FXML
    private ListView<BuyBar> buyGoods;

    @FXML
    private ListView<UpgradeBar> upShips;

    @FXML
    private ListView<UpgradeBar> upWeapons;

    @FXML
    private ListView<UpgradeBar> upShields;

    private ListView<BuyBar> sellGoods;

    @FXML
    public void buyGoods() {
        // not implemented
    }
    @FXML
    public void upShip() {
        // not implemented
    }
    @FXML
    public void upWeapon() {
        // not implemented
    }
    @FXML
    public void upShield() {
        // not implemented
    }

    public void sellGoods() {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SaveFile save = LastAdventures.getCurrentSaveFile();
        Planet planet = save.getPlanet();

        this.lblCash.setText(
            ((Integer) save.getCharacter().getMoney()).toString()
        );

        // this is the tab for ships that the planet sells
        ObservableList<UpgradeBar> lstShips =
            FXCollections.observableArrayList();
        Map<String, ShipType> ships = LastAdventures.data.get(ShipType.KEY);
        for (String x : planet.getShips()) {
            UpgradeBar b = new UpgradeBar();
            ShipType ship = (ShipType) ships.get(x);
            b.setKey(x);
            b.setPrice(ship.getPrice());
            b.setText(ship.getName());
            lstShips.add(b);
        }
        upShips.setItems(lstShips);
        // this is the tab for weapons that the planet sells
        ObservableList<UpgradeBar> lstWeapons =
            FXCollections.observableArrayList();
        Map<String, WeaponType> weps = LastAdventures.data.get(WeaponType.KEY);
        for (String x : planet.getWeapons()) {
            UpgradeBar b = new UpgradeBar();
            WeaponType weapon = (WeaponType) weps.get(x);
            b.setKey(x);
            b.setPrice(weapon.getPrice());
            b.setText(weapon.getName());
            lstWeapons.add(b);
        }
        upWeapons.setItems(lstWeapons);
        // this is the tab for shields that the planet sells
        ObservableList<UpgradeBar> lstShields =
            FXCollections.observableArrayList();
        Map<String, ShieldType> shilds = LastAdventures.data.get(ShieldType.KEY);
        for (String x : planet.getShields()) {
            UpgradeBar b = new UpgradeBar();
            ShieldType shield = (ShieldType) shilds.get(x);
            b.setKey(x);
            b.setPrice(shield.getPrice());
            b.setText(shield.getName());
            lstShields.add(b);
        }
        upShields.setItems(lstShields);
        // this is the tab for goods that the planet sells
        ObservableList<BuyBar> lstGoods = FXCollections.observableArrayList();
        Map<String, GoodType> goods = LastAdventures.data.get(GoodType.KEY);
        for (Map.Entry<String, Integer> x : planet.getStock().entrySet()) {
            BuyBar b = new BuyBar();
            b.setKey(x.getKey());
            b.setQuantity(x.getValue());
            b.setPrice(planet.getSupply().get(x.getKey()));
            b.setText(
                ((GoodType) goods.get(x.getKey())).getName()
            );
            lstGoods.add(b);
        }
        buyGoods.setItems(lstGoods);

        // this is the tab for goods the player has
        ObservableList<BuyBar> listGoods = FXCollections.observableArrayList();
        Map<Good, Integer> playerGoods = LastAdventures.getCurrentSaveFile().getCharacter().getShip().getCargoCounts();
        for (Map.Entry<Good, Integer> g: playerGoods.entrySet()) {
            BuyBar b = new BuyBar();
            b.setKey(g.getKey().getType().getKey());
            b.setQuantity(g.getValue());
            b.setPrice(planet.getDemand().get(g.getKey().getType().getKey()));
            b.setText(g.getKey().getType().getName());
            listGoods.add(b);
        }
        sellGoods.setItems(listGoods);
    }


}
