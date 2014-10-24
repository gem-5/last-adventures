package edu.gatech.gem5.game;

import edu.gatech.gem5.game.controllers.Controller;
/**
 * Defines methods that an Encounterable object should have so that 
 * EncounterManager can properly manage it and EncounterController can get
 * needed info.
 * 
 * @author Jack Mueller
 */
public interface Encounterable {

    /**
     *
     * @return the manager for this Encounterable
     */
    EncounterManager getManager();

    /**
     *
     * @param manager the manager for this Encounterable
     */
    void setManager(EncounterManager manager);

    /**
     *
     * @return The default message for this encounter
     */
    String getEncounterMessage();

    /**
     *
     * @return The FXML file needed for this Encounterable
     */
    String getViewFile();
    
    //@TODO Javadoc properly...? I don't know what this is for exactly
    /**
    * Dispatch method to move control to the correct FXController.
    * @return ???
    */
    public Controller getEncounterController();
}
