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
     *
     * @return The meeple strength.
     */
    int getStrength();

    /**
     *
     * @return The player that owns the meeple.
     */
    Player getOwner();

    void place(Pair<Tile, TileSection> position);

    void remove();

    Pair<Tile, TileSection> getPosition();

    /**
     *
     * @return True if the meeple is placed, or else false.
     */
    boolean isPlaced();

}
