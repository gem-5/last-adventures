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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.ui.BuyBar;
import edu.gatech.gem5.game.data.DataType;
import edu.gatech.gem5.game.data.GoodType;

/**
 * FXML Controller class
 *
 * @author James
 */
public class MarketController implements Initializable {

    @FXML
    private ListView<BuyBar> buyGoods;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SaveFile save = LastAdventures.getCurrentSaveFile();
        Planet planet = save.getPlanet();
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
    }    
    
}
