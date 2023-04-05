package it.unibo.caesena.view.components.tile;

import java.util.Optional;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.view.BasicComponent;

/**
 * This class rappresents a grid of the board.
 * A TileButton object can contain a tile or be empty.
 * It contains information about the state of the tile (whether is present,
 * placed or
 * locked) and the possible precence of a meeple on it.
 * It uses a generic type to allow the interface to be used in any GUI framework
 * (such as Swing or JavaFX).
 *
 * @param <X> the type of the GUI component
 */
public interface TileButton<X> extends BasicComponent<X> {

    /**
     * Adds a tile.
     *
     * @param tile to be add
     */
    void addTile(Tile tile);

    /**
     * Sets the tile image.
     *
     * @param tileImage to be set
     */
    void setTileImage(TileImage tileImage);

    /**
     * Checks wheter or not a tile is lock or not.
     * A tile is considered lock if the tile is confirmed to be place, hence it
     * can't be removed.
     *
     * @return true if the tile is lock, false otherwise
     */
    boolean isLocked();

    /**
     * Removes a tile from the TileButton, making it empty.
     */
    void removeTile();

    /**
     * Gets the state of the tile, if is present or not.
     *
     * @return true if is present, false otherwise
     */
    boolean containsTile();

    /**
     * Gets the meeple, if present.
     *
     * @return a Optional containing the meeple
     */
    Optional<Meeple> getMeeple();

    /**
     * Sets the meeple.
     *
     * @param meeple to be set
     */
    void setMeeple(Meeple meeple);

    /**
     * Unsets the meeple.
     */
    void unsetMeeple();

    /**
     * Locks the TileButoon.
     * Namely makes so that the TileButton is confirmed to be place, hence it
     * can't be removed.
     */
    void lock();

}
