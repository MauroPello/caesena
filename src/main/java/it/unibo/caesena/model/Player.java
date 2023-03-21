package it.unibo.caesena.model;

public interface Player {

    String getName();

    int getScore();

    void setScore(int newScore);

    void addScore(int amount);
}
