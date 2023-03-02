package it.unibo.caesena.model.meeple;

import java.util.Optional;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.*;

public class NormalMeeple implements Meeple {

    private static final int STRENGTH = 1;
    private final Player owner;

    private Optional<TileSection> section;
    private Optional<Tile> tile;

    public NormalMeeple(final Player owner) {
        this.owner = owner;

        this.section = Optional.empty();
        this.tile = Optional.empty();
    }

    @Override
    public int getStrength() {
        return NormalMeeple.STRENGTH;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public Optional<TileSection> getTileSection() {
        return this.section;
    }

    @Override
    public Optional<Tile> getTile() {
        return this.tile;
    }

    @Override
    public boolean place(final TileSection section, final Tile tile) {
        if (isPlaced()) {
            return false;
        }

        this.section = Optional.of(section);
        this.tile = Optional.of(tile);
        return true;
    }

    @Override
    public boolean removeFromTile() {
        if (!isPlaced()) {
            return false;
        }

        this.section = Optional.empty();
        this.tile = Optional.empty();
        return true;
    }

    @Override
    public boolean isPlaced() {
        return this.section.isPresent() && this.tile.isPresent();
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
		NormalMeeple other = (NormalMeeple) obj;
		return owner.equals(other.getOwner()) &&
            section.equals(other.getTileSection()) &&
            tile.equals(other.getTile());
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

}
