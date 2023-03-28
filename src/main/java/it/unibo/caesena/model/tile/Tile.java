package it.unibo.caesena.model.tile;

import java.util.Optional;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;

public interface Tile {

    public void rotateClockwise();

    public Optional<Pair<Integer, Integer>> getPosition();

    public void setPosition(Pair<Integer, Integer> pos);

    public boolean isPlaced();

    public boolean isSectionNearToGameset(final TileSection section, final GameSet gameSet);

    public void putSection(TileSection section, GameSet gameSet);

    public GameSet getGameSet(TileSection section);

    public void closeSection(TileSection section);

    public boolean isSectionClosed(TileSection section);

    public int getRotationCount();

    public TileType getTileType();
}
