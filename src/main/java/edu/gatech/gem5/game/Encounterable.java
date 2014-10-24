package edu.gatech.gem5.game;

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
     * Dispatch method to move control to the correct FXController.
     */
    void processEncounter();

    /**
     *
     * @return The FXML file needed for this Encounterable
     */
    String getViewFile();


}
