package it.unibo.caesena.view.components;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;

public interface TileButton {

    Pair<Integer, Integer> getPosition();

    void addTile(Tile tile);

    void lockTile();

    boolean isLocked();

    Tile getContainedTile();

    TileSection getPlacedMeepleSection();

    void addMeeple(Meeple meeple, TileSection section);

    void removeTile();

    void removeMeeple();

    boolean containsTile();

    boolean containsMeeple();

}