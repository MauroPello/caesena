package it.unibo.caesena.model.tile;

import java.util.Optional;

import it.unibo.caesena.utils.Pair;

/**
 * An interface defining a Tile.
 */
public interface Tile {

    /**
     * Gets the position in which the tile is placed.
     *
     * @return the position of the tile, Optional.empty() if the tile is not placed.
     */
    Optional<Pair<Integer, Integer>> getPosition();

    /**
     * Gets whether or not the tile is placed.
     *
     * @return whether or not the tile is placed.
     */
    boolean isPlaced();

    /**
     * Gets whether or not the provided section in the tile is closed.
     *
     * @param section the provided section
     * @return whether or not the provided section in the tile is closed.
     */
    boolean isSectionClosed(TileSectionType section);

    /**
     * Gets the rotation count.
     *
     * @return the rotation count.
     */
    int getRotationCount();

    /**
     * Gets the TileType of the tile.
     *
     * @return the TileType of the tile.
     */
    TileType getTileType();
}
