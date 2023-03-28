package it.unibo.caesena.model.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

public final class TileImpl implements Tile {

    private static final Integer MAX_ROTATIONS = 4;

    private final TileType type;

    private Optional<Pair<Integer, Integer>> currentPosition;
    private Map<TileSection, Pair<GameSet, Boolean>> sections;
    private int rotationCount;

    public TileImpl(final TileType type) {
        this.type = type;

        this.currentPosition = Optional.empty();
        this.sections = new HashMap<>();
        this.rotationCount = 0;
    }

    @Override
    public void rotateClockwise() {
        final Map<TileSection, Pair<GameSet, Boolean>> rotateSections = new HashMap<>();

        for (final var entry : this.sections.entrySet()) {
            rotateSections.put(TileSection.rotateClockwise(entry.getKey()), entry.getValue());
        }

        this.rotationCount = (this.rotationCount + 1) % MAX_ROTATIONS;
        this.sections = rotateSections;
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
    public void putSection(final TileSection section, final GameSet gameSet) {
        if (this.sections.containsKey(section)) {
            this.sections.put(section, new Pair<>(gameSet, sections.remove(section).getY()));
        } else {
            this.sections.put(section, new Pair<>(gameSet, false));
        }
    }

    @Override
    public int getRotationCount() {
        return this.rotationCount;
    }

    @Override
    public GameSet getGameSet(final TileSection section) {
        return this.sections.get(section).getX();
    }

    public boolean isSectionNearToGameset(final TileSection section, final GameSet gameSet) {
        return getGameSet(TileSection.next(section)).equals(gameSet) 
        || getGameSet(TileSection.previous(section)).equals(gameSet);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((currentPosition == null) ? 0 : currentPosition.hashCode());
        for (final TileSection section : TileSection.values()) {
            result = prime * result + (getGameSet(section).hashCode());
        }
        result = prime * result + rotationCount;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Tile other = (Tile) obj;
        for (final TileSection section : TileSection.values()) {
            if (!this.getGameSet(section).equals(other.getGameSet(section))) {
                return false;
            }
        }

        return getRotationCount() == other.getRotationCount() 
            && getTileType().equals(other.getTileType())
            && getPosition().equals(other.getPosition());
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

    @Override
    public void closeSection(final TileSection section) {
        this.sections.put(section, new Pair<>(sections.remove(section).getX(), true));
    }

    @Override
    public boolean isSectionClosed(final TileSection section) {
        return this.sections.get(section).getY();
    }

    @Override
    public TileType getTileType() {
        return this.type;
    }
}
