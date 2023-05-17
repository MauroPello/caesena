package it.unibo.caesena.model.tile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.caesena.model.Game;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * A class representing a simple Tile.
 */
@Entity(name = "Tiles")
@Table(name = "Tiles")
@Access(AccessType.FIELD)
public final class TileImpl implements MutableTile {

    @Transient
    private static final int MAX_ROTATIONS = 4;

    @Id
    private int tileOrder;

    @ManyToOne
    private final TileType type;

    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "tile")
    private Set<TileSection> sections;

    private int rotationCount;
    private int xCoordinate;
    private int yCoordinate;

    @Transient
    private Pair<Integer, Integer> currentPosition;

    /**
     * Public constructor that accepts a TileType for the Tile to be created.
     * By default all sections are not closed and the rotation count is equal to 0.
     *
     * @param type of Tile just created
     */
    public TileImpl(final TileType type) {
        this.type = type;
        this.rotationCount = 0;

        this.currentPosition = null;
    }

    public int getTileOrder() {
        return tileOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getPosition() {
        return Optional.ofNullable(this.currentPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Pair<Integer, Integer> position) {
        this.currentPosition = position;
        this.xCoordinate = position.getX();
        this.yCoordinate = position.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaced() {
        return this.currentPosition == null;
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

    @Override
    public boolean isSectionClosed(TileSection section) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSectionClosed'");
    }

    @Override
    public void closeSection(TileSection section) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeSection'");
    }

    @Override
    public List<TileSection> getSections() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSections'");
    }
}
