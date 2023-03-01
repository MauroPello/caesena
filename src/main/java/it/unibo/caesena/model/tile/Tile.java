package it.unibo.caesena.model.tile;

import java.util.Optional;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Position;

public interface Tile {

    public void rotateClockwise();

    public Optional<Position> getPosition();
    
    public void setPosition(Position pos);

    public boolean isPlaced();

    public String getImageResourcesPath();

    public void putSection(TileSection section, GameSet gameSet);

    public Optional<GameSet> getGameSet(TileSection section);

    public int getRotationCount();
}