package it.unibo.caesena.model;

import java.util.Optional;

import it.unibo.caesena.utils.Position;

public interface Tile {

    public void rotateClockwise();

    public Optional<Position> getPosition();
    
    public void setPosition(Position pos);

    public boolean isPlaced();

    public String getImagePath();

    public void putSection(TileSection section, GameSet gameSet);

    public int getRotationCount();
}
