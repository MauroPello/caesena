package it.unibo.caesena.model.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.ToStringBuilder;

public class TileImpl implements Tile {

    private final String imageName;

    private static final String ROOT = "it/unibo/caesena/images/";
    private static final String IMAGE_FORMAT = "png";
    private static final Integer MAX_ROTATIONS = 4;

    private Map<TileSection, GameSet> sections;
    private Optional<Pair<Integer, Integer>> currentPosition;
    private int rotationCount;

    public TileImpl(String imageName) {
        this.imageName = imageName;
        
        this.currentPosition = Optional.empty();
        this.sections = new HashMap<>();
        this.rotationCount = 0;
    }

    @Override
    public void rotateClockwise() {
        final Map<TileSection, GameSet> rotateSections = new HashMap<>();
        // //metto in up i left
        // rotateSections.put(TileSection.UpRight, this.sections.get(TileSection.LeftUp));
        // rotateSections.put(TileSection.UpCenter, this.sections.get(TileSection.LeftCenter));
        // rotateSections.put(TileSection.UpLeft, this.sections.get(TileSection.LeftDown));
        
        // //metto in left i down
        // rotateSections.put(TileSection.LeftDown, this.sections.get(TileSection.DownRight));
        // rotateSections.put(TileSection.LeftCenter, this.sections.get(TileSection.DownCenter));
        // rotateSections.put(TileSection.LeftUp, this.sections.get(TileSection.DownLeft));
        
        // //metto in down i right
        // rotateSections.put(TileSection.DownRight, this.sections.get(TileSection.RightUp));
        // rotateSections.put(TileSection.DownCenter, this.sections.get(TileSection.RightCenter));
        // rotateSections.put(TileSection.DownLeft, this.sections.get(TileSection.RightDown));

        // //metto in right gli up
        // rotateSections.put(TileSection.RightDown, this.sections.get(TileSection.UpRight));
        // rotateSections.put(TileSection.RightCenter, this.sections.get(TileSection.UpCenter));
        // rotateSections.put(TileSection.RightUp, this.sections.get(TileSection.UpLeft));

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
    public void setPosition(Pair<Integer, Integer> pos) {
        this.currentPosition = Optional.of(pos);
    }

    @Override
    public boolean isPlaced() {
        return this.currentPosition.isPresent();
    }

    @Override
    public String getImageResourcesPath() {
        return ROOT + this.imageName + "." + IMAGE_FORMAT;
    }

    @Override
    public void putSection(TileSection section, GameSet gameSet) {
        this.sections.put(section, gameSet);
    }

    @Override
    public int getRotationCount() {
        return this.rotationCount;
    }

    @Override
    public Optional<GameSet> getGameSet(TileSection section) {
        return Optional.ofNullable(this.sections.getOrDefault(section, null));
    }

    @Override
    public boolean equals(Object obj) {

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
        boolean equal = true;

        for (TileSection section : TileSection.values()) {
            if (!this.getGameSet(section).equals(other.getGameSet(section)))
            {
                equal = false;
            }
        }

        return equal;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder().addFromObjectGetters(this).build();
    }
}
