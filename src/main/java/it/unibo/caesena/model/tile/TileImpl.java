package it.unibo.caesena.model.tile;

import java.util.List;
import java.util.Optional;

import it.unibo.caesena.model.Game;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * A class representing a simple Tile.
 */
@Entity(name = "tiles")
@Table(name = "tiles")
@Access(AccessType.FIELD)
public final class TileImpl implements MutableTile {

    @Transient
    private static final int MAX_ROTATIONS = 4;

    @Id
    @Column(name = "\"order\"")
    private int order;

    @Id
    @ManyToOne
    private Game game;

    @ManyToOne
    private TileType type;

    @OneToMany(mappedBy = "tile")
    private List<TileSection> sections;

    @Column(name = "rotation_count")
    private int rotationCount;

    @Column(name = "x_coordinate", nullable = true)
    private Integer xCoordinate;

    @Column(name = "y_coordinate", nullable = true)
    private Integer yCoordinate;

    private boolean current;

    public TileImpl() {}

    /**
     * Public constructor that accepts a TileType for the Tile to be created.
     * By default all sections are not closed and the rotation count is equal to 0.
     *
     * @param type of Tile just created
     */
    public TileImpl(final int tileOrder, final Game game, final TileType type) {
        this.type = type;
        this.order = tileOrder;
        this.rotationCount = 0;
        this.xCoordinate = null;
        this.yCoordinate = null;
        this.current = false;
        this.game = game;
    }

    public void setOrder(final int order) {
       this.order = order;
    }

    public void setCurrent(final boolean current) {
        this.current = current;
    }

    public boolean isCurrent() {
        return this.current;
    }

    public int getOrder() {
        return this.order;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + order;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TileImpl other = (TileImpl) obj;
        if (order != other.order)
            return false;
        return true;
    }


}
