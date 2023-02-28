package it.unibo.caesena.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.utils.Position;

public class TileImpl implements Tile {

    private final Map<TileSection, GameSet> sections;
    private final String imagePath;

    private Optional<Position> currentPosition;

    public TileImpl(String imagePath) {
        this.sections = new HashMap<>();
        this.currentPosition = Optional.empty();
        this.imagePath = imagePath;
        //a ogni tile impl genero l'immagine
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
        return this.imagePath;
    }

    @Override
    public void putSection(TileSection section, GameSet gameSet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putSection'");
    }
}
