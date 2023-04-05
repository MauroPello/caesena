package it.unibo.caesena.model;

import java.util.Map;
import java.util.Set;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;

/**
 * An interface defining a mediator for GameSet and MutableTile. It keeps track of
 * GameSets contained in Tiles and vice versa.
 */
public interface GameSetTileMediator {

    /**
     * Adds to a GameSet many sections in a MutableTile.
     *
     * @param gameSet     in which to add the specific section
     * @param tile        the tile that contains the specific section
     * @param tileSections the sections to add to the gameSet
     */
    void addSections(GameSet gameSet, MutableTile tile, Set<TileSection> tileSections);

    /**
     * Checks whether or not a certain tile can be placed in a certain position.
     *
     * @param position the position in which to check if the placement is valid
     * @param tile     the tile that is being checked
     * @return whether or not a certain tile can be placed in a certain position
     */
    boolean isPositionValid(Pair<Integer, Integer> position, MutableTile tile);

    /**
     * Joins two adiacent tiles and their respective gameSets together.
     *
     * @param t1 one of two tiles to join
     * @param t2 one of two tiles to join
     */
    void joinTiles(MutableTile t1, MutableTile t2);

    /**
     * Gets all the tiles that are near a certain position.
     *
     * @param position at which to check for neighbours
     * @return all the tiles that are near a certain position
     */
    Set<MutableTile> getTileNeighbours(Pair<Integer, Integer> position);

    /**
     * Gets all GameSets of type field near a certain GameSet.
     *
     * @param gameSet of which to check neighbouring field GameSets
     * @return all GameSets of type field near a certain GameSet
     */
    Set<GameSet> getFieldGameSetsNearGameSet(GameSet gameSet);

    /**
     * Rotates the tile and its respective GameSets.
     *
     * @param tile the tile to be rotated
     */
    void rotateTileClockwise(MutableTile tile);

    /**
     * Gets the GameSet contained in a specific section in a tile.
     *
     * @param tile        the tile that contains the specific section
     * @param tileSection the specific section that contains the desired GameSet
     * @return the gameSet contained in the specific section in a tile
     */
    GameSet getGameSetInSection(MutableTile tile, TileSection tileSection);

    /**
     * Gets all the GameSets contained in a specific tile.
     *
     * @param tile the specific tile
     * @return all the GameSets contained in the specific tile
     */
    Set<GameSet> getGameSetsInTile(MutableTile tile);

    /**
     * Gets all the GameSets ever created.
     *
     * @return all the GameSets ever created
     */
    Set<GameSet> getAllGameSets();

    /**
     * Gets all the tiles that contain a specific GameSet.
     *
     * @param gameSet the specific GameSet
     * @return all the tiles that contain the specific GameSet
     */
    Map<MutableTile, Set<TileSection>> getTilesFromGameSet(GameSet gameSet);

    /**
     * Places a meeple in a specific section in a tile.
     *
     * @param meeple      meeple to be placed
     * @param tile        the tile that contains the specific section
     * @param tileSection the specific section where the meeple should be placed
     * @return whether or not the operation was successfull
     */
    boolean placeMeeple(MutableMeeple meeple, MutableTile tile, TileSection tileSection);

}
