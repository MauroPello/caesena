package it.unibo.caesena.model.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.*;

public class TileImpl implements Tile {

    private final String imageName;
    private static final String SEP = "/";//File.separator;
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "images" + SEP + "tiles" + SEP;
    private static final String IMAGE_FORMAT = "png";
    private static final Integer MAX_ROTATIONS = 4;

    private Optional<Pair<Integer, Integer>> currentPosition;
    private Map<TileSection, GameSet> sections;
    private int rotationCount;

    public TileImpl(final String imageName) {
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

        // if(this.rotationCount == 4) {
        //     this.rotationCount = 0;
        // }
        // this.rotationCount++;
        //
        for (var entry : this.sections.entrySet()) {
            rotateSections.put(TileSection.rotateClockwise(entry.getKey()), entry.getValue());
        }

        this.rotationCount = (this.rotationCount + 1) % MAX_ROTATIONS;
        //
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
        return ROOT + this.imageName + "." + IMAGE_FORMAT;
    }

    @Override
    public void putSection(final TileSection section, final GameSet gameSet) {
        this.sections.put(section, gameSet);
    }

    @Override
    public int getRotationCount() {
        return this.rotationCount;
    }

    @Override
    public GameSet getGameSet(final TileSection section) {
        return this.sections.get(section);
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
        boolean equal = true;

        for (TileSection section : TileSection.values()) {
            // if (!this.getGameSet(section).equals(other.getGameSet(section)))
            if (!this.getGameSet(section).getType().equals(other.getGameSet(section).getType()))
            {
                equal = false;
            }
        }

        return equal;
    }

    @Override
    public String toString() {
        System.out.println(this.sections);
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

    @Override
    public void closeSection(final TileSection section) {
        
    }
}
