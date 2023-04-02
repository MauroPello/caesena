package it.unibo.caesena.view.components;

import java.util.Optional;

import it.unibo.caesena.model.meeple.Meeple;

public interface TileButton<X> {

    void addTile(TileImage tileImage);

    boolean isLocked();

    void removeTile();

    boolean containsTile();

    Optional<Meeple> getMeeple();

    void setMeeple(Meeple meeple);

    void unsetMeeple();

    void lock();

    X getComponent();

}