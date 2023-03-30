package it.unibo.caesena.view.components;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.TileSection;

public interface TileButton<X> {

    void addTile();

    void addTile(TileImage tileImage);

    void previewMeeple(final Meeple meeple, final TileSection section);

    boolean isLocked();

    void removeTile();

    boolean containsTile();

    void setMeeple(Meeple meeple, TileSection section);

    void lock();

    X getComponent();

}