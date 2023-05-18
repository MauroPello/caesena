package it.unibo.caesena.model.tile;

import java.util.List;
import java.util.Optional;

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

    @Id
    @ManyToOne
    private Game game;

    @ManyToOne
    private TileType type;

    @OneToMany(mappedBy = "tile")
    private List<TileSection> sections;

    private boolean current;
    private int rotationCount;
    private Integer xCoordinate;
    private Integer yCoordinate;

    public TileImpl() {}

    /**
     * Public constructor that accepts a TileType for the Tile to be created.
     * By default all sections are not closed and the rotation count is equal to 0.
     *
     * @param type of Tile just created
     */
    public TileImpl(final int tileOrder, final Game game, final TileType type) {
        this.type = type;
        this.tileOrder = tileOrder;
        this.rotationCount = 0;
        this.xCoordinate = null;
        this.yCoordinate = null;
        this.current = false;
    }

    public void setCurrent(final boolean current) {
        this.current = current;
    }

    public boolean isCurrent() {
        return this.current;
    }

    public int getTileOrder() {
        return this.tileOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getPosition() {
        return Optional.ofNullable(isPlaced() ? new Pair<>(xCoordinate, yCoordinate) : null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Pair<Integer, Integer> position) {
        this.xCoordinate = position.getX();
        this.yCoordinate = position.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaced() {
        return xCoordinate != null && yCoordinate != null;
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
    public List<TileSection> getSections() {
        return this.sections;
    }
}
