package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Player;
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
     * Gets the player that owns the meeple.
     * 
     * @return the player that owns the meeple.
     */
    Player getOwner();

    /**
     * Places the meeple in a specific section inside a tile.
     * 
     * @param tile the tile that contains the specific section.
     * @param tileSection the section in which the meeple should be placed.
     */
    void place(Tile tile, TileSection tileSection);

    /**
     * Removes the meeple both from the section and tile it's placed on.
     */
    void remove();

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

}
