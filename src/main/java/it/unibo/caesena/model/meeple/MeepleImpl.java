package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.player.MutablePlayerInGame;
import it.unibo.caesena.utils.StringUtil;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * {@inheritDoc}
 *
 * A class representing a normal {@link it.unibo.caesena.model.meeple.Meeple} with strength always equal to 1.
 */
@Entity(name = "meeples")
@Table(name = "meeples")
@Access(AccessType.FIELD)
public class MeepleImpl implements MutableMeeple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    private MeepleType type;

    @ManyToOne(optional = false)
    private PlayerInGameImpl owner;

    @Column(nullable = false)
    private boolean placed;

    public MeepleImpl() {}

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
    public MutablePlayerInGame getOwner() {
        return this.owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaced(final boolean placed) {
        this.placed = placed;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        MeepleImpl other = (MeepleImpl) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
