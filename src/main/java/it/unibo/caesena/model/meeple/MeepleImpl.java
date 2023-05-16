package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.player.PlayerInGame;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.StringUtil;

/**
 * {@inheritDoc}
 *
 * A class representing a normal {@link it.unibo.caesena.model.meeple.Meeple} with strength always equal to 1.
 */
public class MeepleImpl implements MutableMeeple {

    // private final int id;
    private final MeepleType type;
    private final PlayerInGame owner;

    private boolean placed;
    // TODO private TileSection section;

    /**
     * Public constructor that accepts as argument the {@link it.unibo.caesena.model.player.PlayerInGame} that owns the meeple.
     *
     * @param owner the player that owns the meeple
     */
    public MeepleImpl(final MeepleType type, final PlayerInGame owner) {
        this.owner = owner;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStrength() {
        return this.type.getStrength();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInGame getOwner() {
        return this.owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void place() {
        this.placed = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        this.placed = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaced() {
        return this.placed;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public MeepleType getType() {
        return this.type;
    }
}
