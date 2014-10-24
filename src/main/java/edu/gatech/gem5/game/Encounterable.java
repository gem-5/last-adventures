/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.Controller;
/**
 *
 * @author Jack Mueller
 */
public interface Encounterable {
    public EncounterManager getManager();
    public void setManager(EncounterManager manager);
    public String getEncounterMessage();
    public Controller getEncounterController();
    public String getViewFile();


}
