package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.StringUtil;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * {@inheritDoc}
 *
 * A class representing a normal {@link it.unibo.caesena.model.meeple.Meeple} with strength always equal to 1.
 */
@Entity(name = "Meeples")
@Table(name = "Meeples")
@Access(AccessType.FIELD)
public class MeepleImpl implements MutableMeeple {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean placed;

    @ManyToOne
    @JoinColumn(name = "fk_meeple_type")
    private final MeepleType type;

    @ManyToOne
    private final PlayerInGameImpl owner;

    @ManyToOne
    @JoinColumn(name = "fk_type", referencedColumnName = "fk_type")
    @JoinColumn(name = "fk_tile", referencedColumnName = "fk_tile")
    private TileSection section;

    /**
     * Public constructor that accepts as argument the {@link it.unibo.caesena.model.player.PlayerInGameImpl} that owns the meeple.
     *
     * @param owner the player that owns the meeple
     */
    public MeepleImpl(final MeepleType type, final PlayerInGameImpl owner) {
        this.owner = owner;
        this.type = type;
        this.placed = false;
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
    public PlayerInGameImpl getOwner() {
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

    @Override
    public TileSection getPosition() {
        return this.section;
    }
}
