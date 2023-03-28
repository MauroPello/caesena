package it.unibo.caesena.model.tile;

import java.util.Optional;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;

public interface Tile {

    void rotateClockwise();

    Optional<Pair<Integer, Integer>> getPosition();

    void setPosition(Pair<Integer, Integer> pos);

    boolean isPlaced();

    boolean isSectionNearToGameset(final TileSection section, final GameSet gameSet);

    void putSection(TileSection section, GameSet gameSet);

    GameSet getGameSet(TileSection section);

    void closeSection(TileSection section);

    boolean isSectionClosed(TileSection section);

    int getRotationCount();

    TileType getTileType();
}
