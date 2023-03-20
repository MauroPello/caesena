package it.unibo.caesena.model.tile;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.*;

public class TileImpl implements Tile {

    private static final String SEP = File.separator;
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "images" + SEP + "tiles" + SEP;
    private static final String IMAGE_FORMAT = "png";
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

        for (var entry : this.sections.entrySet()) {
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
    public String getImageResourcesPath() {
        try {
            return Paths.get(ClassLoader.getSystemResource(ROOT + this.type.name() + "." + IMAGE_FORMAT).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException();
        }
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
        return getGameSet(TileSection.next(section)).equals(gameSet) ||
            getGameSet(TileSection.previous(section)).equals(gameSet);
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
        
		Tile other = (Tile) obj;
        for (TileSection section : TileSection.values()) {
            if (!this.getGameSet(section).equals(other.getGameSet(section))) 
            {
                return false;
            }
        }

        return true;
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
    public boolean isSectionClosed(TileSection section) {
        return this.sections.get(section).getY();
    }

    @Override
    public TileType getTileType() {
        return this.type;
    }
}
