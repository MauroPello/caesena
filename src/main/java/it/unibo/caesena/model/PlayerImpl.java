package it.unibo.caesena.model;

import it.unibo.caesena.utils.*;

public class PlayerImpl implements Player{

    private final String name;
    private int score;

    public PlayerImpl(String name) {
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
    public void setScore(int newScore) {
        if (newScore <= this.score) {
            throw new IllegalStateException("The new score is lower");
        }
        this.score = newScore;
    }

    @Override
    public void addScore(int amount) {
        if (amount < 0) {
            throw new IllegalStateException("Amount is negative");
        }
        setScore(this.score + amount);
    }

    @Override
    public boolean equals(Object arg0) {
        PlayerImpl otherPlayer = (PlayerImpl) arg0;
        return otherPlayer.name == this.name;
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }
}
