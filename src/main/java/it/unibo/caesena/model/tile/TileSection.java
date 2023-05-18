package it.unibo.caesena.model.tile;

import it.unibo.caesena.model.gameset.GameSetImpl;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MutableMeeple;

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

    public TileSection(final TileSectionType type) {
        this.type = type;
        this.closed = false;
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

    public void close() {
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }

}
