package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;

/**
 * Extension of the {@link it.unibo.caesena.model.meeple.Meeple} interface.
 * It rappresents a Meeple as a mutable object.
 */
public interface MutableMeeple extends Meeple {
    /**
     * Places the meeple in a specific section inside a tile.
     *
     * @param tile        the tile that contains the specific section.
     * @param tileSection the section in which the meeple should be placed.
     */
    void place(Tile tile, TileSection tileSection);

    /**
     * Removes the meeple both from the section and tile it's placed on.
     */
    void remove();
}
