package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.utils.*;

public class NormalMeeple implements Meeple {

    private static final int STRENGTH = 1;
    private final Player owner;

    private boolean placed;

    public NormalMeeple(final Player owner) {
        this.owner = owner;

        this.placed = false;
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
    public boolean placeOnTile() {
        if (isPlaced()) {
            return false;
        }

        this.placed = true;
        return true;
    }

    @Override
    public boolean removeFromTile() {
        if (!isPlaced()) {
            return false;
        }

        this.placed = false;
        return true;
    }

    @Override
    public boolean isPlaced() {
        return this.placed;
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
            placed == other.isPlaced();
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

}
