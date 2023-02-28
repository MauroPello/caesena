package it.unibo.caesena.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.utils.Position;

public class TileImpl implements Tile {

    private final Map<TileSection, GameSet> sections;
    
    private Optional<Position> currentPosition;

    public TileImpl() {
        this.sections = new HashMap<>();
        this.currentPosition = Optional.empty();
    }

    @Override
    public void rotateClockwise() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotateClockwise'");
    }

    @Override
    public Optional<Position> getPosition() {
        return this.currentPosition;
    }

    @Override
    public void setPosition(Position pos) {
        this.currentPosition = Optional.of(pos);
    }

    @Override
    public boolean isPlaced() {
        return this.currentPosition.isPresent();
    }

    @Override
    public String getImagePath() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImagePath'");
    }
}
