package it.unibo.caesena.model.player;

import java.util.HashSet;
import java.util.Set;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.Game;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.utils.StringUtil;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * {@inheritDoc}
 * Implementation of the {@link it.unibo.caesena.model.player.PlayerInGame} interface.
 */
@Entity(name = "PlayersInGame")
@Table(name = "PlayersInGame")
@Access(AccessType.FIELD)
public final class PlayerInGameImpl implements MutablePlayerInGame {

    @Id
    @ManyToOne
    @JoinColumn(name = "fk_player")
    private Player player;
    @Id
    @ManyToOne
    @JoinColumn(name = "fk_game")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "fk_color")
    private final Color color;
    @OneToMany(mappedBy = "owner")
    private final Set<MeepleImpl> meeples;

    private boolean current;
    private int score;
    private int order;

    public Player getPlayer() {
        return player;
    }

    public boolean isCurrent() {
        return current;
    }

    public int getOrder() {
        return order;
    }

    /**
     * Class constructor.
     *
     * @param name of the player
     * @param color of the player
     */
    public PlayerInGameImpl(final Player player, final Color color) {
        this.player = player;
        this.color = color;
        this.score = 0;
        this.meeples = new HashSet<>();
    }

    public Set<MeepleImpl> getMeeples() {
        return this.meeples;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.player.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScore(final int newScore) {
        if (newScore < this.score) {
            throw new IllegalStateException("The new score is lower");
        }
        this.score = newScore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addScore(final int amount) {
        if (amount < 0) {
            throw new IllegalStateException("Amount is negative");
        }
        setScore(this.score + amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return this.player.hashCode();
    }

    /**
     * {@inheritDoc}
     */
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

        final PlayerInGameImpl other = (PlayerInGameImpl) obj;
        return this.player.getName().equals(other.getName());
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
        return this.color;
    }
}
