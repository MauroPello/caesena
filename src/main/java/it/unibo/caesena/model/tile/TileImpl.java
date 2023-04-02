package it.unibo.caesena.model.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

public final class TileImpl implements Tile {
    private final static int MAX_ROTATIONS = 4;
    private final Map<TileSection, Boolean> sections;
    private final TileType type;

    private Optional<Pair<Integer, Integer>> currentPosition;
    private int rotationCount;

    public TileImpl(final TileType type) {
        this.type = type;
        this.rotationCount = 0;

        this.currentPosition = Optional.empty();
        this.sections = new HashMap<>();
        for (final var section : TileSection.values()) {
            this.sections.put(section, false);
        }
    }

    @Override
    public Optional<Pair<Integer, Integer>> getPosition() {
        return this.currentPosition;
    }

    @Override
    public void setPosition(final Pair<Integer, Integer> pos) {
        this.currentPosition = Optional.of(pos);
    }

    @Override
    public boolean isPlaced() {
        return this.currentPosition.isPresent();
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

    @Override
    public void closeSection(final TileSection section) {
        this.sections.put(section, true);
    }

    @Override
    public boolean isSectionClosed(final TileSection section) {
        return this.sections.get(section);
    }

    @Override
    public TileType getTileType() {
        return this.type;
    }

    @Override
    public void rotate() {
        this.rotationCount = (this.rotationCount + 1) % MAX_ROTATIONS;
    }

    @Override
    public int getRotationCount() {
        return this.rotationCount;
    }
}
