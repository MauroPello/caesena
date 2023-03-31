package it.unibo.caesena.view.components;

import java.util.Optional;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.TileSection;

public interface TileButton<X> {

    void addTile(TileImage tileImage);

    boolean isLocked();

    void removeTile();

    boolean containsTile();

    Optional<Meeple> getMeeple();

    void setMeeple(Meeple meeple, TileSection section);

    void unsetMeeple();

    void lock();

    X getComponent();

}