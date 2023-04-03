package it.unibo.caesena.model.tile;

import java.util.Optional;

import it.unibo.caesena.utils.Pair;

public interface Tile {
    /**
     * 
     * @return the position of the tile.
     */
    Optional<Pair<Integer, Integer>> getPosition();
    /**
     * set the position of the tile.
     * @param pos
     */
    void setPosition(Pair<Integer, Integer> pos);
    /**
     * 
     * @return true if the tile is placed.
     */
    boolean isPlaced();
    /**
     * close the section of the tile.
     * @param section
     */
    void closeSection(TileSection section);
    /**
     * 
     * @param section
     * @return true if the section is closed.
     */
    boolean isSectionClosed(TileSection section);
    /**
     * rotate the tile.
     */
    void rotate();
    /**
     * 
     * @return the rotation count.
     */
    int getRotationCount();
    /**
     * 
     * @return the TileType of the tile.
     */
    TileType getTileType();
}
