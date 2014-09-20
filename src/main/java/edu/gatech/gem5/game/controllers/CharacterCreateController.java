package edu.gatech.gem5.game.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Ship;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 * @author Jack
 */
public class CharacterCreateController implements Initializable {
    Parent root;
    @FXML
    Slider pilotSlider;
    @FXML
    Slider fighterSlider;
    @FXML
    Slider traderSlider;
    @FXML
    Slider engineerSlider;
    @FXML
    Slider investorSlider;
    @FXML
    Label pilotValue;
    @FXML
    Label fighterValue;
    @FXML
    Label traderValue;
    @FXML
    Label engineerValue;
    @FXML
    Label investorValue;
    @FXML
    Label remainingValue;
    @FXML 
    TextField name;
    
    /**
     *
     * @param event a button press
     * @throws Exception if something happens
     */
    @FXML
    public void changeScenes(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String id = ((Button)(event.getSource())).idProperty().get();
        if(id.equals("confirm")) {
            Character player = new Character( name.getText(),
                    (int)pilotSlider.getValue(),
                    (int)fighterSlider.getValue(),
                    (int)engineerSlider.getValue(),
                    (int)traderSlider.getValue(),
                    (int)investorSlider.getValue(), (Ship)null);
            LastAdventures.getCurrentSaveFile().addCharacter(player);
            //this is to print out the character once it is made
            System.out.println(LastAdventures.getCurrentSaveFile());
            root = FXMLLoader.load(getClass().getResource("/status.fxml"));
        } else if (id.equals("back")) {
            root = FXMLLoader.load(getClass().getResource("/title.fxml"));
        }
        
        stage.setScene(new Scene((Pane) root));
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO implement anonymous changelistener<Number> and 
        // EventHandler<MouseEvent> as concrete classes
        pilotSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (Integer.parseInt(remainingValue.getText()) != 0 || oldValue.intValue() > newValue.intValue()) {

                    pilotValue.setText("" + newValue.intValue());
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) +
                            (oldValue.intValue() - newValue.intValue())));
                }
            }
        });
        pilotSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int oldNumber = Integer.parseInt(pilotValue.getText());
                int badNumber = (int) pilotSlider.getValue();
                //the badNumber was too high
                if (badNumber != oldNumber) {
                    pilotSlider.setValue(oldNumber);
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) - (badNumber - oldNumber)));
                }
            }
        });
        fighterSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (Integer.parseInt(remainingValue.getText()) != 0 || oldValue.intValue() > newValue.intValue()) {

                    fighterValue.setText("" + newValue.intValue());
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) +
                            (oldValue.intValue() - newValue.intValue())));
                }
            }
        });
        fighterSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int oldNumber = Integer.parseInt(fighterValue.getText());
                int badNumber = (int) fighterSlider.getValue();
                //the badNumber was too high
                if (badNumber != oldNumber) {
                    fighterSlider.setValue(oldNumber);
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) - (badNumber - oldNumber)));
                }
            }
        });
        traderSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (Integer.parseInt(remainingValue.getText()) != 0 || oldValue.intValue() > newValue.intValue()) {

                    traderValue.setText("" + newValue.intValue());
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) +
                            (oldValue.intValue() - newValue.intValue())));
                }
            }
        });
        traderSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int oldNumber = Integer.parseInt(traderValue.getText());
                int badNumber = (int) traderSlider.getValue();
                //the badNumber was too high
                if (badNumber != oldNumber) {
                    traderSlider.setValue(oldNumber);
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) - (badNumber - oldNumber)));
                }
            }
        });
        engineerSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (Integer.parseInt(remainingValue.getText()) != 0 || oldValue.intValue() > newValue.intValue()) {

                    engineerValue.setText("" + newValue.intValue());
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) +
                            (oldValue.intValue() - newValue.intValue())));
                }
            }
        });
        engineerSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int oldNumber = Integer.parseInt(engineerValue.getText());
                int badNumber = (int) engineerSlider.getValue();
                //the badNumber was too high
                if (badNumber != oldNumber) {
                    engineerSlider.setValue(oldNumber);
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) - (badNumber - oldNumber)));
                }
            }
        });
        investorSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (Integer.parseInt(remainingValue.getText()) != 0 || oldValue.intValue() > newValue.intValue()) {

                    investorValue.setText("" + newValue.intValue());
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) +
                            (oldValue.intValue() - newValue.intValue())));
                }
            }
        });
        investorSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int oldNumber = Integer.parseInt(investorValue.getText());
                int badNumber = (int) investorSlider.getValue();
                //the badNumber was too high
                if (badNumber != oldNumber) {
                    investorSlider.setValue(oldNumber);
                    remainingValue.setText("" + (Integer.parseInt(
                            remainingValue.getText()) - (badNumber - oldNumber)));
                }
            }
        });
   }     
}
