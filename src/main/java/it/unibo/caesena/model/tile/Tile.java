package it.unibo.caesena.model.tile;

import java.util.Optional;

import it.unibo.caesena.utils.Pair;

public interface Tile {

    Optional<Pair<Integer, Integer>> getPosition();

    void setPosition(Pair<Integer, Integer> pos);

    boolean isPlaced();

    void closeSection(TileSection section);

    boolean isSectionClosed(TileSection section);

    TileType getTileType();
}
