package it.unibo.caesena.model.tile;

import it.unibo.caesena.model.gameset.GameSetImpl;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.utils.StringUtil;

import java.io.Serializable;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Class representing the different possible portions of the sides of a Tile.
 */
@Entity(name = "TileSections")
@Table(name = "TileSections")
@Access(AccessType.FIELD)
public class TileSection implements Serializable {

    @Id
    @ManyToOne
    private TileSectionType type;

    @Id
    @ManyToOne
    private TileImpl tile;

    @ManyToOne
    private GameSetImpl gameSet;

    @OneToOne(mappedBy = "section", optional = true)
    private MeepleImpl meeple;

    private boolean closed;

    public TileSection() {}

    public TileSection(final TileImpl tile, final TileSectionType type, final GameSetImpl gameSet) {
        this.closed = false;
        this.tile = tile;
        this.gameSet = gameSet;
        this.type = type;
    }

    public TileSectionType getType() {
        return this.type;
    }

    public void setType(final TileSectionType tileSectionType) {
        this.type = tileSectionType;
    }

    public MutableTile getTile() {
        return this.tile;
    }

    public GameSetImpl getGameSet() {
        return this.gameSet;
    }

    public void setGameSet(final GameSetImpl gameSet) {
        this.gameSet = gameSet;
    }

    public MutableMeeple getMeeple() {
        return this.meeple;
    }

    public void setMeeple(final MeepleImpl meeple) {
        this.meeple = meeple;
    }

    public void close() {
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((tile == null) ? 0 : tile.hashCode());
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
        TileSection other = (TileSection) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (tile == null) {
            if (other.tile != null)
                return false;
        } else if (!tile.equals(other.tile))
            return false;
        return true;
    }

}
