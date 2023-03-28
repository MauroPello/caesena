package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Player;

/**
 * An interface defining a Meeple. 
 */
public interface Meeple {

    /**
     * 
     * @return The meeple strength.
     */
    public int getStrength();

    /**
     * 
     * @return The player that owns the meeple.
     */
    public Player getOwner();

    /**
     * Set the meeple as placed or not.
     * 
     * @param placed whether the meeple is placed or not.
     */
    public void setPlaced(final boolean placed);

    /**
     * 
     * @return True if the meeple is placed, or else false.
     */
    public boolean isPlaced();

}
