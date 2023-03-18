package it.unibo.caesena.view.components;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;

public interface TileButton {

    Pair<Integer, Integer> getPosition();

    void addTile(Tile tile);

    void lockTile();

    boolean isLocked();

    Tile getConteinedTile();

    void addMeeple(Meeple meeple);

    void removeTile();

    void removeMeeple();

    boolean containsTile();

    boolean containsMeeple();

}