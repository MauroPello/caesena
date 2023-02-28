package it.unibo.caesena.model;

import it.unibo.caesena.utils.Color;

public class PlayerImpl implements Player{

    private final String name;
    private final Color color;
    private int score;

    public PlayerImpl(String name, Color color) {
        this.name = name;
        this.color = color;
        this.score = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Color getColor() {
        return this.color;
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
        return otherPlayer.name == this.name && otherPlayer.color == this.color;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("[")
            .append("Name: ")
            .append(this.name)
            .append(", ")
            .append("Color: ")
            .append(this.color.toString())
            .append(", ")
            .append("Current score: ")
            .append(this.score)
            .append("]");
        return builder.toString();
    }
}
