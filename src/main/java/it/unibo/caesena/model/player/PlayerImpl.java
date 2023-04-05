package it.unibo.caesena.model.player;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.utils.StringUtil;

/**
 * {@inheritDoc}
 * Implementation of the {@link it.unibo.caesena.model.player.Player} interface.
 */
public final class PlayerImpl implements MutablePlayer {

    private final String name;
    private final Color color;
    private int score;

    /**
     * Class constructor.
     *
     * @param name of the player
     * @param color of the player
     */
    public PlayerImpl(final String name, final Color color) {
        this.name = name;
        this.color = color;
        this.score = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
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

        final PlayerImpl other = (PlayerImpl) obj;
        return name.equals(other.getName());
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
