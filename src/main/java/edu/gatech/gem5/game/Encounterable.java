/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.gem5.game;

/**
 *
 * @author Jack Mueller
 */
public interface Encounterable {
    public EncounterManager getManager();
    public void setManager(EncounterManager manager);
    public String getEncounterMessage();
    public void processEncounter();
    public String getViewFile();


}
