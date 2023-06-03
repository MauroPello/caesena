package it.unibo.caesena.model.player;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.Game;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.utils.StringUtil;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * {@inheritDoc}
 * Implementation of the {@link it.unibo.caesena.model.player.PlayerInGame} interface.
 */
@Entity(name = "players_in_game")
@Table(name = "players_in_game")
@Access(AccessType.FIELD)
public final class PlayerInGameImpl implements MutablePlayerInGame, Serializable {

    @Id
    @ManyToOne(optional = false)
    private Player player;

    @Id
    @ManyToOne(optional = false)
    private Game game;

    @ManyToOne(optional = false)
    private Color color;

    @OneToMany(mappedBy = "owner")
    private Set<MeepleImpl> meeples;

    @Column(name = "\"order\"", nullable = false)
    private int order;

    @Column(nullable = false)
    private boolean current;

    @Column(nullable = false)
    private int score;

    public PlayerInGameImpl() {}

    /**
     * Class constructor.
     *
     * @param name of the player
     * @param color of the player
     */
    public PlayerInGameImpl(final Player player, final Color color, final int playerOrder, final Game game) {
        this.player = player;
        this.order = playerOrder;
        this.color = color;
        this.score = 0;
        this.meeples = new HashSet<>();
        this.game = game;
        this.current = false;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCurrent() {
        return current;
    }

    @Override
    public void setCurrent(final boolean current) {
        this.current = current;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public Set<MeepleImpl> getMeeples() {
        return this.meeples;
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

        final PlayerInGame other = (PlayerInGame) obj;
        return this.player.getName().equals(other.getPlayer().getName());
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

    public Game getGame() {
        return game;
    }

}
