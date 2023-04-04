package it.unibo.caesena.model.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

/**
 * A class representing a simple Tile. 
 */
public final class TileImpl implements Tile {
    private final static int MAX_ROTATIONS = 4;
    private final Map<TileSection, Boolean> sections;
    private final TileType type;

    private Optional<Pair<Integer, Integer>> currentPosition;
    private int rotationCount;

    /**
     * Public constructor that accepts a TileType for the Tile to be created.
     * By default all sections are not closed and the rotation count is equal to 0.
     * 
     * @param type of Tile just created
     */
    public TileImpl(final TileType type) {
        this.type = type;
        this.rotationCount = 0;

        this.currentPosition = Optional.empty();
        this.sections = new HashMap<>();
        for (final var section : TileSection.values()) {
            this.sections.put(section, false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getPosition() {
        return this.currentPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Pair<Integer, Integer> position) {
        this.currentPosition = Optional.of(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaced() {
        return this.currentPosition.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeSection(final TileSection section) {
        this.sections.put(section, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSectionClosed(final TileSection section) {
        return this.sections.get(section);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileType getTileType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rotate() {
        this.rotationCount = (this.rotationCount + 1) % MAX_ROTATIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRotationCount() {
        return this.rotationCount;
    }
}
