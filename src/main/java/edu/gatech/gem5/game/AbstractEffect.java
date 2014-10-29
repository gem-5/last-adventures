package edu.gatech.gem5.game;

/**
 *
 * @author Jack Mueller
 */
abstract class AbstractEffect {
    
    /**
     * The property of the game that this object affects.
     */
    Object property;
    /**
     * Get an game variable, and modify it.
     */
    abstract void apply();

    
}
