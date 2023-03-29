package it.unibo.caesena.view.components;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;

public interface TileButton<X> {

    void addTile(Tile tile);

    boolean isLocked();

    Tile getContainedTile();

    void removeTile();

    boolean containsTile();

    boolean containsMeeple();

    void addMeeple(Meeple meeple, TileSection section);

    X getComponent();

}