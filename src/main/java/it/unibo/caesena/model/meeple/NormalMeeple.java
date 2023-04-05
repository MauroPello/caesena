package it.unibo.caesena.model.meeple;

import java.util.Optional;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

/**
 * {@inheritDoc}
 *
 * A class representing a normal Meeple with strength always equal to 1.
 */
public class NormalMeeple implements MutableMeeple {

    private static final int STRENGTH = 1;
    private final Player owner;
    private Optional<Pair<Tile, TileSection>> position;

    /**
     * Public constructor that accepts as argument the player that owns the meeple.
     *
     * @param owner the player that owns the meeple
     */
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
    public void place(final Tile tile, final TileSection tileSection) {
        this.position = Optional.of(new Pair<>(tile, tileSection));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        this.position = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return this.owner.getColor();
    }
}
