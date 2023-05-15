package it.unibo.caesena.model.player;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.utils.StringUtil;

/**
 * {@inheritDoc}
 * Implementation of the {@link it.unibo.caesena.model.player.PlayerInGame} interface.
 */
public final class PlayerInGameImpl implements MutablePlayerInGame {

    private final Player player;
    private final Color color;
    private int score;

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
