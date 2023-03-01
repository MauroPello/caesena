package it.unibo.caesena.model;

import java.util.Optional;

import it.unibo.caesena.utils.ToStringBuilder;

public class NormalMeeple implements Meeple {

    private static final int STRENGTH = 1;
    private final Player owner;

    private Optional<TileSection> section;
    private Optional<Tile> tile;

    public NormalMeeple(Player owner) {
        this.owner = owner;

        this.section = Optional.empty();
        this.tile = Optional.empty();
    }

    @Override
    public int getStrength() {
        return NormalMeeple.STRENGTH;
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
    public boolean place(TileSection section, Tile tile) {
        if (isPlaced() || !tile.getGameSet(section).get().isMeepleFree()) {
            return false;
        }

        this.section = Optional.of(section);
        this.tile = Optional.of(tile);
        this.tile.get().getGameSet(this.section.get()).get().addMeeple(this);
        return true;
    }

    @Override
    public boolean isPlaced() {
        return this.section.isPresent() && this.tile.isPresent();
    }

    @Override
    public Player getOwner() {
        return this.owner;
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
		NormalMeeple other = (NormalMeeple) obj;
		return owner.equals(other.getOwner()) && 
            section.equals(other.getTileSection()) &&
            tile.equals(other.getTile());
    }

    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("OWNER", this.owner)
            .add("TILE", this.tile.orElse(null))
            .add("SECTION", this.section.orElse(null))
            .build();
    }

    @Override
    public boolean removeFromTile() {
        if (!isPlaced()) {
            return false;
        }

        this.tile.get().getGameSet(this.section.get()).get().removeMeeple(this);
        this.section = Optional.empty();
        this.tile = Optional.empty();
        return true;
    }

    
}
