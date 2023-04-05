package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;

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
     * Gets the current position for the meeple, namely the tile and section it's
     * placed on.
     *
     * @return the current position for the meeple.
     */
    Pair<Tile, TileSection> getPosition();

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
    Player getOwner();
}
