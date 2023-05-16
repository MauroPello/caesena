package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.player.PlayerInGame;

/**
 * An interface defining a Meeple.
 */
public interface Meeple {

    /**
     * Gets the meeple strength.
     *
     * @return the meeple strength.
     */
    int getStrength();

    /**
     * Gets whether or not the meeple is currently placed.
     *
     * @return whether or not the meeple is currently placed.
     */
    boolean isPlaced();

    /**
     * Gets the owner's color.
     *
     * @return the owner's color
     */
    Color getColor();

    /**
     * Gets the player that owns the meeple.
     *
     * @return the player that owns the meeple.
     */
    PlayerInGame getOwner();

    /**
     * Gets the type of the meeple.
     *
     * @return the type of the meeple.
     */
    MeepleType getType();
}
