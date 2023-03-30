package it.unibo.caesena.model;

import java.util.Map;
import java.util.Set;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;

public interface GameSetTileMediator {

    void addSection(GameSet gameSet, Tile tile, TileSection tileSection);

    boolean isPositionValid(Pair<Integer, Integer> position, Tile tile);

    void joinTiles(Tile t1, Tile t2);

    Set<Tile> getTileNeighbours(Pair<Integer, Integer> position);

    Set<GameSet> getFieldGameSetsNearGameSet(GameSet gameSet);

    void rotateTileClockwise(Tile tile);

    GameSet getGameSetInSection(Tile tile, TileSection tileSection);

    Set<GameSet> getGameSetsInTile(Tile tile);

    Set<GameSet> getAllGameSets();

    Map<Tile, Set<TileSection>> getTilesFromGameSet(GameSet gameSet);

}
