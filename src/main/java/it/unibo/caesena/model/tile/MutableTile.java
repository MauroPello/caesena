package it.unibo.caesena.model.tile;

import it.unibo.caesena.utils.Pair;

/**
 * Extension of the {@link it.unibo.caesena.model.tile.Tile} interface.
 * It rappresents a Tile as a mutable object.
 */
public interface MutableTile extends Tile {

    /**
     * Sets the position in which the tile is placed.
     *
     * @param position of the tile.
     */
    void setPosition(Pair<Integer, Integer> position);

    /**
     * Rotates the tile.
     */
    void rotate();

    public void setCurrent(boolean current);

}
