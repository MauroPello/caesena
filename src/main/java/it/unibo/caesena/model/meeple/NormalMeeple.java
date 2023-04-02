package it.unibo.caesena.model.meeple;

import java.util.Optional;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

public class NormalMeeple implements Meeple {

    private static final int STRENGTH = 1;
    private final Player owner;
    private Optional<Pair<Tile, TileSection>> position;

    public NormalMeeple(final Player owner) {
        this.owner = owner;
        this.position = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStrength() {
        return NormalMeeple.STRENGTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void place(final Pair<Tile, TileSection> position) {
        this.position = Optional.of(position);
    }

    @Override
    public void remove() {
        this.position = Optional.empty();
    }

    @Override
    public Pair<Tile, TileSection> getPosition() {
        return this.position.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaced() {
        return this.position.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

}
