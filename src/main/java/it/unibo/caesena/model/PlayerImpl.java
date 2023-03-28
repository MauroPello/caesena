package it.unibo.caesena.model;

import it.unibo.caesena.utils.StringUtil;

public final class PlayerImpl implements Player {

    private final String name;
    private int score;

    public PlayerImpl(final String name) {
        this.name = name;
        this.score = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void setScore(final int newScore) {
        if (newScore < this.score) {
            throw new IllegalStateException("The new score is lower");
        }
        this.score = newScore;
    }

    @Override
    public void addScore(final int amount) {
        if (amount < 0) {
            throw new IllegalStateException("Amount is negative");
        }
        setScore(this.score + amount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + score;
        return result;
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

        final PlayerImpl other = (PlayerImpl) obj;
        return name.equals(other.getName())
            && score == other.score;
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }
}
